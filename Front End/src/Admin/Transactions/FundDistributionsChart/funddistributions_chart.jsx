import React, { useEffect, useState } from 'react';
import { Pie, Line } from 'react-chartjs-2';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement, LineElement, CategoryScale, LinearScale } from 'chart.js';
import axios from 'axios';

import './style.scss'; 
import config from '../../../config';


ChartJS.register(Title, Tooltip, Legend, ArcElement, LineElement, CategoryScale, LinearScale);

const FundDistributionChart = () => {
  const accountNo = sessionStorage.getItem("accountNo");
  const [fundData, setFundData] = useState({
    debit: 0,
    credit: 0,
    loanPayment: 0,
    emi: 0
  });
  
  const [lineChartData, setLineChartData] = useState({
    labels: [],
    datasets: []
  });

  useEffect(() => {
    if (accountNo) {
      axios.get(`${config.url}/transactions/transactions`)
        .then(response => {
          setFundData({
            debit: response.data.debit,
            credit: response.data.credit,
            loanPayment: response.data.loanPayment,
            emi: response.data.emi
          });
        })
        .catch(error => {
          console.error('Error fetching fund data:', error);
        });

      axios.get(`${config.url}/transactions/historical`)
        .then(response => {
          const { dates, debits, credits, loanDisbursements, emis } = response.data;
          setLineChartData({
            labels: dates,
            datasets: [
              {
                label: 'Debit',
                data: debits,
                borderColor: '#FF6384',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                fill: false
              },
              {
                label: 'Credit',
                data: credits,
                borderColor: '#36A2EB',
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                fill: false
              },
              {
                label: 'Loan Disbursement',
                data: loanDisbursements,
                borderColor: '#FFCE56',
                backgroundColor: 'rgba(255, 206, 86, 0.2)',
                fill: false
              },
              {
                label: 'EMI',
                data: emis,
                borderColor: '#4BC0C0',
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                fill: false
              }
            ]
          });
        })
        .catch(error => {
          console.error('Error fetching historical data:', error);
        });
    }
  }, [accountNo]);

  const pieChartData = {
    labels: ['Debit', 'Credit', 'Loan Disbursement', 'EMI'],
    datasets: [
      {
        data: [fundData.debit, fundData.credit, fundData.loanPayment, fundData.emi],
        backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
        borderColor: '#fff',
        borderWidth: 1
      }
    ]
  };

  return (
    <div className="chart-container">
      <h2 className="chart-header">Fund Distribution</h2>
      <div className="pie-chart">
        <Pie data={pieChartData} />
      </div>

      <h2 className="chart-header">Fund Trend Over Time</h2>
      <div className="line-chart">
        <Line data={lineChartData} options={{
          scales: {
            x: {
              title: {
                display: true,
                text: 'Date'
              }
            },
            y: {
              title: {
                display: true,
                text: 'Amount'
              }
            }
          }
        }} />
      </div>
    </div>
  );
};

export default FundDistributionChart;

import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';

import personal_loan from './personalloan.jpg'
import car_loan from './car_loan.png'
import education2 from './eduloan2.jpg'
import home_loan from './home_loan.png'
export default function LoanTypeTable() {
  // Static data for loan types
  const loanTypes = [
    {
      loanName: 'Personal Loan',
      imageUrl: personal_loan,
      description: 'A personal loan is an unsecured loan that can be used for various personal expenses.',
      maxAmount: 500000,
      minAmount: 10000,
      interestRate: 12 // Example interest rate
    },
    {
      loanName: 'Home Loan',
      imageUrl: home_loan,
      description: 'A home loan helps you purchase or renovate your house with affordable interest rates.',
      maxAmount: 5000000,
      minAmount: 100000,
      interestRate: 8 // Example interest rate
    },
    {
      loanName: 'Car Loan',
      imageUrl: car_loan,
      description: 'A car loan provides funds to buy a new or used car with flexible repayment options.',
      maxAmount: 2000000,
      minAmount: 50000,
      interestRate: 9 // Example interest rate
    },
    {
      loanName: 'Education Loan',
      imageUrl: education2,
      description: 'An education loan helps cover the cost of higher education, including tuition fees and living expenses.',
      maxAmount: 3000000,
      minAmount: 50000,
      interestRate: 10 // Example interest rate
    }
  ];

  return (
    <div>
      <Card sx={{ minWidth: 1180, mb: 2 }}>
        <CardContent>
          <h2>Loan Types</h2>
        </CardContent>
      </Card>
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
        <TableContainer sx={{ maxHeight: 500 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>Image</TableCell>
                <TableCell align='center'>Loan Name</TableCell>
                <TableCell align='center'>Description</TableCell>
                <TableCell align='center'>Max Amount</TableCell>
                <TableCell align='center'>Min Amount</TableCell>
                <TableCell align='center'>Interest Rate</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {loanTypes.map((loan) => (
                <TableRow hover role="checkbox" tabIndex={-1} key={loan.loanName}>
                  <TableCell align='center'>
                    <img src={loan.imageUrl} alt={loan.loanName} style={{ width: '150px', height: 'auto' }} />
                  </TableCell>
                  <TableCell align='center'>{loan.loanName}</TableCell>
                  <TableCell align='center'>{loan.description}</TableCell>
                  <TableCell align='center'>{`₹${loan.maxAmount.toFixed(2)}`}</TableCell>
                  <TableCell align='center'>{`₹${loan.minAmount.toFixed(2)}`}</TableCell>
                  <TableCell align='center'>{`${loan.interestRate}%`}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </div>
  );
}

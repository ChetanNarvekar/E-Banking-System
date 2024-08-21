import * as React from 'react';
import { useEffect, useState } from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import TextField from '@mui/material/TextField';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import axios from 'axios';
import { toast } from 'react-toastify';
import config from '../../../../config';
import VerifyTpinModal from '../Modal/verifyTpinModal';

export default function EmiPay() {
  const accountNo = sessionStorage.getItem("accountNo");
  const [page, setPage] = useState(0);
  const [rows, setRows] = useState([]);
  const [filteredRows, setFilteredRows] = useState([]);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [modalShow, setModalShow] = useState(false);
  const [selectedLoanNo, setSelectedLoanNo] = useState(null);

  const token = sessionStorage.getItem('token');
  
  useEffect(() => {
    if (accountNo) {
      axios.get(`${config.url}/bank/user/getloandetailsbyacno/${accountNo}`, {
        headers:{
              'Authorization': `Bearer ${token}`, 
              'Content-Type': 'application/json'
        }
      })
        .then((response) => {
          console.log('API Response:', response.data); // Inspecting the response
          setRows(response.data);
          setFilteredRows(response.data);
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
          toast.error('Failed to fetch loan data');
        });
    }
  }, [accountNo]);

  useEffect(() => {
    const filtered = rows.filter(row =>
      row.loanNo?.toLowerCase().includes(searchTerm) ||
      row.loanAmount?.toString().toLowerCase().includes(searchTerm) ||
      row.emi?.toString().toLowerCase().includes(searchTerm) ||
      row.startDate?.toLowerCase().includes(searchTerm) ||
      row.endDate?.toLowerCase().includes(searchTerm)
    );
    setFilteredRows(filtered);
  }, [searchTerm, rows]);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  const handlePayClick = (loanNo) => {
    console.log('Selected Loan No:', loanNo); // Inspecting the selected loan number
    setSelectedLoanNo(loanNo);
    setModalShow(true);
  };

  const handleVerifyAndPay = async () => {
    if (!selectedLoanNo) return;

    try {
      const token = sessionStorage.getItem('token');
      console.log('Token:', token);
      console.log('Processing payment for Loan No:', selectedLoanNo); // Inspecting the loan number

      const response = await axios.post(`${config.url}/bank/user/addloanpayment/${selectedLoanNo}`, {}, {
        headers: {
          'Authorization': `Bearer ${token}`, 
          'Content-Type': 'application/json'
        }
      });

      console.log('Payment Response:', response);
      toast.success('Payment successfully processed');
    } catch (error) {
      console.error('Error processing payment:', error);
      toast.error('Failed to process payment');
    } finally {
      setModalShow(false);
    }
  };

  const formatCurrency = (value) => {
    if (value === null || value === undefined) return "₹0.00";
    return `₹${parseFloat(value).toFixed(2)}`;
  };

  return (
    <div>
      <Card sx={{ minWidth: 1180, mb: 2 }}>
        <CardContent>
          <TextField
            label="Search"
            variant="outlined"
            fullWidth
            onChange={handleSearch}
            value={searchTerm}
            sx={{ mb: 2 }}
          />
        </CardContent>
      </Card>
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
        <TableContainer sx={{ maxHeight: 500 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>LoanNo</TableCell>
                <TableCell align='center'>LoanAmount</TableCell>
                <TableCell align='center'>Balance</TableCell>
                <TableCell align='center'>EMI</TableCell>
                <TableCell align='center'>StartDate</TableCell>
                <TableCell align='center'>EndDate</TableCell>
                <TableCell align='center'>Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row) => (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.loanNo}>
                    <TableCell align='center'>{row.loanNo}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.loanAmount)}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.remainingAmount)}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.emi)}</TableCell>
                    <TableCell align='center'>{row.startDate}</TableCell>
                    <TableCell align='center'>{row.endDate}</TableCell>
                    <TableCell align='center'>
                      <button onClick={() => handlePayClick(row.loanNo)}>Pay</button>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 100]}
          component="div"
          count={filteredRows.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
      <VerifyTpinModal
        show={modalShow}
        handleClose={() => setModalShow(false)}
        onVerify={handleVerifyAndPay}
      />
    </div>
  );
}

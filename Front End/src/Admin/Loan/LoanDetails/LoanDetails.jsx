import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import TablePagination from '@mui/material/TablePagination';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import TextField from '@mui/material/TextField';
import config from '../../../config';

export default function LoanList() {
  const [loans, setLoans] = useState([]);
  const [filteredLoans, setFilteredLoans] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const token = sessionStorage.getItem('token');
  
    axios.get(`${config.url}/bank/admin/getallloandetails`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      setLoans(response.data);
      setFilteredLoans(response.data);
    })
    .catch(error => console.error('Error fetching loans:', error));
  }, []);
  

  useEffect(() => {
    const filtered = loans.filter(loan =>
      loan.loanNo?.toLowerCase().includes(searchTerm) ||
      loan.loanAmount?.toString().toLowerCase().includes(searchTerm) ||
      loan.remainingAmount?.toString().toLowerCase().includes(searchTerm) ||
      loan.emi?.toString().toLowerCase().includes(searchTerm) ||
      loan.loanName?.toLowerCase().includes(searchTerm) ||
      loan.interestRate?.toString().toLowerCase().includes(searchTerm) ||
      loan.tenure?.toString().toLowerCase().includes(searchTerm) ||
      loan.asset?.toLowerCase().includes(searchTerm) ||
      loan.value?.toString().toLowerCase().includes(searchTerm) ||
      loan.startDate?.toLowerCase().includes(searchTerm) ||
      loan.endDate?.toLowerCase().includes(searchTerm) ||
      loan.requestId?.toLowerCase().includes(searchTerm) ||
      loan.status?.toLowerCase().includes(searchTerm) ||
      loan.accountNo?.toLowerCase().includes(searchTerm)
    );
    setFilteredLoans(filtered);
  }, [searchTerm, loans]);

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

  // Function to format amounts
  const formatAmount = (amount) => `₹${parseFloat(amount).toFixed(2)}`;

  return (
    <div>
      <Card sx={{ minWidth: 1180, mb: 2 }}>
        <CardContent>
          <TextField
            label="LoanDetails"
            variant="outlined"
            fullWidth
            onChange={handleSearch}
            value={searchTerm}
            sx={{ mb: 2 }}
          />
        </CardContent>
      </Card>
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
        <TableContainer sx={{ maxHeight: 375 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>Loan No</TableCell>
                <TableCell align='center'>Loan Amount</TableCell>
                <TableCell align='center'>Remaining Amount</TableCell>
                <TableCell align='center'>EMI</TableCell>
                <TableCell align='center'>Loan Name</TableCell>
                <TableCell align='center'>Interest Rate</TableCell>
                <TableCell align='center'>Tenure</TableCell>
                <TableCell align='center'>Asset</TableCell>
                <TableCell align='center'>Value</TableCell>
                <TableCell align='center'>Start Date</TableCell>
                <TableCell align='center'>End Date</TableCell>
                <TableCell align='center'>Request ID</TableCell>
                <TableCell align='center'>Status</TableCell>
                <TableCell align='center'>Account No</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredLoans.length > 0 ? (
                filteredLoans
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((loan) => (
                    <TableRow hover role="checkbox" tabIndex={-1} key={loan.loanNo}>
                      <TableCell align='center'>{loan.loanNo}</TableCell>
                      <TableCell align='center'>{formatAmount(loan.loanAmount)}</TableCell>
                      <TableCell align='center'>{formatAmount(loan.remainingAmount)}</TableCell>
                      <TableCell align='center'>{formatAmount(loan.emi)}</TableCell>
                      <TableCell align='center'>{loan.loanName}</TableCell>
                      <TableCell align='center'>{loan.interestRate}</TableCell>
                      <TableCell align='center'>{loan.tenure}</TableCell>
                      <TableCell align='center'>{loan.asset}</TableCell>
                      <TableCell align='center'>{formatAmount(loan.value)}</TableCell>
                      <TableCell align='center'>{loan.startDate}</TableCell>
                      <TableCell align='center'>{loan.endDate}</TableCell>
                      <TableCell align='center'>{loan.requestId}</TableCell>
                      <TableCell align='center'>{loan.status}</TableCell>
                      <TableCell align='center'>{loan.accountNo}</TableCell>
                    </TableRow>
                  ))
              ) : (
                <TableRow>
                  <TableCell colSpan={14} className="no-content">No content available</TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 100]}
          component="div"
          count={filteredLoans.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
    </div>
  );
}

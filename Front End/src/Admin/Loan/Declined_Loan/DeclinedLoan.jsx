import React, { useState, useEffect } from 'react';
import axios from 'axios';
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
import config from '../../../config';

export default function LoanRequestsDeclined() {
  const [page, setPage] = useState(0);
  const [rows, setRows] = useState([]);
  const [filteredRows, setFilteredRows] = useState([]);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const token = sessionStorage.getItem('token');
  
    axios.get(`${config.url}/bank/admin/getallloandeclined`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })
    .then(response => {
      if (Array.isArray(response.data)) {
        setRows(response.data);
        setFilteredRows(response.data);
      } else {
        console.error('Invalid data format:', response.data);
      }
    })
    .catch(error => console.error('Error fetching loan requests:', error));
  }, []);
  

  useEffect(() => {
    const filtered = rows.filter(row =>
      (row.requestId || "").toLowerCase().includes(searchTerm) ||
      (row.accountNo || "").toLowerCase().includes(searchTerm) ||
      (row.loanAmount || "").toString().toLowerCase().includes(searchTerm) ||
      (row.loanName || "").toLowerCase().includes(searchTerm) ||
      (row.tenure || "").toLowerCase().includes(searchTerm) ||
      (row.status || "").toLowerCase().includes(searchTerm) ||
      (row.asset || "").toLowerCase().includes(searchTerm) ||
      (row.value || "").toString().toLowerCase().includes(searchTerm)
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

  // Function to format amount and value with ₹ symbol
  const formatCurrency = (value) => {
    if (value === null || value === undefined) return "₹0.00";
    return `₹${parseFloat(value).toFixed(2)}`;
  };

  return (
    <div>
      <Card sx={{ minWidth: 1180, mb: 2 }}>
        <CardContent>
          <TextField
            label="Declined Loans"
            variant="outlined"
            fullWidth
            onChange={handleSearch}
            value={searchTerm}
            sx={{ mb: 2 }}
          />
        </CardContent>
      </Card>
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
        <TableContainer sx={{ maxHeight: 800 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>Request ID</TableCell>
                <TableCell align='center'>Account No</TableCell>
                <TableCell align='center'>Loan Amount</TableCell>
                <TableCell align='center'>Loan Name</TableCell>
                <TableCell align='center'>Tenure</TableCell>
                <TableCell align='center'>Status</TableCell>
                <TableCell align='center'>Asset</TableCell>
                <TableCell align='center'>Value</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows.length > 0 ? (
                filteredRows
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row) => (
                    <TableRow hover role="checkbox" tabIndex={-1} key={row.requestId}>
                      <TableCell align='center'>{row.requestId}</TableCell>
                      <TableCell align='center'>{row.accountNo}</TableCell>
                      <TableCell align='center'>{formatCurrency(row.loanAmount)}</TableCell>
                      <TableCell align='center'>{row.loanName}</TableCell>
                      <TableCell align='center'>{row.tenure}</TableCell>
                      <TableCell align='center'>{row.status}</TableCell>
                      <TableCell align='center'>{row.asset}</TableCell>
                      <TableCell align='center'>{formatCurrency(row.value)}</TableCell>
                    </TableRow>
                  ))
              ) : (
                <TableRow>
                  <TableCell colSpan={8} className="no-details">No details available</TableCell>
                </TableRow>
              )}
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
    </div>
  );
}

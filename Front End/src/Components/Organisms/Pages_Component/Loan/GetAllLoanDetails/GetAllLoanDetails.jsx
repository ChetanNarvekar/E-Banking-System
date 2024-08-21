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
import config from '../../../../../config';


export default function GetAllLoanDetails() {
  const accountNo = sessionStorage.getItem("accountNo");
  const [page, setPage] = useState(0);
  const [rows, setRows] = useState([]);
  const [filteredRows, setFilteredRows] = useState([]);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");


  useEffect(() => {
    if (accountNo) {
        const token = sessionStorage.getItem('token');

        axios.get(`${config.url}/bank/user/listOfRequest/${accountNo}`, {
            headers: {
                'Authorization': `Bearer ${token}`, 
                'Content-Type': 'application/json'
            }
        })
        .then((response) => {
            setRows(response.data);
            setFilteredRows(response.data);
        })
        .catch((error) => {
            console.error('Error fetching data:', error);
            toast.error('Failed to fetch loan data');
        });
    }
}, [accountNo]);


  // Filter rows based on search term
  useEffect(() => {
    const filtered = rows.filter(row =>
      row.loanNo?.toLowerCase().includes(searchTerm) ||
      row.loanAmount?.toString().toLowerCase().includes(searchTerm) ||
      row.emi?.toString().toLowerCase().includes(searchTerm) ||
      row.startDate?.toLowerCase().includes(searchTerm) ||
      row.endDate?.toLowerCase().includes(searchTerm) ||
      row.loanName?.toLowerCase().includes(searchTerm) ||
      row.interestRate?.toString().toLowerCase().includes(searchTerm) ||
      row.tenure?.toString().toLowerCase().includes(searchTerm) ||
      row.asset?.toLowerCase().includes(searchTerm) ||
      row.value?.toString().toLowerCase().includes(searchTerm)
    );
    setFilteredRows(filtered);
  }, [searchTerm, rows]);

  // Handle page change
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  // Handle rows per page change
  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  // Handle search input change
  const handleSearch = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  // Function to format amount and balance
  const formatCurrency = (value) => {
    if (value === null || value === undefined) return "₹0.00";
    return `₹${parseFloat(value).toFixed(2)}`;
  };

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
        <TableContainer sx={{ maxHeight: 500 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>LoanNo</TableCell>
                <TableCell align='center'>LoanName</TableCell>
                <TableCell align='center'>LoanAmount</TableCell>
                <TableCell align='center'>Balance</TableCell>
                <TableCell align='center'>EMI</TableCell>
                <TableCell align='center'>InterestRate</TableCell>
                <TableCell align='center'>Tenure</TableCell>
                <TableCell align='center'>Asset</TableCell>
                <TableCell align='center'>Value</TableCell>
                <TableCell align='center'>StartDate</TableCell>
                <TableCell align='center'>EndDate</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row) => (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.loanNo}>
                    <TableCell align='center'>{row.loanNo}</TableCell>
                    <TableCell align='center'>{row.loanName}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.loanAmount)}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.remainingAmount)}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.emi)}</TableCell>
                    <TableCell align='center'>{row.interestRate}%</TableCell>
                    <TableCell align='center'>{row.tenure} months</TableCell>
                    <TableCell align='center'>{row.asset}</TableCell>
                    <TableCell align='center'>{row.value}</TableCell>
                    <TableCell align='center'>{row.startDate}</TableCell>
                    <TableCell align='center'>{row.endDate}</TableCell>
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
    </div>
  );
}


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
import config from '../../../../config';

export default function AccountStatementTable() {
  const accountNo = sessionStorage.getItem("accountNo");
  const [page, setPage] = useState(0);
  const [rows, setRows] = useState([]);
  const [filteredRows, setFilteredRows] = useState([]);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const token = sessionStorage.getItem('token');
  useEffect(() => {
    if (accountNo) {
      axios.get(`${config.url}/bank/user/transactionsofaccount/${accountNo}`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
      }
      })
        .then((response) => {
          setRows(response.data);
          setFilteredRows(response.data);
        })
        .catch((error) => {
          console.error('Error fetching data:', error);
        });
    }
  }, [accountNo]);

  useEffect(() => {
    const filtered = rows.filter(row =>
      row.transactionId?.toLowerCase().includes(searchTerm) ||
      row.createdOn?.toLowerCase().includes(searchTerm) ||
      row.transactionType?.toLowerCase().includes(searchTerm) ||
      row.amount?.toString().toLowerCase().includes(searchTerm) ||
      row.description?.toLowerCase().includes(searchTerm) ||
      row.receiverAccountNo?.toLowerCase().includes(searchTerm) ||
      row.balance?.toString().toLowerCase().includes(searchTerm) ||
      row.paymentId?.toLowerCase().includes(searchTerm) ||
      row.status?.toLowerCase().includes(searchTerm)
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
            label="Transactions"
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
                <TableCell align='center'>TransactionId</TableCell>
                <TableCell align='center'>CreatedOn</TableCell>
                <TableCell align='center'>TransactionType</TableCell>
                <TableCell align='center'>Amount</TableCell>
                <TableCell align='center'>Description</TableCell>
                <TableCell align='center'>ReceiverAccountNo</TableCell>
                <TableCell align='center'>SenderAccountNo</TableCell>
                <TableCell align='center'>Balance</TableCell>
                <TableCell align='center'>PaymentId</TableCell>
                <TableCell align='center'>Status</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row) => (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.transactionId}>
                    <TableCell align='center'>{row.transactionId}</TableCell>
                    <TableCell align='center'>{row.createdOn}</TableCell>
                    <TableCell align='center'>{row.transactionType}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.amount)}</TableCell>
                    <TableCell align='center'>{row.description}</TableCell>
                    <TableCell align='center'>{row.receiverAccountNo}</TableCell>
                    <TableCell align='center'>{row.senderAccountNo}</TableCell>
                    <TableCell align='center'>{formatCurrency(row.balance)}</TableCell>
                    <TableCell align='center'>{row.paymentId}</TableCell>
                    <TableCell align='center'>{row.status}</TableCell>
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


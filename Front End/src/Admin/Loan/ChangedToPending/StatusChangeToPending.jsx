import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TablePagination, TextField, Card, CardContent, Button, CircularProgress } from '@mui/material';
import { toast } from 'react-toastify';
import config from '../../../config';

export default function LoanRequestsWithStatus() {
  const [loanRequests, setLoanRequests] = useState([]);
  const [filteredRows, setFilteredRows] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const token = sessionStorage.getItem('token');

  useEffect(() => {
    const fetchLoanRequests = async () => {
      try {
        const response = await axios.get(`${config.url}/bank/admin/getallloanrequested`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        });

        const data = Array.isArray(response.data) ? response.data : []; // Ensure data is an array
        setLoanRequests(data);
        setFilteredRows(data);
        setError(null);
      } catch (error) {
        console.error('Error fetching loan requests:', error);
        setError('Failed to fetch loan requests');
        toast.error('Failed to fetch loan requests');
      } finally {
        setLoading(false);
      }
    };

    fetchLoanRequests();
  }, [token]);

  useEffect(() => {
    if (Array.isArray(loanRequests)) {
      const filtered = loanRequests.filter(request =>
        request.requestId?.toLowerCase().includes(searchTerm) ||
        request.accountNo?.toLowerCase().includes(searchTerm) ||
        request.loanName?.toLowerCase().includes(searchTerm) ||
        request.tenure?.toLowerCase().includes(searchTerm) ||
        request.status?.toLowerCase().includes(searchTerm)
      );
      setFilteredRows(filtered);
    }
  }, [searchTerm, loanRequests]);

  const updateStatusToPending = (requestId) => {
    axios.patch(`${config.url}/bank/admin/pending/${requestId}`, { status: 'Pending' }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .then(() => {
        toast.success("Request is set to pending");
        setLoanRequests(prevRequests =>
          prevRequests.map(request =>
            request.requestId === requestId ? { ...request, status: 'Pending' } : request
          )
        );
      })
      .catch(error => {
        console.error('Error updating status:', error);
        toast.error('Failed to update status');
      });
  };

  const handleChangePage = (event, newPage) => {
    setCurrentPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setCurrentPage(0);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value.toLowerCase());
  };

  const formatAmount = (amount) => `₹${parseFloat(amount).toFixed(2)}`;

  const currentRequests = filteredRows.slice(currentPage * rowsPerPage, currentPage * rowsPerPage + rowsPerPage);

  if (loading) {
    return <CircularProgress />;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div>
      <Card sx={{ minWidth: 1180, mb: 2 }}>
        <CardContent>
          <TextField
            label="Search Loan Requests"
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
          <Table stickyHeader aria-label="loan requests table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>Request ID</TableCell>
                <TableCell align='center'>Account No</TableCell>
                <TableCell align='center'>Loan Amount</TableCell>
                <TableCell align='center'>Loan Name</TableCell>
                <TableCell align='center'>Tenure</TableCell>
                <TableCell align='center'>Status</TableCell>
                <TableCell align='center'>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={7} align='center' className='no-content'>No content available</TableCell>
                </TableRow>
              ) : (
                currentRequests.map(request => (
                  <TableRow hover role="checkbox" tabIndex={-1} key={request.requestId}>
                    <TableCell align='center'>{request.requestId}</TableCell>
                    <TableCell align='center'>{request.accountNo}</TableCell>
                    <TableCell align='center'>{formatAmount(request.loanAmount)}</TableCell>
                    <TableCell align='center'>{request.loanName}</TableCell>
                    <TableCell align='center'>{request.tenure}</TableCell>
                    <TableCell align='center'>{request.status}</TableCell>
                    <TableCell align='center'>
                      <Button
                        variant="contained"
                        color="primary"
                        onClick={() => updateStatusToPending(request.requestId)}
                      >
                        Set to Pending
                      </Button>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 100]}
          component="div"
          count={filteredRows.length}
          rowsPerPage={rowsPerPage}
          page={currentPage}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
    </div>
  );
}

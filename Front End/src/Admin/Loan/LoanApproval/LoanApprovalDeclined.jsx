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
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import TextField from '@mui/material/TextField';
import CircularProgress from '@mui/material/CircularProgress';
import axios from 'axios';
import { toast } from 'react-toastify';
import config from '../../../config';

export default function LoanRequests() {
  const [loanRequests, setLoanRequests] = useState([]);
  const [filteredRequests, setFilteredRequests] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);

  // Function to fetch loan requests
  const fetchLoanRequests = () => {
    const token = sessionStorage.getItem('token'); 
  
    axios.get(`${config.url}/bank/admin/getallloanpending`, {
      headers: {
        'Authorization': `Bearer ${token}`, 
        'Content-Type': 'application/json'
      }
    })
      .then(response => {
        console.log(response.data); // Log the response to inspect it
        const loans = Array.isArray(response.data) ? response.data : [];
        setLoanRequests(loans);
        setFilteredRequests(loans);
        setLoading(false); // Stop loading after data is fetched
      })
      .catch(error => {
        console.error('Error fetching loan requests:', error);
        setLoading(false); // Stop loading in case of an error
      });
  };
  
  useEffect(() => {
    fetchLoanRequests();
  }, []);

  useEffect(() => {
    if (Array.isArray(loanRequests)) {
      const filtered = loanRequests.filter(request =>
        request.requestId?.toLowerCase().includes(searchTerm) ||
        request.accountNo?.toLowerCase().includes(searchTerm) ||
        request.loanAmount?.toString().toLowerCase().includes(searchTerm) ||
        request.loanName?.toLowerCase().includes(searchTerm) ||
        request.tenure?.toLowerCase().includes(searchTerm) ||
        request.status?.toLowerCase().includes(searchTerm) ||
        request.asset?.toLowerCase().includes(searchTerm) ||
        request.value?.toString().toLowerCase().includes(searchTerm)
      );
      setFilteredRequests(filtered);
    }
  }, [searchTerm, loanRequests]);

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

  const approveLoan = (requestId) => {
    const token = sessionStorage.getItem('token'); 
  
    axios.patch(`${config.url}/bank/admin/approved/${requestId}`, {}, {
      headers: {
        'Authorization': `Bearer ${token}`, 
        'Content-Type': 'application/json' 
      }
    })
      .then(() => {
        toast.success("Request is approved");
        fetchLoanRequests();
      })
      .catch(error => {
        console.error('Error approving loan:', error);
        toast.error("Error approving request");
      });
  };

  const declineLoan = (requestId) => {
    const token = sessionStorage.getItem('token'); 
  
    axios.patch(`${config.url}/bank/admin/declined/${requestId}`, {}, {
      headers: {
        'Authorization': `Bearer ${token}`, 
        'Content-Type': 'application/json'
      }
    })
      .then(() => {
        toast.success("Request is declined");
        fetchLoanRequests(); 
      })
      .catch(error => {
        console.error('Error declining loan:', error);
        toast.error("Error declining request");
      });
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
            label="Update Loan Requests"
            placeholder="Search by Request ID, Account No, Loan Name, etc."
            variant="outlined"
            fullWidth
            onChange={handleSearch}
            value={searchTerm}
            sx={{ mb: 2 }}
          />
        </CardContent>
      </Card>

      {loading ? (
        <CircularProgress />
      ) : (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
          <TableContainer sx={{ maxHeight: 500 }}>
            <Table stickyHeader aria-label="loan requests table">
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
                  <TableCell align='center'>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {filteredRequests.length > 0 ? (
                  filteredRequests
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((request) => (
                      <TableRow hover role="checkbox" tabIndex={-1} key={request.requestId}>
                        <TableCell align='center'>{request.requestId}</TableCell>
                        <TableCell align='center'>{request.accountNo}</TableCell>
                        <TableCell align='center'>{formatCurrency(request.loanAmount)}</TableCell>
                        <TableCell align='center'>{request.loanName}</TableCell>
                        <TableCell align='center'>{request.tenure}</TableCell>
                        <TableCell align='center'>{request.status}</TableCell>
                        <TableCell align='center'>{request.asset}</TableCell>
                        <TableCell align='center'>{formatCurrency(request.value)}</TableCell>
                        <TableCell align='center'>
                          <Button
                            variant="contained"
                            color="success"
                            onClick={() => approveLoan(request.requestId)}
                            sx={{ mr: 1 }}
                          >
                            Approve
                          </Button>
                          <Button
                            variant="contained"
                            color="error"
                            onClick={() => declineLoan(request.requestId)}
                          >
                            Decline
                          </Button>
                        </TableCell>
                      </TableRow>
                    ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={9} align='center'>No content available</TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[10, 25, 100]}
            component="div"
            count={filteredRequests.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Paper>
      )}
    </div>
  );
}

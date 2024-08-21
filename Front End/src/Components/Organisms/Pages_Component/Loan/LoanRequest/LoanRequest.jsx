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
import Button from '@mui/material/Button';
import { toast } from 'react-toastify';
import config from '../../../../../config';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import './style.scss'; // Ensure to import the SCSS file for styling

const LoanRequestsTable = () => {
  const [requests, setRequests] = useState([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [selectedRequestId, setSelectedRequestId] = useState(null);
  const accountNo = sessionStorage.getItem("accountNo");
  const navigate = useNavigate(); // Initialize useNavigate

  useEffect(() => {
    if (accountNo) {
        const token = sessionStorage.getItem('token'); 

        axios.get(`${config.url}/bank/user/getloanrequestsbyacno/${accountNo}`, {
            headers: {
                'Authorization': `Bearer ${token}`, 
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            setRequests(response.data);
        })
        .catch(error => {
            console.error('Error fetching requests:', error);
            toast.error('Failed to fetch loan requests');
        });
    }
}, [accountNo]);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  const handleAddClick = (requestId) => {
    if (requestId) {
      console.log('Adding request with ID:', requestId);
      // Navigate to the add collateral page with the requestId
      sessionStorage.setItem("Req_id",requestId);
      navigate(`/collateralAdd`);
    }
  };

  const handleRowClick = (requestId, status) => {
    if (status === 'P') {
      setSelectedRequestId(requestId);
    } else {
      setSelectedRequestId(null);
    }
  };

  const isAddButtonDisabled = (status) => status !== 'P';

  return (
    <div className="loan-requests-table">
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
        <TableContainer sx={{ maxHeight: 500 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell>Request ID</TableCell>
                <TableCell>Loan Amount</TableCell>
                <TableCell>Loan Duration</TableCell>
                <TableCell>Loan Name</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Updated On</TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {requests
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((request) => (
                  <TableRow
                    key={request.requestId}
                    onClick={() => handleRowClick(request.requestId, request.status)}
                    className={request.status === 'P' ? 'pending' : ''}
                  >
                    <TableCell>{request.requestId}</TableCell>
                    <TableCell>{request.loanAmount}</TableCell>
                    <TableCell>{request.loanDuration}</TableCell>
                    <TableCell>{request.loanName}</TableCell>
                    <TableCell>{request.status}</TableCell>
                    <TableCell>{new Date(request.updatedOn).toLocaleDateString()}</TableCell>
                    <TableCell>
                      <Button
                        variant="contained"
                        color="primary"
                        disabled={isAddButtonDisabled(request.status)}
                        onClick={(e) => {
                          e.stopPropagation(); // Prevent row click event when clicking the button
                          handleAddClick(request.requestId);
                        }}
                      >
                        Add
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 25, 100]}
          component="div"
          count={requests.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={handleChangePage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Paper>
    </div>
  );
};

export default LoanRequestsTable;


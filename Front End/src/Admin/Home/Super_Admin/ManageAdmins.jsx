import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { Button, Table } from 'react-bootstrap';

import './ManageAdmins.css';
import VerifyTpinModal from '../../../Components/Organisms/Pages_Component/Modal/verifyTpinModal';
import config from '../../../config';

const ManageAdmins = () => {
    const [admins, setAdmins] = useState([]);
    const [showTpinModal, setShowTpinModal] = useState(false);
    const [selectedAdminId, setSelectedAdminId] = useState(null);

    const [loading, setLoading] = useState(true);

    useEffect(() => {
      fetchAllAdmins();
    }, []);
  
    const fetchAllAdmins = async () => {
      try {
        const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
        const response = await axios.get(`${config.url}/bank/superadmin/getalladmins`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
          }
        });
        setAdmins(response.data);
        setLoading(false); // Stop the loading state after fetching data
      } catch (error) {
        toast.error('Failed to fetch admins');
        setLoading(false); // Stop the loading state even if there’s an error
      }
    };
  
    const handleDeleteAdmin = (adminId) => {
      setSelectedAdminId(adminId);
      setShowTpinModal(true);
    };
  
    const handleTpinVerification = async () => {
      try {
        const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
        await axios.put(`${config.url}/bank/superadmin/deleteAdmin/${selectedAdminId}`, {}, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
          }
        });
        toast.success('Admin role removed successfully');
        fetchAllAdmins(); // Refresh the list after deletion
      } catch (error) {
        toast.error('Action failed');
      }
    };
  
    if (loading) {
      return <p>Loading...</p>;
    }

    return (
        <div className="manage-admins">
            <h2>Manage Admins</h2>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Admin ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Adhar No</th>
                        <th>PAN No</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {admins.map((admin) => (
                        <tr key={admin.customerId}>
                            <td>{admin.customerId}</td>
                            <td>{admin.firstName}</td>
                            <td>{admin.lastName}</td>
                            <td>{admin.email}</td>
                            <td>{admin.adharNo}</td>
                            <td>{admin.panNo}</td>
                            <td>
                                <Button
                                    variant="danger"
                                    onClick={() => handleDeleteAdmin(admin.customerId)}
                                >
                                    Delete Admin
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <VerifyTpinModal
                show={showTpinModal}
                handleClose={() => setShowTpinModal(false)}
                onVerify={handleTpinVerification}
            />
        </div>
    );
};

export default ManageAdmins;

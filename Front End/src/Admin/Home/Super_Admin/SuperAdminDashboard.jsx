import React from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom'; // Updated import
import './SuperAdminDashboard.css';

const SuperAdminDashboard = () => {
    const navigate = useNavigate(); // Updated hook

    return (
        <div className="main-dashboard">
            <Button
                variant="dark"
                onClick={() => navigate('/manage-admins')} // Updated method
                className="dashboard-button"
            >
                Manage Admins
            </Button>
            <Button
                variant="dark"
                onClick={() => navigate('/add-admin')} // Updated method
                className="dashboard-button"
            >
                Add Admin
            </Button>
            <Button
                variant="dark"
                onClick={() => navigate('/enable-admin')} // Updated method
                className="dashboard-button"
            >
                Enable Admin
            </Button>
            <Button
                variant="dark"
                onClick={() => navigate('/bank-details')} // Updated method
                className="dashboard-button"
            >
                Bank Details
            </Button>
        </div>
    );
};

export default SuperAdminDashboard;

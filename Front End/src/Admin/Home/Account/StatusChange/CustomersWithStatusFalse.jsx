import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './customerList.css';
import config from '../../../../config';

function CustomersWithStatusFalse() {
  const [customers, setCustomers] = useState([]);
  const token = sessionStorage.getItem('token'); // Retrieve the token from session storage
  useEffect(() => {
   

    axios.get(`${config.url}/bank/admin/getallwithstatusfalse`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // Add the token to the Authorization header
      }
    })
    .then(response => {
      setCustomers(response.data);
      console.log(response.data); 
    })
    .catch(error => {
      console.error('Error fetching customers:', error);
    });
  }, []); 

  const updateStatusToTrue = (customerId) => {
   

    axios.patch(`${config.url}/bank/admin/changestatustotrue/${customerId}`, {}, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, 
      }
    })
    .then(() => {
      setCustomers(customers.filter(customer => customer.customerId !== customerId));
    })
    .catch(error => {
      console.error('Error updating status:', error);
    });
  };

  return (
    <div className="customer-list-container">
      <h2>Customers with Status False</h2>
      <table>
        <thead>
          <tr>
            <th>Customer ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Aadhaar No</th>
            <th>PAN No</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {customers.map(customer => (
            <tr key={customer.customerId}>
              <td>{customer.customerId}</td>
              <td>{customer.firstName}</td>
              <td>{customer.lastName}</td>
              <td>{customer.email}</td>
              <td>{customer.adharNo}</td>
              <td>{customer.panNo}</td>
              <td>
                <button onClick={() => updateStatusToTrue(customer.customerId)}>
                  Approve
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default CustomersWithStatusFalse;
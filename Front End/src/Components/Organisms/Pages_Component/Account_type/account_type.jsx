import * as React from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';

// Import images for account types
import savings_account from './saving.jpg';
import minor_account from './minor.jpg';
import current_account from './current.jpg';

export default function AccountTypeTable() {
  // Static data for account types
  const accountTypes = [
    {
      accountName: 'Savings Account',
      imageUrl: savings_account,
      description: 'A savings account is a deposit account held at a financial institution that provides a modest interest rate.',
      minBalance: 500,
    interestRate: 3.25 // Example interest rate
    },
    {
      accountName: 'Minor Account',
      imageUrl: minor_account,
      description: 'A minor account is a type of savings account specifically designed for minors, often with parental oversight.',
      minBalance: 100,
      
      interestRate: 3 // Example interest rate
    },
    {
      accountName: 'Current Account',
      imageUrl: current_account,
      description: 'A current account is a deposit account that provides easy access to funds with minimal interest and no limit on the number of transactions.',
      minBalance: 1000,
     
      interestRate: 1 // Example interest rate
    }
  ];

  return (
    <div>
      <Card sx={{ minWidth: 1180, mb: 2 }}>
        <CardContent>
          <h2>Account Types</h2>
        </CardContent>
      </Card>
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
        <TableContainer sx={{ maxHeight: 500 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell align='center'>Image</TableCell>
                <TableCell align='center'>Account Name</TableCell>
                <TableCell align='center'>Description</TableCell>
                <TableCell align='center'>Min Balance</TableCell>
                {/* <TableCell align='center'>Max Balance</TableCell> */}
                <TableCell align='center'>Interest Rate</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {accountTypes.map((account) => (
                <TableRow hover role="checkbox" tabIndex={-1} key={account.accountName}>
                  <TableCell align='center'>
                    <img src={account.imageUrl} alt={account.accountName} style={{ width: '150px', height: 'auto' }} />
                  </TableCell>
                  <TableCell align='center'>{account.accountName}</TableCell>
                  <TableCell align='center'>{account.description}</TableCell>
                  <TableCell align='center'>{`₹${account.minBalance.toFixed(2)}`}</TableCell>
                  {/* <TableCell align='center'>{`₹${account.maxBalance.toFixed(2)}`}</TableCell> */}
                  <TableCell align='center'>{`${account.interestRate}%`}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </div>
  );
}

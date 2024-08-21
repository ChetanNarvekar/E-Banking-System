import "./style.scss";

const AccountStatementPage = () => {
  return (
    <div className="account-statement-wrapper">
      <div className="account-statement-header">Account Statement</div>
      <div className="account-statement-search">
        <div className="account-statement-text">
          I want Transaction log from
        </div>
        <div>
          <input type="date" id="Start-Date" name="start-date" />
        </div>
        <div>
          To
        </div>
        <div>
          <input type="date" id="End-Date" name="end-date" />
        </div>
        <div className="account-statement-button"><button className="account-statement-search-button" name="search">Search</button></div>
        
      </div>
      <div className="transaction-log">
        <div className="transaction-log-header">Transaction Log</div>
        <div className="transaction-table">
        <table id="myTable" class="display">
    <thead>
        <tr>
            <th>Column 1</th>
            <th>Column 2</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Row 1 Data 1</td>
            <td>Row 1 Data 2</td>
        </tr>
        <tr>
            <td>Row 2 Data 1</td>
            <td>Row 2 Data 2</td>
        </tr>
    </tbody>
</table>
        </div>
      </div>
    
       
  
    </div>
  );
};
export default AccountStatementPage;

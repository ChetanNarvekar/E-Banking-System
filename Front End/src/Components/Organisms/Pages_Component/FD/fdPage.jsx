import "./style.scss";
const FDPage = () => {
  return (
    <div className="fd-wrapper">
      <div className="fd-wrapper-header">Fixed Deposit</div>
      <div className="form-options-fd">
        <form action="">
          <div className="form-options-wrapper">
            <div className="form-options-left-wrapper">
              <div className="required">Account No :</div>
              <div className="required">Interest :</div>
              <div className="required">Term Duration:</div>
              <div className="required">Start Date:</div>
              <div className="required">Maturity Date:</div>
              <div className="required">Iterest Earned:</div>
              <div className="required">Penalty:</div>
              <div className="required">Status:</div>
            </div>
            <div className="form-options-right-wrapper">
              <div>
                <input
                  type="number"
                  id="account-no"
                  name="Account-No"
                  required
                />
              </div>
              <div>
                <input type="number" id="interest" name="interest" required />
              </div>
              <div>
                <input
                  type="number"
                  id="term-duration"
                  name="term-duration"
                  required
                />
              </div>
              <div>
                <input type="date" id="start-date" name="start-date" required />
              </div>
              <div>
                <input
                  type="date"
                  id="maturity-date"
                  name="maturity-date"
                  required
                />
              </div>
              <div>
                <input type="number" id="penalty" name="penalty" required />
              </div>
              <div>
                <input
                  type="number"
                  id="interestEarned"
                  name="interestEarned"
                  required
                />
              </div>
              <div>
                <select id="status">
                  <option value="active">Active</option>
                  <option value="deactive">Deactive</option>
                </select>
              </div>
            </div>
          </div>
        </form>
      </div>
      <div className="conditions">
        <input
          type="checkbox"
          id="coditions"
          name="Terms-and-Conditions"
          required
        />
        <label htmlFor="Terms-and-conditions">
          Accept terms and conditions
        </label>
      </div>
      <div className="fd-button">
        <div className="button">
          <button name="submit" value="Submit">
            Submit
          </button>
        </div>
        <div className="button">
          <button name="back" value="back">
            Back
          </button>
        </div>
      </div>
    </div>
  );
};

export default FDPage;

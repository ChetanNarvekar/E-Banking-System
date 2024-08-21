import "./style.scss";
const MyProfile = () => {
  return (
    <div className="myprofile-wrapper">
      <div className="myprofile-header">My Profile</div>
      <div className="myprofile-details">
        <div className="myprofile-left-wrapper">
          <div className="required">First Name :</div>
          <div className="required">Last Name :</div>
          <div className="required">Address:</div>
          <div className="required">Phone No:</div>
          <div className="required">Email :</div>
          <div className="required">Adhaar No :</div>
          <div className="required">Pan No:</div>
          <div className="required">DOB :</div>
          <div className="required">Gender:</div>
        </div>
        <div className="myprofile-right-wrapper">
          <div>
            <input type="text" id="first-name" name="first-name" value="" />
          </div>

          <div>
            <input type="text" id="last-name" name="last-name" value="" />
          </div>

          <div>
            <input type="text" id="address" name="address" value="" />
          </div>

          <div>
            <input type="tel" id="phone-no" name="phone-no" value="" />
          </div>

          <div>
            <input type="email" id="email" name="email" value="" />
          </div>

          <div>
            <input type="number" id="adhaar-no" name="adhaar-no" value="" />
          </div>

          <div>
            <input type="number" id="pan-no" name="pan-no" value="" />
          </div>
          <div>
            <input type="date" id="DOB" name="DOB" value="" />
          </div>
          <div>
            <input type="radio" id="gender" name="gender" value="Male" />
            <label htmlFor="Male">Male</label>
            <input type="radio" id="gender" name="gender" value="Female" />
            <label htmlFor="Female">Female</label>
          </div>
        </div>
      </div>
      <div className="myprofile-buttons">
        <button className="edit-button" name="Edit">
          Edit
        </button>
        <button className="back-button" name="Back">
          Back
        </button>
      </div>
      <div className="profile-image">
        <img src="" alt="profile-image" />
      </div>
    </div>
  );
};
export default MyProfile;

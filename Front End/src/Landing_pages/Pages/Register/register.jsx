import "./register.scss";
import Header from "../../Components/Header/header";
import Menubar from "../../Components/Menubar/menubar";
import Footer from "../../Components/Footer/footer";
import { calculator, cash, contact, promo1, promo2 } from "../../Assets";
import { useState } from "react";
import DatePicker from "react-date-picker"

const Register = () => {

  const [first_name, setFirstName] = useState(""); 
  const [middle_name, setMiddleName] = useState(""); 
  const [last_name, setLastName] = useState(""); 
  const [date_of_birth, onDateChange] = useState(new Date()); 
  // const [last_name, setLastName] = useState(""); 
  // const [last_name, setLastName] = useState(""); 

  return (
    <div className="home1">
      <Header></Header>
      <Menubar></Menubar>

      <div className="register1">
        <div className="left-register1">
            <h1>Account Registration Form</h1>
        </div>
        <div className="right-register1">
            
        </div>
      </div>
                
      <Footer></Footer>
    </div>
  );
};
export default Register;

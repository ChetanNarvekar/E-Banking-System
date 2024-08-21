import "./home.scss";
import Header from "../../Components/Header/header";
import Menubar from "../../Components/Menubar/menubar";
import Footer from "../../Components/Footer/footer";
import { calculator, cash, contact, promo1, promo2 } from "../../Assets";
import { banking_in_pocket, landing_image } from "../../../Assets";

const Home = () => {
  return (
    <div className="home">
      <Header></Header>
      
      <div className="bank-head">
        <img className="img-bank-head" src={landing_image}></img>
      </div>
      <div className="bank-head-info">
        <div>
          <img className="img-bank-pocket" src={banking_in_pocket}></img>
        </div>
        
        <div className="home-content-para">
          <p style={{width:'200%'}}>
            Welcome to our cutting-edge Online Banking System! Here, financial
            empowerment and technological innovation come together seamlessly.
            Enjoy effortless navigation through your financial journey—initiate
            secure transactions, conveniently deposit funds, and withdraw with
            ease whenever needed.
          </p>
          {/* <p style={{width:'200%'}}>
            Our user-friendly interface ensures a smooth and intuitive
            experience, giving you full control over your finances from the
            comfort of your own device. With advanced security measures in
            place, you can trust that your sensitive information is safeguarded
            throughout every interaction. Join us on this digital financial
            journey and unlock a new era of banking convenience and confidence.
          </p> */}
        </div>
      </div>

      <Footer></Footer>
    </div>
  );
};
export default Home;

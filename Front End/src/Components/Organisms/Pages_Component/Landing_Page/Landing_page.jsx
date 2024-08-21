import "./Landing.scss"
import { jwt,cash, contact } from "../../../../Assets";

const LoanComponenet = () => {
    return (
        <diV>
            
            <div className="header">
                <div className="header-text">
                    <a className="header-links" href=""><h4>Personel</h4></a>
                    <a className="header-links" href=""><h4>Employee</h4></a>
                    <a className="header-links" href=""><h4>Admin</h4></a>
                </div>
                <div className="account-create">
                    <button><h4>Create Account</h4></button>
                </div>
            </div>

            <div className="wrapper">

                <div className="left-wrapper">
                    <img src={jwt}></img>
                    <h4 className="logo-text">JWT <br /> BANK</h4>
                </div>

                <div className="mid-wrapper">
                    <div className="services">
                        <h4 className="service-tags">Bank And Save</h4>
                        <h4 className="service-tags">Home Loans</h4>
                        <h4 className="service-tags">Car loan</h4>
                        <h4 className="service-tags">Personel Loans</h4>
                        <h4 className="service-tags">Schemes</h4>
                        <h4 className="service-tags">Help</h4>
                        <h4 className="service-tags">Contact Us</h4>
                    </div>
                </div>

                <div className="right-wrapper">

                    <button>
                        <div className="login-button"><h4>Log IN </h4></div>
                    </button>
                </div>

            </div>

            <div className="promo">
    
                <div className="promotext">
                    <h1>Under 18! Get your Minor  An Account</h1>
                    <h3>Invest in their future : open a bank account for your child today.</h3>
                    <h4>*No Opening and Maintainanace Charges</h4>
                    <button>Know More</button>
                    <h5>**T&C Applied</h5>
                </div>
            </div>

            

            <div >

                <h1>Help With Your Banking</h1>
                <div className="facilities">
                    <div className="facility-bank facility">
                        <img src={cash}></img>
                        <div>
                            <h2>Banking-</h2>
                            <ul>
                                <li><a href=""><h5>Bank Accounts</h5></a></li>
                                <li><a href=""><h5>Loans</h5></a></li>
                                <li><a href=""><h5>Update Details</h5></a></li>
                            </ul>
                        </div>
                    </div>

                    <div className="facility-calculators facility">
                        <img src={cash}></img>
                        <div>
                            <h2>Calculator-</h2>
                            <ul>
                                <li><a href=""><h5>Home Loan </h5></a></li>
                                <li><a href=""><h5>Personel Loan</h5></a></li>
                                <li><a href=""><h5>Car Loan</h5></a></li>
                            </ul>
                        </div>
                    </div>

                    <div className="ContactUs facility">
                        <img src={contact}></img>
                        <div>
                            <h2>Contact Us-</h2>
                            <ul>
                                <li><a href=""><h5>Contact No: +91 9999999999</h5></a></li>
                                <li><a href=""><h5>Write Us: xyz@gmail.com</h5></a></li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>

            <div className="Footer">
                <div className="Footer-first-div">
                    <div className="logo-and-name">
                        <img src={jwt}></img>
                        <a href=""><h2>JWT Bank</h2></a>
                    </div>

                    <div className="quick-footer">
                        <a href=""><h4>About Us</h4></a>
                        <a href=""><h4>Contact Us</h4></a>
                        <a href=""><h4>Discliamer</h4></a>
                    </div>
                    
                    <div className="footer-bank-description">
                        <hr />
                        <p>For JWT Bank issued products, conditions, fees and charges apply. These may change or we may introduce new ones in the future. Full details are available on request. Lending criteria apply to approval of credit products. This information does not take your personal objectives, circumstances or needs into account. Consider its appropriateness to these factors before acting on it. Read the disclosure documents for your selected product or service, including the Terms and Conditions, before deciding. Target Market Determinations for the products are available. Unless otherwise specified, the products and services described on this website are available only in India from © JWT Bank.</p>
                    </div>

                </div>


            </div>
            
        </diV>
    );
};


export default LoanComponenet;

// C:\Vighnesh\CDAC\ebanking fe\app1\ebanking-fe\src\Assets
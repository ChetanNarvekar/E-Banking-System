package com.app.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AccountDao;
import com.app.dao.AccountTypeDao;
import com.app.dao.BankDao;
import com.app.dao.CustomerDao;
import com.app.dto.CustomerDTO;
import com.app.dto.CustomerReturnDTO;
import com.app.dto.LoginResponseDTO;
import com.app.dto.TransactionHistoryDTO;
import com.app.entity.account.Account;
import com.app.entity.bank.Bank;
import com.app.entity.customer.Customer;
import com.app.entity.enums.Role;
import com.app.exceptions.InvalidTpinException;
import com.app.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BankDao bankDao;

    @Autowired
    private AccountTypeDao accountTypeDao;

    @Autowired 
    private AccountSevice accountService;

    /**
     * Adds a new customer.
     *
     * @param customerDto the CustomerDTO containing customer details.
     * @return a success message.
     */
    @Override
    public String addCustomer(CustomerDTO customerDto) {
        Customer customer = mapper.map(customerDto, Customer.class);
        customerDao.save(customer);
        return "Customer Added Successfully..!";
    }

    /**
     * Updates the email and phone number of a customer.
     *
     * @param customerId the ID of the customer to update.
     * @param newEmail the new email to set.
     * @param newPhoneNumber the new phone number to set.
     * @return a success message.
     */
    @Override
    public String updateCustomerEmailAndPhone(Long customerId, String newEmail, String newPhoneNumber) {
        Customer existingCustomer = customerDao.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        if (newEmail != null && !newEmail.isEmpty()) {
            existingCustomer.setEmail(newEmail);
        }
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty()) {
            existingCustomer.setPhoneNumber(newPhoneNumber);
        }
        customerDao.save(existingCustomer);
        return "Customer Details Updated Successfully!";
    }
    @Override
    public String updateCustomerPassword(Long customerId, String newPassword) {
        Customer existingCustomer = customerDao.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        if (newPassword != null && !newPassword.isEmpty()) {
            existingCustomer.setPassword(newPassword);
        }
        customerDao.save(existingCustomer);
        return "Success";
    }

    /**
     * Retrieves the details of a customer.
     *
     * @param customerId the ID of the customer to retrieve.
     * @return the Customer entity.
     * @throws ResourceNotFoundException if the customer is not found.
     */
    @Override
    public Customer getCustomer(Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
    }

    /**
     * Retrieves all customers with a status of false.
     *
     * @return a list of customers.
     */
    @Override
    public List<CustomerReturnDTO> getCustomersWithStatusFalse() {
        return customerDao.findByStatusFalse().stream().map(customer -> mapper.map(customer, CustomerReturnDTO.class)).collect(Collectors.toList());
    }

    /**
     * Retrieves all customers with an admin role.
     *
     * @return a list of admins.
     */
    @Override
    public List<CustomerReturnDTO> getAllAdmins() {
        return customerDao.findByRole(Role.ROLE_ADMIN).stream().map(customer -> mapper.map(customer, CustomerReturnDTO.class)).collect(Collectors.toList());
    }
    
    @Override
    public List<CustomerReturnDTO> getAllDisabled() {
        return customerDao.findByRole(Role.ROLE_DISABLED).stream().map(customer -> mapper.map(customer, CustomerReturnDTO.class)).collect(Collectors.toList());
    }
    
    @Override
    public List<CustomerReturnDTO> getAllCustomersOnly() {
        return customerDao.findByRole(Role.ROLE_CUSTOMER).stream().map(customer -> mapper.map(customer, CustomerReturnDTO.class)).collect(Collectors.toList());
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of customers.
     */
    @Override
    public List<CustomerReturnDTO> getAllCustomers() {
        return customerDao.findAll().stream().map(customer -> mapper.map(customer, CustomerReturnDTO.class)).collect(Collectors.toList());
    }

    /**
     * Sets the status of a customer to true and creates an account for the customer.
     *
     * @param customerId the ID of the customer to update.
     * @return a success message.
     */
    @Override
    public String setCustomerStatusToTrue(Long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        customer.setStatus(true);
        Bank bank = bankDao.getBankDetails().orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(customer.getDateOfBirth(), currentDate);
        if(age.getYears()< 18)
        	customer.setAccountType("MINOR");
        Account account = accountService.addAccount(customer, bank, customer.getAccountType());
        customerDao.save(customer);
        return "Customer status updated to true!";
    }

    /**
     * Logs in a customer by validating email/phone and password.
     *
     * @param emailOrPhone the email or phone number of the customer.
     * @param password the password of the customer.
     * @return the Customer entity if login is successful.
     * @throws ResourceNotFoundException if the credentials are invalid or account is suspended.
     */
    @Override
    public LoginResponseDTO login(String emailOrPhone, String password) {
        Optional<Customer> customerOpt = customerDao.findByEmail(emailOrPhone);
        if (!customerOpt.isPresent()) {
            customerOpt = customerDao.findByPhoneNumber(emailOrPhone);
        }
        Customer customer = customerOpt.orElseThrow(() -> new ResourceNotFoundException("Invalid email/phone number or password"));

        if (!customer.getPassword().equals(password)) {
            throw new ResourceNotFoundException("Invalid email/phone number or password");
        }
        
        Account account = customer.getAccount();
        if (account != null) {
            boolean isAccountActive = accountService.checkAccountSuspension(account);
            if (!isAccountActive) {
                throw new ResourceNotFoundException("Account is suspended due to inactivity.");
            }
        }
        LoginResponseDTO loginResponseDTO = mapper.map(customer, LoginResponseDTO.class);
        loginResponseDTO.setAccountNo(account.getAccountNo());

        return loginResponseDTO;
    }

    @Override
    public LoginResponseDTO getCustmoerDetail(String emailOrPhone) {
        Optional<Customer> customerOpt = customerDao.findByEmail(emailOrPhone);
        if (!customerOpt.isPresent()) {
            customerOpt = customerDao.findByPhoneNumber(emailOrPhone);
        }
        Customer customer = customerOpt.orElseThrow(() -> new ResourceNotFoundException("Invalid email/phone number"));
        Account account = customer.getAccount();
        if (account != null) {
            boolean isAccountActive = accountService.checkAccountSuspension(account);
            if (!isAccountActive) {
                throw new ResourceNotFoundException("Account is suspended due to inactivity.");
            }
        }
        LoginResponseDTO loginResponseDTO = mapper.map(customer, LoginResponseDTO.class);
        loginResponseDTO.setAccountNo(account.getAccountNo());

        return loginResponseDTO;
    }
    
    /**
     * Creates an admin role for a customer.
     *
     * @param customerId the ID of the customer to update.
     * @return a success message.
     */
    @Override
    public String createAdmin(Long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        customer.setRole(Role.ROLE_ADMIN);
        customer.setStatus(true);
        customerDao.save(customer);
        return "Admin created successfully..!";
    }

    /**
     * Disables an admin role for a customer.
     *
     * @param customerId the ID of the customer to update.
     * @return a success message.
     */
    @Override
    public String deleteAdmin(Long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        customer.setRole(Role.ROLE_DISABLED);
        customerDao.save(customer);
        return "Admin disabled successfully..!";
    }

    /**
     * Verifies the TPIN of a customer.
     *
     * @param customerId the ID of the customer to verify.
     * @param inputTpin the input TPIN to verify.
     * @throws ResourceNotFoundException if the customer or account is not found.
     * @throws InvalidTpinException if the TPIN is invalid.
     */
    @Override
    public void verifyCustomerTpin(Long customerId, String inputTpin) {
    	Customer customer = customerDao.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        boolean verification = (customer.getTpin().equals(inputTpin));
        if(!verification)
        	throw new InvalidTpinException("Invalid TPIN provided");
        return;	
    }

    /**
     * Updates the TPIN of a customer.
     *
     * @param customerId the ID of the customer to update.
     * @param newTpin the new TPIN to set.
     * @throws ResourceNotFoundException if the customer is not found.
     */
    @Override
    public void updateCustomerTpin(Long customerId, String newTpin) {
        Customer customer = customerDao.findById(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        
        customer.setTpin(newTpin);
        customerDao.save(customer);
    }
    
}

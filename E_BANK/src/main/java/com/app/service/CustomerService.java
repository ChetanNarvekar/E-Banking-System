package com.app.service;

import com.app.dto.CustomerDTO;
import com.app.dto.CustomerReturnDTO;
import com.app.dto.LoginResponseDTO;
import com.app.entity.customer.Customer;

import java.util.List;

public interface CustomerService {

    String addCustomer(CustomerDTO customerDto);

    String updateCustomerEmailAndPhone(Long customerId, String newEmail, String newPhoneNumber);

    Customer getCustomer(Long customerId);

    List<CustomerReturnDTO> getCustomersWithStatusFalse();

    String setCustomerStatusToTrue(Long customerId);

	LoginResponseDTO login(String emailOrPhone, String password);

	String createAdmin(Long id);

	List<CustomerReturnDTO> getAllCustomers();

	String deleteAdmin(Long customerId);

	List<CustomerReturnDTO> getAllAdmins();

	void verifyCustomerTpin(Long customerId, String inputTpin);

	void updateCustomerTpin(Long customerId, String newTpin);

	String updateCustomerPassword(Long customerId, String newPassword);

	List<CustomerReturnDTO> getAllDisabled();

	List<CustomerReturnDTO> getAllCustomersOnly();

	LoginResponseDTO getCustmoerDetail(String emailOrPhone);

}

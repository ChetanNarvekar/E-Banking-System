package com.app.loan.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AccountDao;
import com.app.dao.BankDao;
import com.app.dao.TransactionHistoryDao;
import com.app.dto.AccountResponseDTO;
import com.app.dto.AccountTypeDTO;
import com.app.dto.TransactionHistoryDTO;
import com.app.loan.dao.CollateralDao;
import com.app.loan.dao.LoanDao;
import com.app.loan.dao.LoanDetailsDao;
import com.app.loan.dao.RequestDao;

import com.app.loan.dto.RequestDto;
import com.app.loan.dto.RequestResponseDto;
import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.LoanDetailResponse;
import com.app.loan.entities.Collateral;
import com.app.loan.entities.Loan;
import com.app.loan.entities.LoanDetails;
import com.app.loan.entities.LoanPayment;
import com.app.loan.entities.Request;
import com.app.loan.entities.Status;
import com.app.loan.entities.TransactionStatus;
import com.app.loan.exceptions.ResourceNotFoundException;
import com.app.entity.account.Account;
import com.app.entity.bank.Bank;
import com.app.entity.payment.TransactionHistory;


import com.app.miscellaneous.mail.MailSend;
import com.app.miscellaneous.mail.RegistrationMailSender;


@Service
@Transactional
public class RequestServiceImpl implements RequestService{

	@Autowired
	private RequestDao reqDao;
	
	@Autowired
	private AccountDao accDao;
	
	@Autowired
	private LoanDao loanDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CollateralDao collateralDao;
	
	@Autowired
	private BankDao bankDao;
	
	@Autowired
	private LoanDetailsDao loanDetailsDao;
	
	@Autowired
	private TransactionHistoryDao transactionHistoryDao;

	@Override
	public ApiResponse addRequest(RequestDto reqDto) {
		
        // Retrieve the account and loan details based on provided IDs

		Account account = accDao.findById(reqDto.getAccountId()).orElseThrow(()-> new ResourceNotFoundException("Invalid Account Id"));
		LoanDetails loanDetails = loanDetailsDao.findById(reqDto.getLoanType()).orElseThrow(()-> new ResourceNotFoundException("Invalid Loan details Id"));

		if(reqDto.getLoanAmount() > loanDetails.getMinAmount() && reqDto.getLoanAmount() < loanDetails.getMaxAmount() && reqDto.getLoanDuration() < loanDetails.getTenure()) {
        // Map RequestDto to Request entity and associate with account and loan details
			Request req = mapper.map(reqDto, Request.class);
			account.addRequest(req);
			loanDetails.addRequestLoanType(req);
			return new ApiResponse("Added a new Request");
		}else {
			return new ApiResponse("Please review the loan conditions. Please enter valid details");
		}
		
	}


	
	@Override
	public List<LoanDetailResponse> viewPending() {
	    // Fetch all Request entities and handle potential null
	    List<Request> listRequested = Optional.ofNullable(reqDao.findAllByStatusWithPending()).orElse(Collections.emptyList());
	    
	    // Map each Request to RequestResponseDto
	    List<LoanDetailResponse> dto = listRequested.stream()
	        .map(req -> {
	        	LoanDetailResponse response = new LoanDetailResponse(); // Create a new instance of RequestResponseDto
	            
	            // Populate fields in RequestResponseDto from Request object
	            response.setAccountNo(req.getAccount().getAccountNo());
	            response.setLoanAmount(req.getLoanAmount());
	           response.setRequestId(req.getRequestId());
	           response.setStatus(req.getStatus().name());
	           
	            response.setLoanName(req.getDetails().getLoanName()); // Assuming `getDetails()` returns an object with `getLoanName()`
	           
	            response.setTenure(req.getDetails().getTenure()); // Assuming `getDetails()` returns an object with `getTenure()`
	            
	            // Handle collateral details
	            List<Collateral> collaterals = req.getCollaterals(); // Fetch the list of Collateral associated with the Request
	            
	            if (collaterals != null && !collaterals.isEmpty()) {
	                Collateral firstCollateral = collaterals.get(0); // Assuming you want to use the first collateral
	                response.setAsset(firstCollateral.getAsset()); // Set asset from the first Collateral
	                response.setValue(firstCollateral.getValue()); // Set value from the first Collateral
	            } else {
	                response.setAsset("N/A"); // Or handle cases where no collateral is present
	                response.setValue("N/A");
	            }
	            
	            return response; // Return the populated RequestResponseDto
	        })
	        .collect(Collectors.toList()); // Collect results into a List
	    
	    return dto;
	}

	@Override
	public List<RequestResponseDto> viewRequested() {
	    // Fetch all Request entities
	    List<Request> listRequested = reqDao.findAllByStatusWithRequested();
	    
	    // Map each Request to RequestResponseDto
	    List<RequestResponseDto> dto = listRequested.stream()
	        .map(req -> {
	            RequestResponseDto response = new RequestResponseDto(); // Create a new instance of RequestResponseDto
	            response.setLoanAmount(req.getLoanAmount());
	            response.setLoanName(req.getDetails().getLoanName());
	            response.setRequestId(req.getRequestId());
	            response.setStatus(req.getStatus());
	            response.setTenure(req.getDetails().getTenure());
	            response.setAccountNo(req.getAccount().getAccountNo());
	           
	            return response; // Return the populated RequestResponseDto
	        })
	        .collect(Collectors.toList()); // Collect results into a List
	    
	    return dto;
	}

	@Override
	public List<Request> viewApproved() {
		// TODO Auto-generated method stub
		return reqDao.findAllByStatusWithApproved();
	}

	@Override
	public List<LoanDetailResponse> viewDeclined() {
	    // Fetch all Request entities and handle potential null
	    List<Request> listRequested = Optional.ofNullable(reqDao.findAllByStatusWithDeclined()).orElse(Collections.emptyList());
	    
	    // Filter for declined requests
	    List<LoanDetailResponse> dto = listRequested.stream()
	        .filter(req -> "DECLINED".equals(req.getStatus().name())) 
	        .map(req -> {
	            LoanDetailResponse response = new LoanDetailResponse(); 
	            
	            // Populate fields in LoanDetailResponse from Request object
	            response.setAccountNo(req.getAccount().getAccountNo());
	            response.setLoanAmount(req.getLoanAmount());
	            response.setRequestId(req.getRequestId());
	            response.setStatus(req.getStatus().name());
	            
	            response.setLoanName(req.getDetails().getLoanName()); 
	            response.setTenure(req.getDetails().getTenure()); 
	            
	            // Handle collateral details
	            List<Collateral> collaterals = req.getCollaterals(); 
	            
	            if (collaterals != null && !collaterals.isEmpty()) {
	                Collateral firstCollateral = collaterals.get(0); 
	                response.setAsset(firstCollateral.getAsset()); 
	                response.setValue(firstCollateral.getValue()); 
	            } else {
	                response.setAsset("N/A"); 
	                response.setValue("N/A");
	            }
	            
	            return response; 
	        })
	        .collect(Collectors.toList());
	    
	    return dto;
	}

	@Override
	public ApiResponse updateToPending(String requestId) {
		// TODO Auto-generated method stub
		Optional<Request> optionalEntity = reqDao.findById(requestId);
        if (optionalEntity.isPresent()) {
            Request entity = optionalEntity.get();
            if(entity.getStatus() == Status.R) {
            	entity.setStatus(Status.P);
            	reqDao.save(entity);
            	
            	RegistrationMailSender afterRequest = new RegistrationMailSender(entity.getAccount().getCustomer().getEmail(),entity.getAccount().getAccountNo(), entity.getRequestId(), entity.getAccount().getBank().getBankName());
        		MailSend.sendEmail(afterRequest.getMessage(), afterRequest.getSubject(), afterRequest.getTo());
            	
        		return new ApiResponse("Succeesfully Updated to Pending");
            }else {
            	if(entity.getStatus() == Status.A) {
            		return new ApiResponse("Loan for given request is already Approved !");
            	}else if(entity.getStatus() == Status.D){
            		return new ApiResponse("Loan for given request is Declined !");
            	}else {
            		return new ApiResponse("Given Loan request is already marked as Pending !");
            	}
            }
        } else {
            // Handle the case where the entity is not found
            throw new RuntimeException("Entity not found with id: " + requestId);
        }
	}

	@Override
	public ApiResponse updateToApproved(String requestId) {
		// TODO Auto-generated method stub
		Optional<Request> optionalEntity = reqDao.findById(requestId);
         
        if (optionalEntity.isPresent()) {
        	
            Request entity = optionalEntity.get();
            if(entity.getStatus()  == Status.P) {
            	
            	entity.setStatus(Status.A);
            	reqDao.save(entity);
            	
                // Retrieve collateral, account, and loan details
            	Collateral collateral = collateralDao.findByRequest(entity);
              	Account account = accDao.findById(entity.getAccount().getAccountNo()).orElseThrow(()-> new ResourceNotFoundException("Account Not found with given request id"));
              	LoanDetails loanDetails = loanDetailsDao.findById(entity.getDetails().getLoanName()).orElseThrow(()-> new ResourceNotFoundException("Loan details Found "));
              	
                // Calculate interest and create a new loan
              	float interest = entity.getLoanAmount()*entity.getLoanDuration()*(entity.getDetails().getInterestRate()/100);
              	Loan loan = new Loan(account, (entity.getLoanAmount()+interest), ((entity.getLoanAmount()+interest)/entity.getLoanDuration()), LocalDate.now(), LocalDate.now().plusMonths(entity.getLoanDuration()), loanDetails, collateral );
              	Loan forLoanId = loanDao.save(loan);
              	LoanPayment loanPayment = new LoanPayment(forLoanId, ((entity.getLoanAmount()+interest)/entity.getLoanDuration()), (entity.getLoanAmount()+interest), TransactionStatus.CREDIT);
              	forLoanId.addLoanPayment(loanPayment);
                

              	//Fund transfer to account
              	account.setBalance(account.getBalance()+entity.getLoanAmount());
                TransactionHistory transactionHistory  = new TransactionHistory();
                account.addTransaction(entity, loanDetails,transactionHistory);
                transactionHistory.setLoanPayment(loanPayment);
                
                //Bank fund management
              	Bank bank = account.getBank();
              	bank.subtractFundAvailable(entity.getLoanAmount());
              	bank.addLoanDisbursed(entity.getLoanAmount());
              	bank.addLoanExpected(entity.getLoanAmount()+interest);
              	
              	bankDao.save(bank);
              	accDao.save(account);
              	transactionHistoryDao.save(transactionHistory);
              	
            	RegistrationMailSender afterApproved = new RegistrationMailSender(account.getCustomer().getEmail(), account.getAccountNo(), entity.getRequestId(), account.getBank().getBankName(), forLoanId.getLoanNo());
        		MailSend.sendEmail(afterApproved.getMessage(), afterApproved.getSubject(), afterApproved.getTo());

              	
             	return new ApiResponse("Your Loan Request is approved , You will recieve money in short time.....");
            }else {
            	if(entity.getStatus() == Status.A) {
            		return new ApiResponse("Loan for given request is already Approved !");
            	}else if(entity.getStatus() == Status.D){
            		return new ApiResponse("Loan for given request is Declined !");
            	}else {
            		return new ApiResponse("Given Loan request is marked as Requested !");
            	}
            }
            
        } else {
            // Handle the case where the entity is not found
            throw new RuntimeException("Entity not found with id: " + requestId);
        }
	}



	@Override
	public ApiResponse updateToDeclined(String requestId) {
		// TODO Auto-generated method stub
		Optional<Request> optionalEntity = reqDao.findById(requestId);
        if (optionalEntity.isPresent()) {
            Request entity = optionalEntity.get();
            if(entity.getStatus()==Status.P | entity.getStatus() == Status.R) {
            	entity.setStatus(Status.D);
            	reqDao.save(entity);
            	
            	RegistrationMailSender afterDeclined = new RegistrationMailSender(entity.getAccount().getCustomer().getEmail(), entity.getRequestId(), entity.getAccount().getBank().getBankName());
        		MailSend.sendEmail(afterDeclined.getMessage(), afterDeclined.getSubject(), afterDeclined.getTo());
            	
        		return new ApiResponse("Request is declined..  ");
            }else {
            	if(entity.getStatus() == Status.A) {
            		return new ApiResponse("Can't delete approved requests");
            	}else {
            		return new ApiResponse("Given request is already marked as declined");
            	}
            }
        } else {
            // Handle the case where the entity is not found
            throw new RuntimeException("Entity not found with id: " + requestId);
        }
	}

	
	   


	



@Override
public List<LoanDetailResponse> getListOfLoansByAccount(String accountNo) {
    Optional<Account> account = accDao.findById(accountNo);
    if (account.isPresent()) {
        Account account2 = account.get();
        List<Loan> loans = account2.getLoan();

        if (loans.isEmpty()) {
            return List.of(); // Return an empty list instead of null
        } else {
            // Assuming you have a ModelMapper instance configured
            ModelMapper modelMapper = new ModelMapper();
            return loans.stream()
                        .map(loan -> {
                            // Map loan to LoanDetailResponse
                            LoanDetailResponse response = modelMapper.map(loan, LoanDetailResponse.class);
                            
                            // Fetch and set collateral details
                            Collateral collateral = loan.getCollateralId(); // Replace with actual method to fetch collateral
                            if (collateral != null) {
                                response.setAsset(collateral.getAsset()); // Replace with actual getter
                                response.setValue(collateral.getValue()); // Replace with actual getter
                            } else {
                                response.setAsset("No Asset"); // or any default value
                                response.setValue("No Asset"); // or any default value
                            }
                            
                            return response;
                        })
                        .collect(Collectors.toList());
        }
    } else {
        return List.of(); // Return an empty list instead of null
    }
}
@Override
public List<RequestResponseDto> getAllRequestsByAccountNo(String accountNo) {
    List<Request> requests = reqDao.findByAccountAccountNo(accountNo);
    
    // Convert List<Request> to List<RequestResponseDto>
    return requests.stream().map(request -> {
        RequestResponseDto dto = new RequestResponseDto();
        dto.setRequestId(request.getRequestId());
        dto.setLoanAmount(request.getLoanAmount());
        dto.setLoanDuration(request.getLoanDuration());
        dto.setLoanName(request.getDetails().getLoanName());
        dto.setStatus(request.getStatus());
        dto.setCreatedOn(request.getCreatedOn());
        dto.setUpdatedOn(request.getUpdatedOn());
        return dto;
    }).collect(Collectors.toList());
}
@Override
public List<LoanDetailResponse> getAllRequests() {
    List<Request> requests = reqDao.findAll();

    List<String> accountNumbers = requests.stream()
            .map(req -> req.getAccount().getAccountNo())
            .distinct()
            .collect(Collectors.toList());

    List<Loan> loans = loanDao.findLoansByAccountNumbers(accountNumbers);

    // Map loans to LoanDetailResponse and return
    return loans.stream()
            .map(loan -> {
                LoanDetailResponse response = new LoanDetailResponse();
                response.setLoanNo(loan.getLoanNo());
                response.setLoanAmount(loan.getLoanAmount());
                response.setRemainingAmount(loan.getRemainingAmount());
                response.setEmi(loan.getEmi());
                response.setStartDate(loan.getStartDate() != null ? loan.getStartDate().toString() : "N/A");
                response.setEndDate(loan.getEndDate() != null ? loan.getEndDate().toString() : "N/A");
                response.setLoanName(loan.getLoanDetails().getLoanName());

                // Fetch associated request and account details
                Optional<Request> associatedRequest = requests.stream()
                        .filter(req -> req.getAccount().getAccountNo().equals(loan.getAccount().getAccountNo()))
                        .findFirst();

                if (associatedRequest.isPresent()) {
                    Request request = associatedRequest.get();
                    response.setRequestId(request.getRequestId());
                    response.setStatus(request.getStatus().name());
                    response.setAccountNo(request.getAccount() != null ? request.getAccount().getAccountNo() : "N/A");
                    
                    // Populate collateral details if available
                    if (request.getCollaterals() != null && !request.getCollaterals().isEmpty()) {
                        Collateral collateral = request.getCollaterals().get(0); // Assuming only one collateral for simplicity
                        response.setAsset(collateral.getAsset());
                        response.setValue(collateral.getValue());
                    } else {
                        response.setAsset("No Asset");
                        response.setValue("No Asset");
                    }
                } else {
                    response.setRequestId("N/A");
                    response.setStatus("Unknown");
                    response.setAccountNo("N/A");
                    response.setAsset("No Asset");
                    response.setValue("No Asset");
                }

                return response;
            })
            .collect(Collectors.toList());
}





	}

	



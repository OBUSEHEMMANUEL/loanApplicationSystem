package com.project.loanapplicationsystem.service;

import com.project.loanapplicationsystem.data.dto.register.LoanRequest;
import com.project.loanapplicationsystem.data.dto.response.SucessResponse;
import com.project.loanapplicationsystem.data.model.Customer;
import com.project.loanapplicationsystem.data.model.LoanApplication;
import com.project.loanapplicationsystem.data.model.enums.ApplicationStatus;
import com.project.loanapplicationsystem.data.repostory.LoanApplicationRepository;
import com.project.loanapplicationsystem.exception.ResourceException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.project.loanapplicationsystem.data.model.enums.ApplicationStatus.*;

@AllArgsConstructor
@Service
public class LoanApplicationServiceImp implements LoanApplicationService {

    private final LoanApplicationRepository loanRepository;
    private final CustomerService service;
    private final ModelMapper modelMapper;

    @Override
    public SucessResponse loanApplication(LoanRequest request) throws ResourceException {
        Customer foundCustomer = service.findById(request.getCustomerId()).orElseThrow(() -> new ResourceException("CUSTOMER NOT FOUND"));
        LoanApplication application = modelMapper.map(request, LoanApplication.class);

        application.setApplicationStatus(ApplicationStatus.IN_PROGRESS);
        application.setDateApplied(LocalDateTime.now());
        LoanApplication savedApplication = loanRepository.save(application);
        foundCustomer.getLoanApplication().add(savedApplication);


        return SucessResponse.builder()
                .message("Loan Application Submitted Successfully")
                .StatusCode(HttpStatus.ACCEPTED.value())
                .build();
    }

    @Override
    public LoanApplication acceptLoanApplication(UUID loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(String.valueOf(loanApplicationId)).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == ACCEPTED) {
            throw new ResourceException("Loan has already been Accepted");
        }
        loanApplication.setApplicationStatus(ACCEPTED);
        loanApplication.setDateAccepted(LocalDateTime.now());
        return loanRepository.save(loanApplication);
    }

    @Override
    public LoanApplication rejectedLoanApplication(UUID loanApplicationId) throws ResourceException {
        LoanApplication loanApplication = loanRepository.findById(String.valueOf(loanApplicationId)).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == REJECTED) {
            throw new ResourceException("Loan has already been Rejected");
        }
        loanApplication.setApplicationStatus(REJECTED);
        loanApplication.setDateRejected(LocalDateTime.now());
        return loanRepository.save(loanApplication);
    }

    @Override
    public LoanApplication closeLoanApplication(UUID loanApplicationId) throws ResourceException {

        LoanApplication loanApplication = loanRepository.findById(String.valueOf(loanApplicationId)).orElseThrow(() -> new ResourceException("Application not found"));
        if (loanApplication.getApplicationStatus() == CLOSED) {
            throw new ResourceException("Loan has already been Closed");
        }
        loanApplication.setApplicationStatus(CLOSED);
        loanApplication.setDateClosed(LocalDateTime.now());
        return loanRepository.save(loanApplication);
    }
    @Override
    public LoanApplication viewLoanApplicationStatus(UUID loanApplication) throws ResourceException {
        return loanRepository.findById(String.valueOf(loanApplication))
                .orElseThrow(()->new ResourceException("id not found"));
    }

    @Override
    public List<LoanApplication> viewLoanApplication() {
        return loanRepository.findAll();
    }


    public ResponseEntity<?>  viewLoanAgreement(UUID loanApplicationId) throws ResourceException {
      LoanApplication loanApplication = loanRepository.findById(String.valueOf(loanApplicationId))
                .orElseThrow(()->new ResourceException("Loan not found"));

        if (loanApplication.getApplicationStatus() != ACCEPTED){
            throw new ResourceException("Loan application not found");
        }
        return null;

    }

//    public ResponseEntity<byte[]> viewLoanAgreement(UUID loanApplicationId) throws ResourceException {
//        LoanApplication loanApplication = loanRepository.findById(loanApplicationId)
//                .orElseThrow(() -> new ResourceException("Loan application not found"));
//
//        if (loanApplication.getApplicationStatus() != ApplicationStatus.ACCEPTED) {
//            throw new ResourceException("Loan application is not approved");
//        }
//
//        // Retrieve the loan agreement document (Assuming it is stored as a byte array)
//        byte[] loanAgreementDocument = loanAgreementService.getLoanAgreementDocument(loanApplicationId);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDisposition(ContentDisposition.attachment()
//                .filename("loan_agreement.pdf")
//                .build());
//
//        return new ResponseEntity<>(loanAgreementDocument, headers, HttpStatus.OK);
//    }


}

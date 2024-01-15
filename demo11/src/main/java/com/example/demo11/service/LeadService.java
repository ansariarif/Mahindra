package com.example.demo11.service;

import com.example.demo11.exception.ErrorResponse;
import com.example.demo11.exception.LeadValidationException;
import com.example.demo11.model.Lead;
import com.example.demo11.model.Response;
import com.example.demo11.repository.LeadRepository;
import com.example.demo11.validation.LeadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.demo11.exception.CasConstant.*;

@Service
public class LeadService {
    @Autowired
    private LeadRepository leadRepository;

    public Response createLead(Lead lead) {
        LeadValidator.validateLead(lead);
        Response response = null;
        Optional<Lead> lead2 =  leadRepository.findById(lead.getId());
        if (lead2.isPresent()){
          throw new LeadValidationException(LEAD_EXIST);
        }else {
            Lead lead1 = leadRepository.save(lead);
            if (Objects.nonNull(lead1)) {
                response = Response.builder().status(STATUS_SUCCESS).data(DATA).build();
            }
            return response;
        }


    }


    public ResponseEntity<Response> getLeadsByMobileNumber(String mobileNumber) {
        List<Lead> leads=  leadRepository.findByMobileNumber(mobileNumber);
        if (leads.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("E10011", Collections.singletonList(NO_LEAD_EXIST + mobileNumber));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(STATUS_ERROR,null, errorResponse));
        } else {
            return ResponseEntity.ok().body(new Response("success", leads , null));
        }
    }
}

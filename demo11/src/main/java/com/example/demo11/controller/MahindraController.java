package com.example.demo11.controller;

import com.example.demo11.exception.ErrorResponse;
import com.example.demo11.exception.LeadValidationException;
import com.example.demo11.model.Lead;
import com.example.demo11.model.Response;
import com.example.demo11.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.example.demo11.exception.CasConstant.*;

@RestController
public class MahindraController {

    @Autowired
    private LeadService leadService;

    @PostMapping("/lead")
    public ResponseEntity<Response> createLeads(@RequestBody Lead lead) {
        try {
            return ResponseEntity.ok(leadService.createLead(lead));
        }catch (LeadValidationException e){
            ErrorResponse errorResponse = new ErrorResponse("E10010", Collections.singletonList(LEAD_EXIST));
            return ResponseEntity.status(400).body(new Response(STATUS_ERROR,null, errorResponse));
         }
    }


    @GetMapping("/mobileNumber/{mobileNumber}")
    public ResponseEntity<Response> getLeadsByMobileNumber(@PathVariable String mobileNumber) {
        return leadService.getLeadsByMobileNumber(mobileNumber);
    }


}
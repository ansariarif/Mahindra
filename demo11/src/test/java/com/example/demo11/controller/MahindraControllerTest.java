package com.example.demo11.controller;

import com.example.demo11.exception.ErrorResponse;
import com.example.demo11.exception.LeadValidationException;
import com.example.demo11.model.Lead;
import com.example.demo11.model.Response;
import com.example.demo11.service.LeadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MahindraController.class, useDefaultFilters = false)
class MahindraControllerTest {

    @Mock
    private LeadService leadService;

    @InjectMocks
    private MahindraController leadController;

    @Test
    public void testCreateLeadsSuccess() throws LeadValidationException {
        Lead lead = new Lead();
        when(leadService.createLead(any(Lead.class))).thenReturn(new Response("success", "Lead created successfully", null));
        ResponseEntity<Response> responseEntity = leadController.createLeads(lead);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals("Lead created successfully", responseEntity.getBody().getData());
    }

    @Test
    public void testCreateLeadsValidationException() throws LeadValidationException {

        Lead lead = new Lead();
        when(leadService.createLead(any(Lead.class))).thenThrow(new LeadValidationException("LEAD_EXIST"));
        ResponseEntity<Response> responseEntity = leadController.createLeads(lead);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
    }


    @Test
    public void testGetLeadsByMobileNumberSuccess() {
        String mobileNumber = "1234567890";
        List<Lead> leads = new ArrayList<>();
        Response successResponse = new Response("success", leads, null);
        when(leadService.getLeadsByMobileNumber(mobileNumber)).thenReturn(new ResponseEntity<>(successResponse, HttpStatus.OK));
        ResponseEntity<Response> responseEntity = leadController.getLeadsByMobileNumber(mobileNumber);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("success", responseEntity.getBody().getStatus());
        assertEquals(leads, responseEntity.getBody().getData());
    }

    @Test
    public void testGetLeadsByMobileNumberNoLeadsFound() {
        String mobileNumber = "1234567890";
        ErrorResponse errorResponse = new ErrorResponse("E10011", Collections.singletonList("No leads found"));
        when(leadService.getLeadsByMobileNumber(mobileNumber)).thenReturn(new ResponseEntity<>(new Response("error", null, errorResponse), HttpStatus.NOT_FOUND));
        ResponseEntity<Response> responseEntity = leadController.getLeadsByMobileNumber(mobileNumber);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody().getStatus());
        assertEquals(errorResponse, responseEntity.getBody().getErrorResponse());
    }


}
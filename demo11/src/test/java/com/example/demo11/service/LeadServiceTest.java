package com.example.demo11.service;

import com.example.demo11.exception.ErrorResponse;
import com.example.demo11.exception.LeadValidationException;
import com.example.demo11.model.Lead;
import com.example.demo11.model.Response;
import com.example.demo11.repository.LeadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LeadServiceTest {
    @Mock
    private LeadRepository leadRepository;

    @InjectMocks
    private LeadService leadService;


    @Test
    public void testCreateLead_Success() {
        Lead lead = new Lead();
        lead.setId(1);
        lead.setFirstName("John");
        lead.setLastName("Doe");
        lead.setMobileNumber("9876543210");
        lead.setGender("Male");
        lead.setDob("14/03/2000");
        lead.setEmail("john.doe@example.com");
        when(leadRepository.findById(lead.getId())).thenReturn(Optional.empty());
        when(leadRepository.save(lead)).thenReturn(lead);
        Response response = leadService.createLead(lead);
        assertNotNull(response);
        assertEquals(Response.builder().status("success").data("Created Leads Successfully").build(), response);
    }

    @Test
    public void testCreateLead_LeadExists() {
        Lead lead = new Lead();
        lead.setId(2);
        lead.setFirstName("John");
        lead.setLastName("Doe");
        lead.setMobileNumber("9876543210");
        lead.setGender("Male");
        lead.setDob("14/03/2000");
        lead.setEmail("john.doe@example.com");
        when(leadRepository.findById(lead.getId())).thenReturn(Optional.of(lead));

        LeadValidationException exception = assertThrows(LeadValidationException.class, () -> leadService.createLead(lead));
        assertEquals("Lead Already Exists in the database with the lead id", exception.getMessage());
    }

    @Test
    public void testGetLeadsByMobileNumber_NoLeadsFound() {
        String mobileNumber = "9876543210";
        when(leadRepository.findByMobileNumber(mobileNumber)).thenReturn(Collections.emptyList());

        ResponseEntity<Response> responseEntity = leadService.getLeadsByMobileNumber(mobileNumber);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Response responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("error", responseBody.getStatus());
        assertNull(responseBody.getData());
        assertNotNull(responseBody.getErrorResponse());

        // Check the error code and message
        ErrorResponse errorResponse = responseBody.getErrorResponse();
        assertEquals("E10011", errorResponse.getCode());
    }

    @Test
    public void testGetLeadsByMobileNumber_LeadsFound() {
        String mobileNumber = "9876543210";
        List<Lead> leads = Arrays.asList(
                new Lead(1, "John", null, "Doe", "9876543210", "Male", "1990-01-01", "john@example.com"),
                new Lead(2, "Jane", null, "Smith", "9876543210", "Female", "1992-03-15", "jane@example.com")
        );
        when(leadRepository.findByMobileNumber(mobileNumber)).thenReturn(leads);
        ResponseEntity<Response> responseEntity = leadService.getLeadsByMobileNumber(mobileNumber);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Response responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals("success", responseBody.getStatus());
        assertEquals(leads, responseBody.getData());
        assertNull(responseBody.getErrorResponse());
    }



}
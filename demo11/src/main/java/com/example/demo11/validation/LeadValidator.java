package com.example.demo11.validation;

import com.example.demo11.model.Lead;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LeadValidator {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    public static void validateLead(Lead lead) {
        validateLeadId(lead.getId());
        validateFirstName(lead.getFirstName());
        validateMiddleName(lead.getMiddleName());
        validateLastName(lead.getLastName());
        validateMobileNumber(lead.getMobileNumber());
        validateGender(lead.getGender());
        validateDOB(lead.getDob());
        validateEmail(lead.getEmail());
    }

    private static void validateLeadId(int leadId) {
        if (leadId <= 0) {
            throw new IllegalArgumentException("LeadId should be a positive integer");
        }
    }

    private static void validateFirstName(String firstName) {
        if (StringUtils.isBlank(firstName) || !firstName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("FirstName should contain only alphabets and be mandatory");
        }
    }

    private static void validateMiddleName(String middleName) {
        if (middleName != null && !middleName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("MiddleName should contain only alphabets");
        }
    }

    private static void validateLastName(String lastName) {
        if (StringUtils.isBlank(lastName) || !lastName.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("LastName should contain only alphabets and be mandatory");
        }
    }

    private static void validateMobileNumber(String mobileNumber) {
        if (StringUtils.isBlank(mobileNumber) || !mobileNumber.matches("^[6-9]\\d{9}$")) {
            throw new IllegalArgumentException("MobileNumber should be a 10-digit number starting with 6, 7, 8, or 9");
        }
    }

    private static void validateGender(String gender) {
        if (StringUtils.isBlank(gender) || !gender.matches("^(Male|Female|Others)$")) {
            throw new IllegalArgumentException("Gender should be Male, Female, or Others and be mandatory");
        }
    }

    public static void validateDOB(String dob) throws IllegalArgumentException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dob);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use dd/mm/yyyy");
        }
    }

    private static void validateEmail(String email) {
        if (StringUtils.isNotBlank(email) && !EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException("Email should be valid");
        }
    }
}

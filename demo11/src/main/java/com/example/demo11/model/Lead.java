package com.example.demo11.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "leads")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lead {

    @Id
    @NotNull(message = "LeadId is mandatory")
    @Min(value = 1, message = "LeadId should be a positive integer")
    private int id;

    @NotNull(message = "FirstName is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "FirstName should contain only alphabets")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "MiddleName should contain only alphabets")
    private String middleName;

    @NotNull(message = "LastName is mandatory")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "LastName should contain only alphabets")
    private String lastName;

    @NotNull(message = "MobileNumber is mandatory")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "MobileNumber should be a 10-digit number starting with 6, 7, 8, or 9")
    private String  mobileNumber;

    @NotNull(message = "Gender is mandatory")
    @Pattern(regexp = "^(Male|Female|Others)$", message = "Gender should be Male, Female, or Others")
    private String gender;

    @NotNull(message = "DOB is mandatory")
    private String dob;

    @Email(message = "Email should be valid")
    private String email;
}

package com.example.demo11.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class CasConstant {

    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCESS = "success";
    public static final String LEAD_EXIST = "Lead Already Exists in the database with the lead id";
    public static final String NO_LEAD_EXIST = "No Lead found with the Mobile Number ";
    public static final String DATA = "Created Leads Successfully";

}

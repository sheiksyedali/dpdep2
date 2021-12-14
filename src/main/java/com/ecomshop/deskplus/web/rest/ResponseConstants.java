package com.ecomshop.deskplus.web.rest;

/**
 * Author: Sheik Syed Ali
 * Date: 26 Sep 2021
 */
public class ResponseConstants {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static final int ERR_USER_EXIST = 1001;
    public static final String ERR_USER_EXIST_MSG = "User already exist. Try with different email id";

    public static final int ERR_SIGNUP_FAILED = 1002;
    public static final String ERR_SIGNUP_FAILED_MSG = "Account creation failed. Please contact support";

    public static final int ERR_SIGNUP_AUTH_FAILED = 1003;
    public static final String ERR_SIGNUP_AUTH_FAILED_MSG = "Token creation failed during signup. Please contact support";

    public static final int ERR_LOGIN_FAILED = 1004;
    public static final String ERR_LOGIN_FAILED_MSG = "Login failed. Contact Support";

    public static final int ERR_LOGIN_USER_NOT_FOUND = 1005;
    public static final String ERR_LOGIN_USER_NOT_FOUND_MSG = "Login failed. User not found!";

    public static final int ERR_STRIPE_CUSTOMER_CREATION_FAILED = 1006;
    public static final String ERR_STRIPE_CUSTOMER_CREATION_FAILED_MSG = "Stripe - Customer creation failed";

    public static final int ERR_SIGNUP_PLAN_NOT_FOUND = 1007;
    public static final String ERR_SIGNUP_PLAN_NOT_FOUND_MSG = "Plan not found!";

    public static final int ERR_SIGNUP_STRIPE_SUBSCRIPTION_CREATION_FAILED =  1008;
    public static final String ERR_SIGNUP_STRIPE_SUBSCRIPTION_CREATION_FAILED_MSG =  "Stripe - Subscription creation failed!";

    public static final int ERR_INVALID_ACTIVATION = 1009;
    public static final String ERR_INVALID_ACTIVATION_MSG = "Invalid activation link";

    public static final String ACCOUNT_ACTIVATION_SUCCEEDED = "Account activation succeeded";

    public static final int ERR_INVALID_CREATED_BY_USER = 1010;
    public static final String ERR_INVALID_CREATED_BY_USER_MSG = "Invalid user. Cant create account!";

    public static final int ERR_INVALID_ROLE = 1011;
    public static final String ERR_INVALID_ROLE_MSG = "Invalid role";

    public static final int ERR_ACCOUNT_ACTIVATION_USER_NOT_FOUND = 1012;
    public static final String ERR_ACCOUNT_ACTIVATION_USER_NOT_FOUND_MSG = "User not found";

    public static final String RESEND_ACTIVATION_MAIL_SUCCEEDED = "Resend activation mail succeeded";

    public static final int ERR_INVALID_TEAM_MEMBERS = 1013;
    public static final String ERR_INVALID_TEAM_MEMBERS_MSG = "Invalid team members";

    public static final int ERR_TEAM_ALREADY_EXIST = 1014;
    public static final String ERR_TEAM_ALREADY_EXIST_MSG = "Team already exist";

    public static final String TEAM_CREATED_SUCCESSFULLY = "Team created successfully";

    public static final int ERR_TEAM_NOT_FOUND = 1015;
    public static final String ERR_TEAM_NOT_FOUND_MSG = "Team not found";

    public static final String TEAM_UPDATED_SUCCESSFULLY = "Team updated successfully";

    public static final String TEAM_DELETED_SUCCESSFULLY = "Team deleted successfully";

    public static final int ERR_CHAT_SOURCE_ID_NOT_FOUND = 1016;
    public static final String ERR_CHAT_SOURCE_ID_NOT_FOUND_MSG = "Chat source not found!";

    public static final int ERR_CHAT_MEDIUM_NOT_FOUND = 1017;
    public static final String ERR_CHAT_MEDIUM_NOT_FOUND_MSG = "Chat medium not found!";

    public static final int ERR_CHAT_CUSTOMER_TRACK_ID_NOT_FOUND = 1018;
    public static final String ERR_CHAT_CUSTOMER_TRACK_ID_NOT_FOUND_MSG = "TrackId not found!";

    public static final int ERR_REGISTRATION_NOT_FOUND = 1019;
    public static final String ERR_REGISTRATION_NOT_FOUND_MSG = "Registration not found!";

    public static final String CUSTOMER_CHAT_MESSAGE_ADDED = "Customer message added";

    public static final int ERR_INVALID_USER = 1020;
    public static final String ERR_INVALID_USER_MSG = "Invalid user!";

    public static final int ERR_INVALID_MESSAGE_TRACK_ID = 1021;
    public static final String ERR_INVALID_MESSAGE_TRACK_ID_MSG = "Invalid message track id!";

    public static final String USER_CHAT_REPLY_ADDED = "User reply added";

    public static final String CHAT_STATUS_UPDATE_SUCCEEDED = "Chat status updated successfully";

}

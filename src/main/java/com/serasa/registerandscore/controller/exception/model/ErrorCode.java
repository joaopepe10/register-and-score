package com.serasa.registerandscore.controller.exception.model;

public enum ErrorCode {
    INTERNAL_ERROR,
    NOT_FOUND,
    UNAUTHORIZED,
    FORBIDDEN,
    BUSINESS_ERROR,
    VALIDATION_ERROR,
    FILE_TOO_LARGE,
    EXISTS_EMAIL,
    EXISTS_GOOGLE_EMAIL,
    EMAIL_NOT_VERIFIED,
    FAILED_TO_SEND_EMAIL
}

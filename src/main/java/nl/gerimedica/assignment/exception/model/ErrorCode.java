package nl.gerimedica.assignment.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DEFAULT_ERROR("E000", "Sorry, there was a problem.", "An unexpected error occurred!"),
    PATIENT_NOT_FOUND("E001", "Patient not found!", "There are no patients with this SSN!"),
    APPOINTMENT_NOT_FOUND("E002", "Appointment not found!", "Couldn't find any appointments with this SSN!");

    private final String errorCode;
    private final String message;
    private final String reason;
}

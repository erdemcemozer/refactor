package nl.gerimedica.assignment.exception.subexceptions;

import nl.gerimedica.assignment.exception.BaseException;
import nl.gerimedica.assignment.exception.model.ErrorCode;

import java.util.Date;

public class PatientNotFoundException extends BaseException {

    public PatientNotFoundException() {
        super(ErrorCode.PATIENT_NOT_FOUND.getErrorCode(),
                ErrorCode.PATIENT_NOT_FOUND.getMessage(),
                ErrorCode.PATIENT_NOT_FOUND.getReason(),
                new Date());
    }
}

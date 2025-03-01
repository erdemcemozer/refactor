package nl.gerimedica.assignment.exception.subexceptions;

import nl.gerimedica.assignment.exception.BaseException;
import nl.gerimedica.assignment.exception.model.ErrorCode;

import java.util.Date;

public class AppointmentNotFoundException extends BaseException {

    public AppointmentNotFoundException() {
        super(ErrorCode.APPOINTMENT_NOT_FOUND.getErrorCode(),
                ErrorCode.APPOINTMENT_NOT_FOUND.getMessage(),
                ErrorCode.APPOINTMENT_NOT_FOUND.getReason(),
                new Date());
    }
}

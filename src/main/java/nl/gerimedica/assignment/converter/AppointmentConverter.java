package nl.gerimedica.assignment.converter;

import nl.gerimedica.assignment.model.entity.Appointment;
import nl.gerimedica.assignment.model.dto.AppointmentDTO;

public class AppointmentConverter {

    public static AppointmentDTO toDTO(Appointment appointment) {
        return AppointmentDTO
                .builder()
                .id(appointment.getId())
                .reason(appointment.getReason())
                .date(appointment.getDate())
                .patient(appointment.getPatient())
                .build();
    }
}

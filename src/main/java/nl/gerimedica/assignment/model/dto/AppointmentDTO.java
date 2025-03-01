package nl.gerimedica.assignment.model.dto;

import lombok.Builder;
import nl.gerimedica.assignment.model.entity.Patient;

@Builder
public record AppointmentDTO(
        Long id,
        String reason,
        String date,
        Patient patient
) {
}

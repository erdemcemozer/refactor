package nl.gerimedica.assignment.service;

import nl.gerimedica.assignment.model.dto.AppointmentDTO;

import java.util.List;

public interface HospitalService {

    List<AppointmentDTO> bulkCreateAppointments(String patientName,
                                             String ssn,
                                             List<String> reasons,
                                             List<String> dates);

    List<AppointmentDTO> getAppointmentsByReason(String reasonKeyword);

    void deleteAppointmentsBySSN(String ssn);

    AppointmentDTO findLatestAppointmentBySSN(String ssn);
}

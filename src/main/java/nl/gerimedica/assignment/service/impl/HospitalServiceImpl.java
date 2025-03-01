package nl.gerimedica.assignment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.gerimedica.assignment.converter.AppointmentConverter;
import nl.gerimedica.assignment.exception.subexceptions.AppointmentNotFoundException;
import nl.gerimedica.assignment.exception.subexceptions.PatientNotFoundException;
import nl.gerimedica.assignment.model.dto.AppointmentDTO;
import nl.gerimedica.assignment.repository.AppointmentRepository;
import nl.gerimedica.assignment.model.entity.Patient;
import nl.gerimedica.assignment.model.entity.Appointment;
import nl.gerimedica.assignment.repository.PatientRepository;
import nl.gerimedica.assignment.service.HospitalService;
import nl.gerimedica.assignment.util.HospitalUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final PatientRepository patientRepo;
    private final AppointmentRepository appointmentRepo;

    @Override
    public List<AppointmentDTO> bulkCreateAppointments(
            String patientName,
            String ssn,
            List<String> reasons,
            List<String> dates
    ) {

        Patient patient = findOrCreatePatientIfNecessary(patientName, ssn);
        log.info("Patient found/created, SSN: {}", patient.getSsn());

        int loopSize = Math.min(reasons.size(), dates.size());
        List<Appointment> createdAppointments = IntStream.range(0, loopSize)
                .mapToObj(i -> Appointment.builder()
                        .reason(reasons.get(i))
                        .date(dates.get(i))
                        .patient(patient)
                        .build())
                .collect(Collectors.toList());

        appointmentRepo.saveAll(createdAppointments);

        createdAppointments.forEach(appointment ->
                log.info("Created appointment for reason: {} [Date: {}] [Patient SSN: {}]",
                        appointment.getReason(), appointment.getDate(), appointment.getPatient().getSsn()));

        HospitalUtils.recordUsage("Bulk create appointments");

        return createdAppointments
                .stream()
                .map(AppointmentConverter::toDTO)
                .toList();
    }

    @Override
    public List<AppointmentDTO> getAppointmentsByReason(String reasonKeyword) {
        HospitalUtils.recordUsage("Get appointments by reason");

        return appointmentRepo.findAll()
                .stream()
                .filter(appointment -> appointment.getReason().contains(reasonKeyword) ||
                        appointment.getReason().equalsIgnoreCase(reasonKeyword))
                .map(AppointmentConverter::toDTO)
                .toList();
    }

    @Override
    public void deleteAppointmentsBySSN(String ssn) {
        Patient patient = findPatientBySSN(ssn);

        Optional.ofNullable(patient.getAppointments())
                .filter(appointments -> !appointments.isEmpty())
                .ifPresent(appointmentRepo::deleteAll);
    }

    @Override
    public AppointmentDTO findLatestAppointmentBySSN(String ssn) {
        Patient patient = findPatientBySSN(ssn);

        return Optional.ofNullable(patient.getAppointments())
                .filter(appointments -> !appointments.isEmpty())
                .flatMap(appointments -> appointments.stream()
                        .max(Comparator.comparing(Appointment::getDate)))
                .map(AppointmentConverter::toDTO)
                .orElseThrow(AppointmentNotFoundException::new);
    }


    private Patient findPatientBySSN(String ssn) {
        return patientRepo
                .findBySsn(ssn)
                .orElseThrow(PatientNotFoundException::new);
    }

    @Transactional
    private Patient findOrCreatePatientIfNecessary(String patientName, String ssn) {
        return patientRepo.findBySsn(ssn)
                .orElseGet(() -> {
                    log.info("Creating new patient with SSN: {}", ssn);
                    return patientRepo.save(Patient
                            .builder()
                            .name(patientName)
                            .ssn(ssn)
                            .build());
                });
    }
}

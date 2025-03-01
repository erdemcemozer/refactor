package nl.gerimedica.assignment.controller;

import lombok.RequiredArgsConstructor;
import nl.gerimedica.assignment.model.dto.AppointmentDTO;
import nl.gerimedica.assignment.service.HospitalService;
import nl.gerimedica.assignment.util.HospitalUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final HospitalService hospitalService;

    /**
     * Example: {
     * "reasons": ["Checkup", "Follow-up", "X-Ray"],
     * "dates": ["2025-02-01", "2025-02-15", "2025-03-01"]
     * }
     */
    @PostMapping("/bulk-insert")
    public ResponseEntity<List<AppointmentDTO>> createBulkAppointments(
            @RequestParam String patientName,
            @RequestParam String ssn,
            @RequestBody Map<String, List<String>> payload
    ) {
        List<String> reasons = payload.get("reasons");
        List<String> dates = payload.get("dates");

        HospitalUtils.recordUsage("Controller triggered bulk appointments creation");

        return ResponseEntity.ok().body(hospitalService.bulkCreateAppointments(patientName, ssn, reasons, dates));
    }

    @GetMapping("/reason")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByReason(@RequestParam String keyword) {
        return ResponseEntity.ok().body(hospitalService.getAppointmentsByReason(keyword));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAppointmentsBySSN(@RequestParam String ssn) {
        hospitalService.deleteAppointmentsBySSN(ssn);
        return ResponseEntity.ok().body("Deleted all appointments for SSN: " + ssn);
    }

    @GetMapping("/latest")
    public ResponseEntity<AppointmentDTO> getLatestAppointment(@RequestParam String ssn) {
        return ResponseEntity.ok().body(hospitalService.findLatestAppointmentBySSN(ssn));
    }
}

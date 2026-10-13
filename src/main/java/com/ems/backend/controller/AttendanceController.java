package com.ems.backend.controller;

import com.ems.backend.dto.AttendanceDto;
import com.ems.backend.entity.Attendance;
import com.ems.backend.entity.Employee;
import com.ems.backend.repository.AttendanceRepository;
import com.ems.backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/checkin")
    public ResponseEntity<String> checkIn(@RequestBody Map<String, Long> request) {
        Long employeeId = request.get("employeeId");
        LocalDate today = LocalDate.now();
        
        Optional<Attendance> existingAttendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);
        
        if (existingAttendance.isPresent()) {
            return ResponseEntity.badRequest().body("Employee already checked in today");
        }
        
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(employeeId);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setDate(today);
        
        attendanceRepository.save(attendance);
        return ResponseEntity.ok("Check-in successful");
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkOut(@RequestBody Map<String, Long> request) {
        Long employeeId = request.get("employeeId");
        LocalDate today = LocalDate.now();
        
        Optional<Attendance> attendanceOpt = attendanceRepository.findByEmployeeIdAndDate(employeeId, today);
        
        if (!attendanceOpt.isPresent()) {
            return ResponseEntity.badRequest().body("No check-in record found for today");
        }
        
        Attendance attendance = attendanceOpt.get();
        if (attendance.getCheckOutTime() != null) {
            return ResponseEntity.badRequest().body("Employee already checked out today");
        }
        
        attendance.setCheckOutTime(LocalDateTime.now());
        attendanceRepository.save(attendance);
        
        return ResponseEntity.ok("Check-out successful");
    }

    @GetMapping("/today")
    public ResponseEntity<List<AttendanceDto>> getTodayAttendance() {
        LocalDate today = LocalDate.now();
        List<Employee> allEmployees = employeeRepository.findAll();
        List<Attendance> todayAttendance = attendanceRepository.findByDate(today);
        
        List<AttendanceDto> attendanceDtos = allEmployees.stream().map(employee -> {
            AttendanceDto dto = new AttendanceDto();
            dto.setEmployeeId(employee.getId());
            dto.setEmployeeName(employee.getFirstName() + " " + employee.getLastName());
            dto.setEmployeeEmail(employee.getEmailId());
            dto.setDate(today);
            
            Optional<Attendance> attendance = todayAttendance.stream()
                    .filter(a -> a.getEmployeeId().equals(employee.getId()))
                    .findFirst();
            
            if (attendance.isPresent()) {
                dto.setCheckInTime(attendance.get().getCheckInTime());
                dto.setCheckOutTime(attendance.get().getCheckOutTime());
            }
            
            return dto;
        }).collect(Collectors.toList());
        
        return ResponseEntity.ok(attendanceDtos);
    }
}
package Joining.example.controller;

import Joining.example.model.Employee;
import Joining.example.service.EmailService;
import Joining.example.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow all origins for testing
public class EmployeeController {

    private final EmailService emailService;

    @Autowired
    private EmployeeService employeeService;

    EmployeeController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {
        try {
            System.out.println("Received employee data: " + employee);
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            System.out.println("Error saving employee: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/send-appointment-letter")
public ResponseEntity<?> sendAppointmentLetter(@RequestBody Map<String, String> request) {
    try {
        String email = request.get("email");
        String fullName = request.get("fullName");
        String position = request.get("position");
        String employeeId = request.get("employeeId");
        String startDate = request.get("startDate");
        String department = request.get("department");
        String workLocation = request.get("workLocation");
        String salary = request.get("salary");
        String annualSalary = request.get("annualSalary");
        String probationPeriod = request.get("probationPeriod");
        String trainingPeriod = request.get("trainingPeriod");
        String address = request.get("address");
        String phone = request.get("phone");
        String reportingManager = request.get("reportingManager");

        // Send email with all details
        emailService.sendAppointmentLetter(email, "Appointment Letter - Hamsini Tech Solutions", 
                                         fullName, position, employeeId, startDate,
                                         department, workLocation, salary, annualSalary,
                                         probationPeriod, trainingPeriod, address, phone, reportingManager);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment letter sent successfully to " + email);
        response.put("status", "success");
        
        System.out.println("Appointment letter sent to: " + email);
        
        return ResponseEntity.ok(response);
    } catch (Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Failed to send appointment letter: " + e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Backend is running!");
    }

    @PutMapping("/employees/{id}")
public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
    try {
        System.out.println("Updating employee with ID: " + id);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    } catch (Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        System.out.println("Error updating employee: " + e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
}
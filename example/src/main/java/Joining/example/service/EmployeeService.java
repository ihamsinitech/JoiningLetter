package Joining.example.service;

import Joining.example.model.Employee;
import Joining.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        // Check if email already exists
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Employee with email " + employee.getEmail() + " already exists");
        }
        
        // Check if employee ID already exists
        if (employeeRepository.existsByEmployeeId(employee.getEmployeeId())) {
            throw new RuntimeException("Employee ID " + employee.getEmployeeId() + " already exists");
        }
        
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }


    public Employee updateEmployee(Long id, Employee employee) {
    Employee existingEmployee = employeeRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    
    // Update fields
    existingEmployee.setFullName(employee.getFullName());
    existingEmployee.setEmployeeId(employee.getEmployeeId());
    existingEmployee.setPosition(employee.getPosition());
    existingEmployee.setDepartment(employee.getDepartment());
    existingEmployee.setStartDate(employee.getStartDate());
    existingEmployee.setWorkLocation(employee.getWorkLocation());
    existingEmployee.setSalary(employee.getSalary());
    existingEmployee.setAnnualSalary(employee.getAnnualSalary());
    existingEmployee.setProbationPeriod(employee.getProbationPeriod());
    existingEmployee.setTrainingPeriod(employee.getTrainingPeriod());
    existingEmployee.setEmail(employee.getEmail());
    existingEmployee.setAddress(employee.getAddress());
    existingEmployee.setReportingManager(employee.getReportingManager());
    existingEmployee.setHrManager(employee.getHrManager());
    existingEmployee.setPhone(employee.getPhone());
    
    return employeeRepository.save(existingEmployee);
}

public Long getTotalEmployees() {
        return employeeRepository.count();
    }

    public Long getNewEmployeesThisMonth() {
        // Calculate statistics from the employee list
        List<Employee> employees = getAllEmployees();
        LocalDate now = LocalDate.now();
        return employees.stream()
                .filter(emp -> {
                    LocalDate joinDate = emp.getStartDate();
                    return joinDate.getMonthValue() == now.getMonthValue() && 
                           joinDate.getYear() == now.getYear();
                })
                .count();
    }

    public Long getDepartmentsCount() {
        // Calculate distinct departments from the employee list
        List<Employee> employees = getAllEmployees();
        return employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .count();
    }
}


package Joining.example.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

     //@NotBlank(message = "Employee ID is required")
    @Column(nullable = false, unique = true,updatable = false  ) 
    private String employeeId;

    @NotBlank(message = "Position is required")
    @Column(nullable = false)
    private String position;

    @NotBlank(message = "Department is required")
    @Column(nullable = false)
    private String department;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotBlank(message = "Work location is required")
    @Column(nullable = false)
    private String workLocation;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    @Column(nullable = false)
    private Double salary;

    @NotBlank(message = "Probation period is required")
    @Column(nullable = false)
    private String probationPeriod;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank(message = "Reporting manager is required")
    @Column(nullable = false)
    private String reportingManager;

    @NotBlank(message = "HR manager is required")
    @Column(nullable = false)
    private String hrManager;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number")
    @Column(nullable = false)
    private String phone;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        // this.employeeId="EMP" +System.currentTimeMillis();
    }

    @NotNull(message = "Annual salary is required")
@Min(value = 0, message = "Annual salary must be positive")
@Column(nullable = false)
private Double annualSalary;

@NotBlank(message = "Training period is required")
@Column(nullable = false)
private String trainingPeriod;
}

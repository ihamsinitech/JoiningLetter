package Joining.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentLetter(String toEmail, String subject, String employeeName, 
                                    String position, String employeeId, String startDate,
                                    String department, String workLocation, String salary, 
                                    String annualSalary, String probationPeriod, String trainingPeriod,
                                    String address, String phone, String reportingManager) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setFrom("hamsinitechsolutions@gmail.com", "Hamsini Tech Solutions");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            
            String emailContent = buildEmailContent(toEmail, employeeName, position, employeeId, startDate,
                                                  department, workLocation, salary, annualSalary,
                                                  probationPeriod, trainingPeriod, address, phone, reportingManager);
            helper.setText(emailContent, true); // true indicates HTML content
            
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
            
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    private String buildEmailContent(String toEmail, String employeeName, String position, String employeeId, String startDate,
                                   String department, String workLocation, String salary, String annualSalary,
                                   String probationPeriod, String trainingPeriod, String address, String phone, 
                                   String reportingManager) {
        
        // Format current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String currentDate = dateFormat.format(new Date());
        
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { 
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
                        line-height: 1.6; 
                        color: #333;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        max-width: 800px;
                        margin: 0 auto;
                        padding: 20px;
                    }
                    .header { 
                        background: #2c3e50; 
                        padding: 30px; 
                        text-align: center; 
                        color: white;
                    }
                    .content { 
                        padding: 30px;
                        border: 2px solid #000000;
                        margin: 20px 0;
                    }
                    .footer { 
                        background: #f4f4f4; 
                        padding: 20px; 
                        text-align: center;
                        font-size: 14px;
                        color: #666;
                    }
                    .section-title {
                        color: #2c3e50;
                        border-bottom: 2px solid #3498db;
                        padding-bottom: 10px;
                        margin: 25px 0 15px 0;
                        font-size: 1.4rem;
                        text-align: center;
                    }
                    .clause {
                        margin-bottom: 15px;
                        text-align: justify;
                    }
                    .clause-title {
                        font-weight: 600;
                        color: #2c3e50;
                        margin-bottom: 5px;
                        font-size: 1.1rem;
                    }
                    .company-name {
                        font-size: 2.5rem;
                        color: #2c3e50;
                        font-weight: 700;
                        margin-bottom: 10px;
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <div class="company-name">Hamsini Tech Solutions</div>
                        <h2>APPOINTMENT LETTER</h2>
                    </div>
                    
                    <div class="content">
                        <div style="margin-bottom: 20px;">
                            <p><strong>Date:</strong> %s</p>
                            <p><strong>Name:</strong> %s</p>
                            <p><strong>Employee ID:</strong> %s</p>
                            <p><strong>Mobile No:</strong> %s</p>
                            <p><strong>Email:</strong> %s</p>
                            <p><strong>Address:</strong> %s</p>
                        </div>
                        
                        <div class="clause">
                            <p>Dear <strong>%s</strong>,</p>
                            <p>Welcome to Hamsini Tech Solutions!</p>
                            <p>Congratulations — we are delighted to have you on board.</p>
                            <p>Following your application and subsequent personal interview, we are pleased to offer you the position of <strong>%s</strong> in the <strong>%s</strong> department. We look forward to providing you with a rewarding and challenging work environment that supports your professional growth and success.</p>
                        </div>
                        
                        <div style="margin: 20px 0;">
                            <p><strong>Work Location:</strong> %s</p>
                            <p><strong>Date of Joining:</strong> %s</p>
                        </div>
                        
                        <div class="clause">
                            <div class="clause-title">Compensation & Benefits:</div>
                            <p>You will receive a stipend of <strong>₹%s/-</strong> per month during your initial <strong>%s</strong> of training and probation.</p>
                            <p>Upon successful completion of this period, you will be entitled to a Cost to Company (CTC) of <strong>₹%s/-</strong> per annum.</p>
                            <p>Your probation period will be <strong>%s</strong> and training period will be <strong>%s</strong>.</p>
                        </div>
                        
                        <div class="clause">
                            <p><strong>Reporting Manager:</strong> %s</p>
                            <p>Please refer to the attached PDF document for complete details of your appointment, including all terms and conditions, policies, and annexures.</p>
                        </div>
                        
                        <div class="clause">
                            <p>We take this opportunity to welcome you once again to Hamsini Tech Solutions and wish you a long and successful career with us.</p>
                        </div>
                    </div>
                    
                    <div class="footer">
                        <p><strong>HAMSINI TECH SOLUTIONS</strong></p>
                        <p>Flat no:502, Annapoorna Block, Aditya Enclave, Ameerpet, Hyderabad, Telangana 500085</p>
                        <p>Phone: +91 9515345553 | Email: hamsinitechsolutions@gmail.com | Website: www.hamsinitechsolutions.com</p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            currentDate,                    // %s - Date
            employeeName,                   // %s - Name  
            employeeId,                     // %s - Employee ID
            phone,                          // %s - Mobile No
            toEmail,                        // %s - Email (this was the missing variable)
            address,                        // %s - Address
            employeeName,                   // %s - Dear [Name]
            position,                       // %s - Position
            department,                     // %s - Department
            workLocation,                   // %s - Work Location
            startDate,                      // %s - Date of Joining
            salary,                         // %s - Salary
            trainingPeriod,                 // %s - Training Period
            annualSalary,                   // %s - Annual Salary
            probationPeriod,                // %s - Probation Period
            trainingPeriod,                 // %s - Training Period (again)
            reportingManager                // %s - Reporting Manager
        );
    }
}
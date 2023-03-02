package com.RentCars.RentCars.auth;

import com.RentCars.RentCars.config.LogoutService;
import com.RentCars.RentCars.entities.PasswordResetToken;
import com.RentCars.RentCars.entities.User;
import com.RentCars.RentCars.persistances.repositories.UserRepository;
import com.RentCars.RentCars.persistances.services.EmailService;
import com.RentCars.RentCars.persistances.services.PasswordResetTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final LogoutService logoutService;
  private final PasswordResetTokenService passwordResetTokenService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;


  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/logout")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    logoutService.logout(request, response, authentication);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/update")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<AuthenticationResponse> update(
          @RequestBody UpdateRequest request
  ) {
    return ResponseEntity.ok(service.update(request));
  }

  @PostMapping("/updateEmail")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<AuthenticationResponse> updateEmail(
          @RequestBody UpdateEmailRequest request
  ) {
    return ResponseEntity.ok(service.updateEmail(request));
  }

  @PostMapping("/updatePassword")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<AuthenticationResponse> updatePassword(
          @RequestBody UpdatePasswordRequest request
  ) {
    return ResponseEntity.ok(service.updatePassword(request));
  }

  @DeleteMapping("/deleteAccount")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> deleteAccount(Authentication authentication) {
    String email = authentication.getName();
    service.deleteAccount(email);
    return ResponseEntity.ok().build();
  }


  @GetMapping("/user")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<GetUserByEmailResponse> getUserByEmail(@RequestParam String email) {
    return ResponseEntity.ok(service.getUserByEmail(email));
  }

  @GetMapping("/checkEmail")
  public ResponseEntity<Void> checkEmail(@RequestParam String email) {
    boolean emailExists = service.isEmailExists(email);
    if (emailExists) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  //password forget

  @PostMapping("/forgot-password")
  public ResponseEntity<String> forgotPassword(@RequestParam("email") String email) {
    PasswordResetToken passwordResetToken = passwordResetTokenService.createPasswordResetToken(email);
    // Send an email to the user's email address containing a link to a password reset page on your website
    String resetUrl = "https://example.com/reset-password?token=" + passwordResetToken.getToken();
    String emailBody = "Click the following link to reset your password: " + resetUrl;
    emailService.sendEmail(email, "Password Reset Request", emailBody);
    return ResponseEntity.ok("Password reset email sent successfully");
  }

  @GetMapping("/reset-password")
  public ResponseEntity<String> showResetPasswordPage(@RequestParam("token") String token) {
    PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid password reset token"));

    if (passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Password reset token has expired");
    }
    // If the token is valid, show the password reset page
    return ResponseEntity.ok("Password reset page");
  }

  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
    PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid password reset token"));
    if (passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Password reset token has expired");
    }
    // Update the user's password in your database
    User user = userRepository.findByEmail(passwordResetToken.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + passwordResetToken.getEmail()));
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    // Delete the password reset token from your database
    passwordResetTokenService.deletePasswordResetToken(passwordResetToken);
    return ResponseEntity.ok("Password reset successful");
  }




}

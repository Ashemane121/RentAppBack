package com.RentCars.RentCars.auth;

import com.RentCars.RentCars.config.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;
  private final LogoutService logoutService;


  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/admin/register")
  public ResponseEntity<AuthenticationResponse> registerAdmin(
          @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerAdmin(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/admin/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticateAdmin(
          @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticateAdmin(request));
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

  @GetMapping("/users")
  @PreAuthorize("isAuthenticated()")
  public List<GetUserByEmailResponse> getAllUsers(@RequestParam String email) {
    if (!service.isAdmin(email)) {
      throw new AccessDeniedException("Only users with ADMIN role can access this endpoint");
    }
    return service.getAllUsers();
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

  @GetMapping("/checkAdmin")
  public ResponseEntity<Void> checkAdmin(@RequestParam String email) {
    boolean isAdmin = service.isAdmin(email);
    if (isAdmin) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.badRequest().build();
    }
  }



}

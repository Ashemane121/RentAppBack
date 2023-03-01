package com.RentCars.RentCars.auth;

import com.RentCars.RentCars.config.JwtService;
import com.RentCars.RentCars.entities.Token;
import com.RentCars.RentCars.persistances.repositories.TokenRepository;
import com.RentCars.RentCars.entities.TokenType;
import com.RentCars.RentCars.entities.Role;
import com.RentCars.RentCars.entities.User;
import com.RentCars.RentCars.persistances.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    // Check if the email is already used
    if (repository.findByEmail(request.getEmail()).isPresent()) {
      throw new IllegalArgumentException("Email address is already in use");
    }
    // Create a new user with the provided data
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse update(UpdateRequest request) {
    // Find the user by email
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
    // Update the user details
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    // user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setPhone(request.getPhone());
    user.setAddress(request.getAddress());
    // Save the updated user details
    var savedUser = repository.save(user);
    // Revoke all the user's existing tokens
    revokeAllUserTokens(user);
    // Generate a new token for the user
    var jwtToken = jwtService.generateToken(user);
    // Save the token in the database
    saveUserToken(savedUser, jwtToken);
    // Return the authentication response containing the new token
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }

  public AuthenticationResponse updateEmail(UpdateEmailRequest request) {
    // Find the user by email
    var user = repository.findByEmail(request.getOldEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getOldEmail()));
    // Update the user's email
    user.setEmail(request.getNewEmail());
    // Save the updated user details
    var savedUser = repository.save(user);
    // Revoke all the user's existing tokens
    revokeAllUserTokens(user);
    // Generate a new token for the user
    var jwtToken = jwtService.generateToken(user);
    // Save the token in the database
    saveUserToken(savedUser, jwtToken);
    // Return the authentication response containing the new token
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }

  public AuthenticationResponse updatePassword(UpdatePasswordRequest request) {
    // Find the user by email
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));
    // Update the user's password
    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    // Save the updated user details
    var savedUser = repository.save(user);
    // Revoke all the user's existing tokens
    revokeAllUserTokens(user);
    // Generate a new token for the user
    var jwtToken = jwtService.generateToken(user);
    // Save the token in the database
    saveUserToken(savedUser, jwtToken);
    // Return the authentication response containing the new token
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
  }

  public void deleteAccount(String email) {
    var user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    repository.delete(user);
    revokeAllUserTokens(user);
  }

  public GetUserByEmailResponse getUserByEmail(String email) {
    var user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    //Get user's properties
    return GetUserByEmailResponse.builder()
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .address(user.getAddress())
            .phone(user.getPhone())
            .build();

  }

  public boolean isEmailExists(String email) {
    return repository.findByEmail(email).isPresent();
  }


  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}

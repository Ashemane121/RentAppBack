package com.RentCars.RentCars.auth;

import com.RentCars.RentCars.config.JwtService;
import com.RentCars.RentCars.persistances.entities.Role;
import com.RentCars.RentCars.persistances.entities.Token;
import com.RentCars.RentCars.persistances.entities.TokenType;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.persistances.repositories.TokenRepository;
import com.RentCars.RentCars.persistances.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private static final String USER_NOT_FOUND_WITH_EMAIL="User not found with email: ";
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

  public AuthenticationResponse registerAdmin(RegisterRequest request) {
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
            .role(Role.ADMIN)
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

  public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow();
    // check the user's role
    if (!user.getRole().equals(Role.ADMIN)) {
      throw new BadCredentialsException("Invalid credentials");
    }
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
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + request.getEmail()));
    // Update the user details
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
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
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + request.getOldEmail()));
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
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + request.getEmail()));
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

  public AuthenticationResponse updateProfilePicture(UpdateProfilePictureRequest request) {
    // Find the user by email
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + request.getEmail()));
    // Update the user's profile picture
    user.setProfile_picture(request.getProfilePicture());
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
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));
    repository.delete(user);
    revokeAllUserTokens(user);
  }

  public GetUserByEmailResponse getUserById(Long id) {
    var user = repository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    //Get user's properties
    return GetUserByEmailResponse.builder()
            .id(user.getId())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .address(user.getAddress())
            .phone(user.getPhone())
            .role(user.getRole().toString())
            .profilePicture(user.getProfile_picture())
            .build();
  }
  public GetUserByEmailResponse getUserByEmail(String email) {
    var user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));

    //Get user's properties
    return GetUserByEmailResponse.builder()
            .id(user.getId())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .email(user.getEmail())
            .address(user.getAddress())
            .phone(user.getPhone())
            .role(user.getRole().toString())
            .profilePicture(user.getProfile_picture())
            .build();

  }


  public List<GetUserByEmailResponse> getAllUsers() {

    return repository.findAll().stream()
            .map(user -> GetUserByEmailResponse.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .phone(user.getPhone())
                    .role(user.getRole().toString())
                    .profilePicture(user.getProfile_picture())
                    .build())
            .collect(Collectors.toList());
  }

  public boolean isEmailExists(String email) {
    return repository.findByEmail(email).isPresent();
  }

  public boolean isAdmin(String email) {
    var user = repository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));
    return user.getRole().toString().equals(Role.ADMIN.toString());
  }


  void saveUserToken(User user, String jwtToken) {
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

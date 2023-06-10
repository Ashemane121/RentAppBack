package com.RentCars.RentCars.auth;

import com.RentCars.RentCars.config.JwtService;
import com.RentCars.RentCars.persistances.entities.Role;
import com.RentCars.RentCars.persistances.entities.Token;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.persistances.repositories.TokenRepository;
import com.RentCars.RentCars.persistances.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void register_ValidRequest_Success() {
//        // Prepare
//        RegisterRequest request = new RegisterRequest();
//        request.setFirstname("John");
//        request.setLastname("Doe");
//        request.setEmail("johndoe@example.com");
//        request.setPassword("password");
//        User savedUser = new User();
//        savedUser.setId(1L);
//        savedUser.setFirstname("John");
//        savedUser.setLastname("Doe");
//        savedUser.setEmail("johndoe@example.com");
//        savedUser.setPassword("password");
//        savedUser.setRole(Role.USER);
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//        when(userRepository.save(any(User.class))).thenReturn(savedUser);
//        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
//        when(jwtService.generateToken(savedUser)).thenReturn("jwtToken");
//        // Execute
//        AuthenticationResponse response = authenticationService.register(request);
//        // Verify
//        assertNotNull(response);
//        assertEquals("jwtToken", response.getToken());
//        verify(userRepository, times(1)).findByEmail(request.getEmail());
//        verify(userRepository, times(1)).save(any(User.class));
//        verify(passwordEncoder, times(1)).encode(request.getPassword());
//        verify(jwtService, times(1)).generateToken(savedUser);
//        verify(tokenRepository, times(1)).save(any(Token.class));
//    }

    @Test
    void register_EmailAlreadyExists_ThrowsException() {
        // Prepare
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existingemail@example.com");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        // Execute and Verify
        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(request));
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
        verify(jwtService, never()).generateToken(any(User.class));
        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    void authenticate_ValidCredentials_Success() {
        // Prepare
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("johndoe@example.com");
        request.setPassword("password");
        User user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Execute
        AuthenticationResponse response = authenticationService.authenticate(request);
        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(user);
        //verify(tokenRepository, times(1)).deleteAllByUser(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

//    @Test
//    void authenticate_InvalidCredentials_ThrowsException() {
//        // Prepare
//        AuthenticationRequest request = new AuthenticationRequest();
//        request.setEmail("johndoe@example.com");
//        request.setPassword("password");
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
//        // Execute and Verify
//        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(request));
//        // Verify method invocations
//        verify(userRepository, times(1)).findByEmail(request.getEmail());
//        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(jwtService, never()).generateToken(any(User.class));
//        verify(tokenRepository, never()).save(any(Token.class));
//    }


    @Test
    void authenticateAdmin_ValidCredentials_Success() {
        // Prepare
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("admin@example.com");
        request.setPassword("password");
        User admin = new User();
        admin.setId(1L);
        admin.setFirstname("Admin");
        admin.setLastname("User");
        admin.setEmail("admin@example.com");
        admin.setPassword("password");
        admin.setRole(Role.ADMIN);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(admin));
        when(jwtService.generateToken(admin)).thenReturn("jwtToken");
        // Execute
        AuthenticationResponse response = authenticationService.authenticateAdmin(request);
        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, times(1)).generateToken(admin);
        //verify(tokenRepository, times(1)).deleteAllByUser(admin);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void authenticateAdmin_InvalidCredentials_ThrowsException() {
        // Prepare
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("johndoe@example.com");
        request.setPassword("password");
        User user = new User();
        user.setId(1L);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        // Execute and Verify
        assertThrows(BadCredentialsException.class, () -> authenticationService.authenticateAdmin(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(jwtService, never()).generateToken(any(User.class));
        //verify(tokenRepository, never()).deleteAllByUser(any(User.class));
        verify(tokenRepository, never()).save(any(Token.class));
    }

    @Test
    void update_ValidUpdateRequest_Success() {
        // Prepare
        UpdateRequest request = new UpdateRequest();
        request.setEmail("johndoe@example.com");
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setPhone(1234567890);
        request.setAddress("123 Street, City");
        User user = new User();
        user.setEmail("johndoe@example.com");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setPhone(null);
        user.setAddress(null);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Execute
        AuthenticationResponse response = authenticationService.update(request);
        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(userRepository, times(1)).save(user);
        //verify(tokenRepository, times(1)).deleteAllByUser(user);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }
    @Test
    void updateEmail_ValidUpdateEmailRequest_Success() {
        // Prepare
        UpdateEmailRequest request = new UpdateEmailRequest();
        request.setOldEmail("johndoe@example.com");
        request.setNewEmail("newemail@example.com");
        User user = new User();
        user.setEmail("johndoe@example.com");
        when(userRepository.findByEmail(request.getOldEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Execute
        AuthenticationResponse response = authenticationService.updateEmail(request);
        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals(request.getNewEmail(), user.getEmail());
        verify(userRepository, times(1)).findByEmail(request.getOldEmail());
        verify(userRepository, times(1)).save(user);
        //verify(tokenRepository, times(1)).deleteAllByUser(user);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void updatePassword_ValidUpdatePasswordRequest_Success() {
        // Prepare
        UpdatePasswordRequest request = new UpdatePasswordRequest();
        request.setEmail("johndoe@example.com");
        request.setNewPassword("newPassword");
        User user = new User();
        user.setEmail("johndoe@example.com");
        // Mock the behavior of repository methods
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Execute
        AuthenticationResponse response = authenticationService.updatePassword(request);
        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals(passwordEncoder.encode(request.getNewPassword()), user.getPassword());
        // Verify method invocations
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(userRepository, times(1)).save(user);
        //verify(tokenRepository, times(1)).deleteAllByUser(user);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }



    @Test
    void updateProfilePicture_ValidUpdateProfilePictureRequest_Success() {
        // Prepare
        UpdateProfilePictureRequest request = new UpdateProfilePictureRequest();
        request.setEmail("johndoe@example.com");
        request.setProfilePicture("profile_picture");
        User user = new User();
        user.setEmail("johndoe@example.com");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");
        // Execute
        AuthenticationResponse response = authenticationService.updateProfilePicture(request);
        // Verify
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        assertEquals(request.getProfilePicture(), user.getProfile_picture());
        verify(userRepository, times(1)).findByEmail(request.getEmail());
        verify(userRepository, times(1)).save(user);
        //verify(tokenRepository, times(1)).deleteAllByUser(user);
        verify(jwtService, times(1)).generateToken(user);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void deleteAccount_UserExists_DeletesUserAndRevokesTokens() {
        // Prepare
        String email = "johndoe@example.com";
        User user = new User();
        user.setEmail(email);
        // Mock the behavior of repository methods
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // Execute
        authenticationService.deleteAccount(email);
        // Verify
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).delete(user);
    }



    @Test
    void getUserById_UserExists_ReturnsUserByEmailResponse() {
        // Prepare
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstname("John");
        user.setLastname("Doe");
        // Set other user properties
        user.setRole(Role.USER); // Set a non-null role for the user
        // Mock the behavior of repository methods
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // Execute
        GetUserByEmailResponse response = authenticationService.getUserById(userId);
        // Verify
        assertNotNull(response);
        assertEquals(userId, response.getId());
        assertEquals("John", response.getFirstname());
        assertEquals("Doe", response.getLastname());
        // Verify other user properties
        assertEquals("USER", response.getRole()); // Verify the role string value
        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    void getUserByEmail_UserExists_ReturnsUserByEmailResponse() {
        // Prepare
        String email = "johndoe@example.com";
        User user = new User();
        user.setEmail(email);
        user.setFirstname("John");
        user.setLastname("Doe");
        // Set other user properties
        user.setRole(Role.USER); // Set a non-null role for the user
        // Mock the behavior of repository methods
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // Execute
        GetUserByEmailResponse response = authenticationService.getUserByEmail(email);
        // Verify
        assertNotNull(response);
        assertEquals(email, response.getEmail());
        assertEquals("John", response.getFirstname());
        assertEquals("Doe", response.getLastname());
        // Verify other user properties
        assertEquals("USER", response.getRole()); // Verify the role string value
        verify(userRepository, times(1)).findByEmail(email);
    }


    @Test
    void getAllUsers_UsersExist_ReturnsListWithUserByEmailResponses() {
        // Prepare
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstname("John");
        user1.setLastname("Doe");
        // Set other user properties
        user1.setRole(Role.USER); // Set a non-null role for the user
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        // Set other user properties
        user2.setRole(Role.ADMIN); // Set a non-null role for the user
        List<User> userList = Arrays.asList(user1, user2);
        // Mock the behavior of repository methods
        when(userRepository.findAll()).thenReturn(userList);
        // Execute
        List<GetUserByEmailResponse> responseList = authenticationService.getAllUsers();
        // Verify
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        GetUserByEmailResponse response1 = responseList.get(0);
        assertEquals(1L, response1.getId());
        assertEquals("John", response1.getFirstname());
        assertEquals("Doe", response1.getLastname());
        // Verify other user properties
        assertEquals("USER", response1.getRole()); // Verify the role string value
        GetUserByEmailResponse response2 = responseList.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("Jane", response2.getFirstname());
        assertEquals("Smith", response2.getLastname());
        // Verify other user properties
        assertEquals("ADMIN", response2.getRole()); // Verify the role string value
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void isEmailExists_EmailExists_ReturnsTrue() {
        // Prepare
        String email = "johndoe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        // Execute
        boolean result = authenticationService.isEmailExists(email);
        // Verify
        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void isEmailExists_EmailDoesNotExist_ReturnsFalse() {
        // Prepare
        String email = "johndoe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // Execute
        boolean result = authenticationService.isEmailExists(email);
        // Verify
        assertFalse(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void isAdmin_UserExistsWithAdminRole_ReturnsTrue() {
        // Prepare
        String email = "johndoe@example.com";
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.ADMIN);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // Execute
        boolean result = authenticationService.isAdmin(email);
        // Verify
        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void isAdmin_UserExistsWithNonAdminRole_ReturnsFalse() {
        // Prepare
        String email = "johndoe@example.com";
        User user = new User();
        user.setEmail(email);
        user.setRole(Role.USER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        // Execute
        boolean result = authenticationService.isAdmin(email);
        // Verify
        assertFalse(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void isAdmin_UserDoesNotExist_ThrowsException() {
        // Prepare
        String email = "johndoe@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // Execute and Verify
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.isAdmin(email));
        verify(userRepository, times(1)).findByEmail(email);
    }
}

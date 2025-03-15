package com.example.userservice.services;

import com.example.userservice.dtos.UserDto;
import com.example.userservice.exceptions.UserAlreadyExistsException;
import com.example.userservice.exceptions.UserAlreadyLoggedInTwoDevices;
import com.example.userservice.exceptions.UserDoesNotExistException;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.repositories.SessionRepository;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import org.apache.commons.lang3.RandomStringUtils;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password) throws UserDoesNotExistException,UserAlreadyLoggedInTwoDevices {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserDoesNotExistException("User with email "+email+" doesn't exists");
        }

        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        long no_of_sessions = sessionRepository.countByUser_IdAndStatus(user.getId(), SessionStatus.IS_ACTIVE);
        if(no_of_sessions == 2) {
            throw new UserAlreadyLoggedInTwoDevices("User is logged in two devices must logout of one device");
        }

        String token  = RandomStringUtils.randomAscii(20);
        MultiValueMapAdapter<String,String>headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", token);

        UserDto userDto = UserDto.fromUser(user);

        Session session = new Session();
        session.setUser(user);
        session.setToken(token);
        session.setStatus(SessionStatus.IS_ACTIVE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,5);
        Date date = calendar.getTime();
        session.setExpiresAt(date);
        sessionRepository.save(session);

        ResponseEntity<UserDto>response = new ResponseEntity(
                userDto,
                headers,
                HttpStatus.OK
        );

        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setStatus(SessionStatus.LOGGED_OUT);

        sessionRepository.save(session);

        return ResponseEntity.ok().build();
    }

    public UserDto signUp(String email, String password, String name) throws UserAlreadyExistsException {

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isEmpty()) {
            throw new UserAlreadyExistsException("User with " + email + " already exists.");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);

        User savedUser = userRepository.save(user);

        return UserDto.fromUser(savedUser);
    }

    public Optional<UserDto> validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return Optional.empty();
        }

        Session session = sessionOptional.get();

        if (!session.getStatus().equals(SessionStatus.IS_ACTIVE)) {
            return Optional.empty();
        }

        User user = userRepository.findById(userId).get();

        UserDto userDto = UserDto.fromUser(user);

//        if (!session.getExpiringAt() > new Date()) {
//            return SessionStatus.EXPIRED;
//        }

        return Optional.of(userDto);
    }
}

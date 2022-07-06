package by.bdg.task.rest.controller;

import by.bdg.task.security.model.AuthRequest;
import by.bdg.task.security.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthenticateController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        ResponseEntity<String> responseEntity = null;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                            authRequest.getPassword())
            );
            logger.info("Authorization attempt");
            String string = String.format("{\"username\":\"%s\",\"token\":\"%s\"}",authRequest.getUserName(),
                    jwtUtil.generateToken(authRequest.getUserName()));
            responseEntity = new ResponseEntity<>(string, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Invalid username or password" + e);
            responseEntity = new ResponseEntity<>("Invalid username or password", HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }
}

package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.config.JwtUtil;
import com.parcialspring.parcialspring.dto.AuthenticationResponse;
import com.parcialspring.parcialspring.dto.UserRequest;
import com.parcialspring.parcialspring.dto.UserResponse;
import com.parcialspring.parcialspring.model.UserModel;
import com.parcialspring.parcialspring.model.TokenModel;
import com.parcialspring.parcialspring.repository.TokenRepository;
import com.parcialspring.parcialspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;


    // Metodo para crear usuario usando el metodo save del repository
    public UserResponse createUser(UserRequest request) {
        UserModel user = new UserModel();
        user.setIdCard(request.getIdCard());
        user.setCode(request.getCode());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // mapear role (nuevo campo)
        user.setRole(request.getRole() != null ? request.getRole() : "PRACTICANTE");

        UserModel newUser = repository.save(user);

        // Construcción explícita del UserResponse (incluye role)
        return new UserResponse(
                newUser.getId(),
                newUser.getIdCard(),
                newUser.getCode(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getEmail(),
                newUser.getPhone(),
                newUser.getRole()
        );
    }

    // Metodo para listar todos los usuarios usando el metodo findAll del repository
    public List<UserResponse> findAllUsers(){
        List<UserModel> users = repository.findAll();
        return users.stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getIdCard(),
                        u.getCode(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getPhone(),
                        u.getRole()
                ))
                .toList();
    }

    // Metodo para buscar usuario por id usando el metodo findById del repository
    public UserResponse findUserById(Long id){
        UserModel user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado por id" + id));
        return new UserResponse(
                user.getId(),
                user.getIdCard(),
                user.getCode(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    // Metodo para actualizar usuario por id usando el metodo save del repository
    public UserResponse updateUser(Long id, UserRequest request) {
        UserModel user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado por id" + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        UserModel updatedUser = repository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getIdCard(),
                updatedUser.getCode(),
                updatedUser.getFirstName(),
                updatedUser.getLastName(),
                updatedUser.getEmail(),
                updatedUser.getPhone(),
                updatedUser.getRole()
        );
    }

    // Metodo para eliminar usuario por id usando el metodo deleteById del repository
    public void deleteUserById(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Usuario no encontrado por id" + id);
        }
        repository.deleteById(id);
    }

    // Metodo para login de usuario
    public AuthenticationResponse login(String email, String password) {
        UserModel user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
        String token = jwtUtil.generateToken(user);

        // Generar el token en la DB
        Date expDate = jwtUtil.getExpirationDateFromToken(token);
        LocalDateTime expiresAt = LocalDateTime.ofInstant(expDate.toInstant(), ZoneId.systemDefault());

        TokenModel tokenModel = TokenModel.builder()
                .token(token)
                .tokenType("BEARER")
                .revoked(false)
                .expired(false)
                .expiresAt(expiresAt)
                .user(user)
                .build();

        tokenRepository.save(tokenModel);

        // Construcción explícita del UserResponse para el login
        UserResponse userResp = new UserResponse(
                user.getId(),
                user.getIdCard(),
                user.getCode(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
        return new AuthenticationResponse(token, userResp);
    }

    // Método para logout: revocar token enviado en la cabecera Authorization
    public void logout(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) return;
        String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : authorizationHeader;
        tokenRepository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            t.setExpired(true);
            tokenRepository.save(t);
        });
    }
}

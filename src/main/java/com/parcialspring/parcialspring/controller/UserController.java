package com.parcialspring.parcialspring.controller;


import com.parcialspring.parcialspring.dto.AuthenticationResponse;
import com.parcialspring.parcialspring.dto.UserRequest;
import com.parcialspring.parcialspring.dto.UserResponse;
import com.parcialspring.parcialspring.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Data
public class UserController {

    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    //Endpoints

    //Endpoint para crear usuario
    //Metodo POST http://localhost:8080/users

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest request){

        return service.createUser(request);
    }

    //Endpoint para listar todos los usuarios
    //Metodo GET http://localhost:8080/users
    @GetMapping
    public List<UserResponse> findAllUsers(){
        return service.findAllUsers();
    }

    //Endpoint para buscar usuario por id
    //Metodo GET http://localhost:8080/users/{id}

    @GetMapping("/{id}")
    public UserResponse findUserById(@PathVariable Long id){
        return service.findUserById(id);
    }

    //Enpoint para actualizar usuario
    //Metodo PUT http://localhost:8080/users/{id}
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest request){
        return service.updateUser(id, request);
    }

    //Enpoint para eliminar usuario
    //Metodo DELETE http://localhost:8080/users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        service.deleteUserById(id);
    }

    //Endpoint para login de usuario
    //Metodo POST http://localhost:8080/users/login
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody UserRequest request){
        return service.login(request.getEmail(), request.getPassword());
    }

    // Endpoint para logout
    // Metodo POST http://localhost:8080/users/logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(name = "Authorization", required = false) String authorization) {
        service.logout(authorization);
        return ResponseEntity.ok().build();
    }
}

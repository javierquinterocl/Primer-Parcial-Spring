package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.dto.UserRequest;
import com.parcialspring.parcialspring.dto.UserResponse;
import com.parcialspring.parcialspring.model.UserModel;
import com.parcialspring.parcialspring.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }


    // Metodos del servicio aca

    //Metodo para crear usuario usando el metodo save del repository

    public UserResponse createUser(UserRequest request) {
        UserModel user = new UserModel();
        user.setIdCard(request.getIdCard());
        user.setCode(request.getCode());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        UserModel newUser = repository.save(user);

        return new UserResponse
                (
                        newUser.getId(),
                        newUser.getIdCard(),
                        newUser.getCode(),
                        newUser.getFirstName(),
                        newUser.getLastName(),
                        newUser.getEmail(),
                        newUser.getPhone()
                );
    }

    //Metodo para listar todos los usuarios usando el metodo findAll del repository

    public List<UserResponse> findAllUsers(){
        List<UserModel> user = repository.findAll();

        return  user.stream()
                .map(u -> new UserResponse
                (
                        u.getId(),
                        u.getIdCard(),
                        u.getCode(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getPhone())
                ).toList();

    }

    //Metodo para buscar usuario por id usando el metodo findById del repository

    public UserResponse findUserById(Long id){
        UserModel user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado por id" + id));

        return new UserResponse
                (
                        user.getId(),
                        user.getIdCard(),
                        user.getCode(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone()
                );
    }

    //Metodo para actualizar usuario por id usando el metodo save del repository

    public UserResponse updateUser(Long id, UserRequest request) {

        UserModel user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado por id" + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        UserModel updatedUser = repository.save(user);

        return new UserResponse
                (
                        updatedUser.getId(),
                        updatedUser.getIdCard(),
                        updatedUser.getCode(),
                        updatedUser.getFirstName(),
                        updatedUser.getLastName(),
                        updatedUser.getEmail(),
                        updatedUser.getPhone()
                );

    }

    //Metodo para eliminar usuario por id usando el metodo deleteById del repository

    public void deleteUserById(Long id){

        if(!repository.existsById(id)){
            throw new RuntimeException("Usuario no encontrado por id" + id);
        }

        repository.deleteById(id);
    }

}

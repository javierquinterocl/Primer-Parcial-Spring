package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.GoatRequest;
import com.parcialspring.parcialspring.dto.GoatResponse;
import com.parcialspring.parcialspring.service.GoatService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goats")
@Data
public class GoatController {

    private final GoatService service;

    public GoatController(GoatService service) {
        this.service = service;
    }

    // Crear cabra
    //Endpoint para crear cabra
    //Metodo POST http://localhost:8080/goats
    @PostMapping
    public GoatResponse createGoat(@RequestBody GoatRequest request) {
        return service.createGoat(request);
    }

    // Listar todas
    //Endpoint para listar todas las cabras
    //Metodo GET http://localhost:8080/goats
    @GetMapping
    public List<GoatResponse> findAllGoats() {
        return service.findAllGoats();
    }

    // Buscar por id
    //Endpoint para buscar cabra por id
    //Metodo GET http://localhost:8080/goats/{id}
    @GetMapping("/{id}")
    public GoatResponse findGoatById(@PathVariable Long id) {
        return service.findGoatById(id);
    }

    // Actualizar
    //Endpoint para actualizar cabra
    //Metodo PUT http://localhost:8080/goats/{id}
    @PutMapping("/{id}")
    public GoatResponse updateGoat(@PathVariable Long id, @RequestBody GoatRequest request) {
        return service.updateGoat(id, request);
    }

    // Eliminar
    //Endpoint para eliminar cabra
    //Metodo DELETE http://localhost:8080/goats/{id}
    @DeleteMapping("/{id}")
    public void deleteGoat(@PathVariable Long id) {
        service.deleteGoatById(id);
    }
}


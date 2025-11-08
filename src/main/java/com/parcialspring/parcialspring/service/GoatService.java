package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.dto.GoatRequest;
import com.parcialspring.parcialspring.dto.GoatResponse;
import com.parcialspring.parcialspring.model.GoatModel;
import com.parcialspring.parcialspring.repository.GoatRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class GoatService {

    private final GoatRepository repository;

    public GoatService(GoatRepository repository) {
        this.repository = repository;
    }

    // Crear cabra
    public GoatResponse createGoat(GoatRequest request) {
        GoatModel goat = new GoatModel();
        goat.setGoatId(request.getGoatId());
        goat.setName(request.getName());
        goat.setBreed(request.getBreed());
        // Asignación directa de Strings/Long sin conversiones
        goat.setBirthDate(request.getBirthDate());
        goat.setGender(request.getGender());
        goat.setGoatType(request.getGoatType());
        goat.setWeight(request.getWeight());
        goat.setMilkProduction(request.getMilkProduction());
        goat.setFoodConsumption(request.getFoodConsumption());
        goat.setVaccinationsCount(request.getVaccinationsCount());
        goat.setHeatPeriods(request.getHeatPeriods());
        goat.setOffspringCount(request.getOffspringCount());
        goat.setParentId(request.getParentId());
        goat.setStatus(request.getStatus());
        goat.setNotes(request.getNotes());

        GoatModel saved = repository.save(goat);
        return toResponse(saved);
    }

    // Listar cabras
    public List<GoatResponse> findAllGoats() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    // Buscar por id
    public GoatResponse findGoatById(Long id) {
        GoatModel goat = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabra no encontrada con id " + id));
        return toResponse(goat);
    }

    // Actualizar
    public GoatResponse updateGoat(Long id, GoatRequest request) {
        GoatModel goat = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabra no encontrada con id " + id));

        goat.setName(request.getName());
        goat.setBreed(request.getBreed());
        goat.setBirthDate(request.getBirthDate());
        goat.setGender(request.getGender());
        goat.setGoatType(request.getGoatType());
        goat.setWeight(request.getWeight());
        goat.setMilkProduction(request.getMilkProduction());
        goat.setFoodConsumption(request.getFoodConsumption());
        goat.setVaccinationsCount(request.getVaccinationsCount());
        goat.setHeatPeriods(request.getHeatPeriods());
        goat.setOffspringCount(request.getOffspringCount());
        goat.setParentId(request.getParentId());
        goat.setStatus(request.getStatus());
        goat.setNotes(request.getNotes());

        GoatModel updated = repository.save(goat);
        return toResponse(updated);
    }

    // Eliminar
    public void deleteGoatById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cabra no encontrada con id " + id);
        }
        repository.deleteById(id);
    }

    private GoatResponse toResponse(GoatModel g) {
        return new GoatResponse(
                g.getId(),
                g.getGoatId(),
                g.getName(),
                g.getBreed(),
                g.getBirthDate(),
                g.getGender(),
                g.getGoatType(),
                g.getWeight(),
                g.getMilkProduction(),
                g.getFoodConsumption(),
                g.getVaccinationsCount(),
                g.getHeatPeriods(),
                g.getOffspringCount(),
                g.getParentId(),
                g.getStatus(),
                g.getNotes()
        );
    }
}

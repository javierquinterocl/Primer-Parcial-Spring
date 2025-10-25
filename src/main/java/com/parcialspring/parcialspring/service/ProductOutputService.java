package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.dto.ProductOutputRequest;
import com.parcialspring.parcialspring.dto.ProductOutputResponse;
import com.parcialspring.parcialspring.model.ProductModel;
import com.parcialspring.parcialspring.model.ProductOutputModel;
import com.parcialspring.parcialspring.model.UserModel;
import com.parcialspring.parcialspring.repository.ProductOutputRepository;
import com.parcialspring.parcialspring.repository.ProductRepository;
import com.parcialspring.parcialspring.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
public class ProductOutputService {

    private final ProductOutputRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    ProductOutputService(ProductOutputRepository repository, UserRepository userRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Métodos del servicio 

    // Crear salida de producto
    @Transactional
    public ProductOutputResponse createProductOutput(ProductOutputRequest request) {
        // Buscar el usuario por ID
        UserModel user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + request.getUserId()));

        // Buscar el producto por ID
        ProductModel product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + request.getProductId()));

        // Validar que haya suficiente stock
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Stock insuficiente. Stock disponible: " + product.getStock() + ", Cantidad solicitada: " + request.getQuantity());
        }

        // Crear la salida de producto
        ProductOutputModel productOutput = new ProductOutputModel();
        productOutput.setUser(user);
        productOutput.setProduct(product);
        productOutput.setQuantity(request.getQuantity());
        productOutput.setNotes(request.getNotes());

        ProductOutputModel newProductOutput = repository.save(productOutput);

        // Actualizar el stock del producto
        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        return new ProductOutputResponse(
                newProductOutput.getId(),
                newProductOutput.getUser().getId(),
                newProductOutput.getUser().getFirstName() + " " + newProductOutput.getUser().getLastName(),
                newProductOutput.getProduct().getId(),
                newProductOutput.getProduct().getName(),
                newProductOutput.getQuantity(),
                newProductOutput.getNotes(),
                newProductOutput.getCreatedAt()
        );
    }

    // Listar todas las salidas de productos
    public List<ProductOutputResponse> findAllProductOutputs() {
        List<ProductOutputModel> productOutputs = repository.findAll();

        return productOutputs.stream()
                .map(po -> new ProductOutputResponse(
                        po.getId(),
                        po.getUser().getId(),
                        po.getUser().getFirstName() + " " + po.getUser().getLastName(),
                        po.getProduct().getId(),
                        po.getProduct().getName(),
                        po.getQuantity(),
                        po.getNotes(),
                        po.getCreatedAt()
                ))
                .toList();
    }

    // Buscar salida de producto por ID
    public ProductOutputResponse findProductOutputById(Long id) {
        ProductOutputModel productOutput = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida de producto no encontrada con id " + id));

        return new ProductOutputResponse(
                productOutput.getId(),
                productOutput.getUser().getId(),
                productOutput.getUser().getFirstName() + " " + productOutput.getUser().getLastName(),
                productOutput.getProduct().getId(),
                productOutput.getProduct().getName(),
                productOutput.getQuantity(),
                productOutput.getNotes(),
                productOutput.getCreatedAt()
        );
    }

    // Eliminar salida de producto por ID
    @Transactional
    public void deleteProductOutputById(Long id) {
        ProductOutputModel productOutput = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salida de producto no encontrada con id " + id));

        // Devolver el stock al producto
        ProductModel product = productOutput.getProduct();
        product.setStock(product.getStock() + productOutput.getQuantity());
        productRepository.save(product);

        // Eliminar la salida
        repository.deleteById(id);
    }
}


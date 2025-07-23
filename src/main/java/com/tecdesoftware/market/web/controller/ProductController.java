package com.tecdesoftware.market.web.controller;

import com.tecdesoftware.market.domain.Product;
import com.tecdesoftware.market.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "API para gestión de productos en el mercado")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los productos",
            description = "Retorna una lista de todos los productos disponibles en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Retorna un producto específico basado en su ID único"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
    )
    public ResponseEntity<Product> getProduct(
            @Parameter(
                    description = "ID único del producto",
                    required = true,
                    example = "1"
            )
            @PathVariable("id") int productId) {
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(
            summary = "Obtener productos por categoría",
            description = "Retorna todos los productos que pertenecen a una categoría específica"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Productos de la categoría obtenidos exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron productos para la categoría especificada",
            content = @Content
    )
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(
                    description = "ID de la categoría de productos",
                    required = true,
                    example = "1"
            )
            @PathVariable("categoryId") int categoryId) {
        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/scarce/{quantity}")
    @Operation(
            summary = "Obtener productos con stock escaso",
            description = "Retorna productos que tienen un stock menor o igual a la cantidad especificada"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Productos con stock escaso obtenidos exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron productos con stock escaso",
            content = @Content
    )
    public ResponseEntity<List<Product>> getScarceProducts(
            @Parameter(
                    description = "Cantidad límite para considerar un producto como escaso",
                    required = true,
                    example = "5"
            )
            @PathVariable("quantity") int quantity) {
        return productService.getScarceProducts(quantity)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Crea y guarda un nuevo producto en el sistema"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Product.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos del producto inválidos",
            content = @Content
    )
    public ResponseEntity<Product> save(
            @RequestBody(
                    description = "Datos del producto a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de producto",
                                    summary = "Producto de ejemplo para crear",
                                    value = """
                                    {
                                        "name": "Leche Entera",
                                        "categoryId": 1,
                                        "price": 25.50,
                                        "stock": 100,
                                        "active": true
                                    }
                                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Product product) {
        System.out.println("=== DEBUG POST ===");
        System.out.println("Producto recibido:");
        System.out.println("- Name: " + product.getName());
        System.out.println("- CategoryId: " + product.getCategoryId());
        System.out.println("- Price: " + product.getPrice());
        System.out.println("- Stock: " + product.getStock());
        System.out.println("- Active: " + product.isActive());

        try {
            Product savedProduct = productService.save(product);
            System.out.println("Producto guardado exitosamente con ID: " + savedProduct.getProductId());
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error al guardar producto: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar producto",
            description = "Elimina un producto específico del sistema basado en su ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto eliminado exitosamente",
            content = @Content
    )
    @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content
    )
    public ResponseEntity<Void> delete(
            @Parameter(
                    description = "ID único del producto a eliminar",
                    required = true,
                    example = "1"
            )
            @PathVariable("id") int productId) {
        if (productService.delete(productId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
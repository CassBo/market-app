package com.tecdesoftware.market.web.controller;

import com.tecdesoftware.market.domain.Purchase;
import com.tecdesoftware.market.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchase Controller", description = "API para gestión de compras y transacciones")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    @Operation(
            summary = "Obtener todas las compras",
            description = "Retorna una lista de todas las compras registradas en el sistema"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de compras obtenida exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Purchase.class)
            )
    )
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    @GetMapping("/client/{clientId}")
    @Operation(
            summary = "Obtener compras por cliente",
            description = "Retorna todas las compras realizadas por un cliente específico"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Compras del cliente obtenidas exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Purchase.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "No se encontraron compras para el cliente especificado",
            content = @Content
    )
    public ResponseEntity<List<Purchase>> getByClient(
            @Parameter(
                    description = "ID único del cliente",
                    required = true,
                    example = "CLI001"
            )
            @PathVariable String clientId) {
        return purchaseService.getByClient(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crear una nueva compra",
            description = "Registra una nueva compra en el sistema con todos sus detalles e items"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Compra creada exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Purchase.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Datos de la compra inválidos",
            content = @Content
    )
    public ResponseEntity<Purchase> save(
            @RequestBody(
                    description = "Datos de la compra a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Purchase.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de compra",
                                    summary = "Compra de ejemplo para crear",
                                    value = """
                                    {
                                        "clientId": "CLI001",
                                        "date": "2025-07-21T10:30:00",
                                        "paymentMethod": "Tarjeta de crédito",
                                        "comment": "Compra mensual de productos básicos",
                                        "state": "Completada",
                                        "items": [
                                            {
                                                "productId": 1,
                                                "quantity": 2,
                                                "total": 51.00,
                                                "active": true
                                            },
                                            {
                                                "productId": 2,
                                                "quantity": 1,
                                                "total": 15.75,
                                                "active": true
                                            }
                                        ]
                                    }
                                    """
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody Purchase purchase) {
        Purchase saved = purchaseService.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}


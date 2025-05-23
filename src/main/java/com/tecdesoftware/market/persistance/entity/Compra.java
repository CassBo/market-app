package com.tecdesoftware.market.persistance.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_compra")
    private Integer idCompra;

    @Column (name = "id_cliente")
    private String idCliente;

    private LocalDateTime fecha;

    @Column (name = "medio_pago")
    private String medioPago;

    private String comentario;
    private String estado;
}

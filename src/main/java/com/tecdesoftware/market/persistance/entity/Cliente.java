package com.tecdesoftware.market.persistance.entity;
import jakarta.persistence.*;

@Entity
@Table(name="clientes")

public class Cliente {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String nombre;
    private String apellidos;
    private Long celular;
    private String direccion;

    @Column(name="correo_electronico")
    private String correoElectronico;


}

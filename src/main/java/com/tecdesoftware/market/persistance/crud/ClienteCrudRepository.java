package com.tecdesoftware.market.persistance.crud;

import com.tecdesoftware.market.persistance.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClienteCrudRepository extends CrudRepository<Cliente,String> {

    Optional<Cliente> findByCorreoElectronico(String correoElectronico);
}

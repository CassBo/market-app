package com.tecdesoftware.market.persistance.crud;

import com.tecdesoftware.market.persistance.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {
}

package com.tecdesoftware.market.persistance;

import com.tecdesoftware.market.persistance.crud.ProductoCrudRepository;
import com.tecdesoftware.market.persistance.entity.Producto;

import java.util.List;

public class ProductoRepository {
    private ProductoCrudRepository  productoCrudRepository;

    public List<Producto> getAll() {
        return (List<Producto>) productoCrudRepository.findAll();
        }
}


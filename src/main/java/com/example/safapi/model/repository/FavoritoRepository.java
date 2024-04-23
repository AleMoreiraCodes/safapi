package com.example.safapi.model.repository;

import com.example.safapi.model.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
}

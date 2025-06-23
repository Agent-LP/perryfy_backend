package com.perryfyback.perryfy.repositories;

import com.perryfyback.perryfy.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
} 
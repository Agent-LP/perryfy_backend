package com.perryfyback.perryfy.repositories;

import com.perryfyback.perryfy.entities.PrintArea;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrintAreaRepository extends JpaRepository<PrintArea, Integer> {
    Optional<PrintArea> findByWidthAndHeight(Integer width, Integer height);

} 
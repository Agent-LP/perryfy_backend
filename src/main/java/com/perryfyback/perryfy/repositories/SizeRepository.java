package com.perryfyback.perryfy.repositories;

import com.perryfyback.perryfy.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
} 
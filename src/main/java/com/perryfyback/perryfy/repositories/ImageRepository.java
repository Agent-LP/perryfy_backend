package com.perryfyback.perryfy.repositories;

import com.perryfyback.perryfy.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
} 
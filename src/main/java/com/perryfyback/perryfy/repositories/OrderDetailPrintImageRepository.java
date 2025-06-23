package com.perryfyback.perryfy.repositories;

import com.perryfyback.perryfy.entities.OrderDetailPrintImage;
import com.perryfyback.perryfy.models.JPAModelTypes.OrderDetailPrintImageId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailPrintImageRepository extends JpaRepository<OrderDetailPrintImage, OrderDetailPrintImageId> {
} 
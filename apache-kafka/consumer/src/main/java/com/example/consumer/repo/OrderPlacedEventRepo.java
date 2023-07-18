package com.example.consumer.repo;

import com.example.consumer.model.OrderPlacedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPlacedEventRepo extends JpaRepository<OrderPlacedEvent, Integer> {
}

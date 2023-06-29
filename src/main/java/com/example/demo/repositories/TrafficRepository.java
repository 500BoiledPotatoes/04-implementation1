package com.example.demo.repositories;

import com.example.demo.model.Customer;
import com.example.demo.model.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrafficRepository extends JpaRepository<Traffic,Long> {

    Traffic findByTrafficId(Long tid);

    List<Traffic> findAllByTypeContaining(String a);
}

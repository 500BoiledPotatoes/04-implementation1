package com.example.demo.repositories;

import com.example.demo.model.Customer;
import com.example.demo.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    Staff findByStaffId(Long administratorId);
    List<Staff> findAllByNameContaining(String name);
    Staff findByEmailContaining(String email);

    Staff findByEmail(String email);

    Staff findByEmailAndPwd(String email, String pwd);
}

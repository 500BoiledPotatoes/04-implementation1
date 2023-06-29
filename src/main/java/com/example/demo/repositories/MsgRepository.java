package com.example.demo.repositories;

import com.example.demo.model.MsgLeave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgRepository extends JpaRepository<MsgLeave,Long> {

}

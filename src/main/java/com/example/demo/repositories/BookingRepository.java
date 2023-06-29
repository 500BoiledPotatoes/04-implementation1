package com.example.demo.repositories;

import com.example.demo.model.Booking;
import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking>findAllByCustomer(Customer customerName);

    List<Booking> findAllByCustomerAndStartTimeBetween(Customer customer, LocalDateTime dateTime,  LocalDateTime dateTime2);

    Booking findByBookingId(Long id);

    List<Booking> findAllByConfirmed(boolean b);

    List<Booking> findAllByFinished(boolean b);
}

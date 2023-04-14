package com.example.demo.xrpl.nft.domain.repository;

import com.example.demo.xrpl.nft.domain.model.RentalType;
import com.example.demo.xrpl.nft.domain.model.RentalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<RentalView, Long> {
    Page<RentalView> findAllByRentalType(RentalType rentalType, Pageable pageable);

    List<RentalView> findAllByRentalType(RentalType rentalType);
}

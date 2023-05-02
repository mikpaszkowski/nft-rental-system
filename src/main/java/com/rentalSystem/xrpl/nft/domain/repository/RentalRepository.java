package com.rentalSystem.xrpl.nft.domain.repository;

import com.rentalSystem.xrpl.nft.domain.model.rental.RentalType;
import com.rentalSystem.xrpl.nft.domain.model.rental.RentalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalView, Long> {
    Page<RentalView> findAllByRentalType(final RentalType rentalType, final Pageable pageable);

}

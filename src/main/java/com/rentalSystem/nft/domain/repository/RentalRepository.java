package com.rentalSystem.nft.domain.repository;

import com.rentalSystem.nft.domain.model.rental.RentalType;
import com.rentalSystem.nft.domain.model.rental.RentalView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalView, Long> {
    Page<RentalView> findAllByRentalType(final RentalType rentalType, final Pageable pageable);

}

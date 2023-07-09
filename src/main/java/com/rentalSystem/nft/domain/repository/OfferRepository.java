package com.rentalSystem.nft.domain.repository;

import com.rentalSystem.nft.domain.model.offer.OfferType;
import com.rentalSystem.nft.domain.model.offer.OfferView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<OfferView, Long> {

    Page<OfferView> findAllByOfferType(OfferType offerType, Pageable pageable);

    @Query(value = "SELECT offer FROM OfferView offer WHERE offer.nftView = :nftTokenId")
    Optional<OfferView> findOne(@Param("nftTokenId") String nftTokenId);
}

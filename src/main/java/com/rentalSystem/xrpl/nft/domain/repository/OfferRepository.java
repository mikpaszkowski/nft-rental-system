package com.rentalSystem.xrpl.nft.domain.repository;

import com.rentalSystem.xrpl.nft.domain.model.offer.OfferType;
import com.rentalSystem.xrpl.nft.domain.model.offer.OfferView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferView, Long> {

    Page<OfferView> findAllByOfferType(OfferType offerType, Pageable pageable);

    @Query(value = "SELECT * FROM OFFERS offer WHERE offer.nft_id = :nftTokenId", nativeQuery = true)
    Optional<OfferView> findOfferViewByNftId(@Param("nftTokenId") String nftTokenId);
}

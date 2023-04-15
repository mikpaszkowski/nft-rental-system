package com.rentalSystem.xrpl.nft.domain.repository;

import com.rentalSystem.xrpl.nft.domain.model.OfferType;
import com.rentalSystem.xrpl.nft.domain.model.OfferView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<OfferView, Long> {

    Page<OfferView> findAllByOfferType(OfferType offerType, Pageable pageable);

    List<OfferView> findAllByNftView_OwnerId(String ownerId);
}

package com.rentalSystem.xrpl.nft.domain.repository;

import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NFTRepository extends JpaRepository<NFTView, String> {
}

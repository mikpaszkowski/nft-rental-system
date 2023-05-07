package com.rentalSystem.xrpl.wallet.fake.domain.repository;

import com.rentalSystem.xrpl.wallet.fake.domain.model.WalletView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Deprecated
@Repository
public interface WalletRepository extends JpaRepository<WalletView, String> {

    Optional<WalletView> findWalletViewByAddress(String address);
}

package com.rentalSystem.xrpl.wallet.fake.domain.repository;

import com.rentalSystem.xrpl.wallet.fake.domain.model.WalletView;
import org.springframework.data.jpa.repository.JpaRepository;

@Deprecated
public interface WalletRepository extends JpaRepository<WalletView, String> {

}

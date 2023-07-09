package com.rentalSystem.nft.domain.repository;

import com.rentalSystem.nft.domain.model.condition.ConditionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository<ConditionView, Long> {

}

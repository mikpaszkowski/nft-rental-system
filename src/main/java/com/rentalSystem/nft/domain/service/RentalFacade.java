package com.rentalSystem.nft.domain.service;

import com.rentalSystem.nft.api.model.RentRequestDTO;
import com.rentalSystem.nft.api.model.RentResponseDTO;
import com.rentalSystem.nft.domain.model.mapper.RentalMapper;
import com.rentalSystem.nft.domain.model.rental.RentalView;
import com.rentalSystem.nft.domain.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentalFacade {
    private final RentalRepository rentalRepository;

    private final RentalMapper rentalMapper;

    @Transactional
    public RentResponseDTO rentNft(RentRequestDTO rentRequestDTO) {
        var savedRental = rentalRepository.save(rentalMapper.mapEntity(rentRequestDTO, new RentalView()));
        return rentalMapper.mapDTO(savedRental);
    }
}
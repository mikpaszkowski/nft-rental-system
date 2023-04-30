package com.rentalSystem.xrpl.nft.domain.model.offer;


import com.rentalSystem.xrpl.configuration.model.DateAudit;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "offers")
@EntityListeners(AuditingEntityListener.class)
public class OfferView extends DateAudit {
    @Id
    private Long id;
    @NotNull
    private Integer dailyRentalPrice;
    @NotNull
    private Integer maxRentalDurationDays;
    @NotNull
    private OfferType offerType;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id", referencedColumnName = "nfTokenID", foreignKey = @ForeignKey(name = "NFT_ID_FK"))
    private NFTView nftView;
}

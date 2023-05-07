package com.rentalSystem.xrpl.nft.domain.model.offer;


import com.rentalSystem.xrpl.configuration.model.DateAudit;
import com.rentalSystem.xrpl.nft.domain.model.nft.NFTView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "offers")
@EntityListeners(AuditingEntityListener.class)
public class OfferView extends DateAudit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @NotNull
    private Integer dailyRentalPrice;
    @NotNull
    private Integer maxRentalDurationDays;
    @NotNull
    private OfferType offerType;
    @OneToOne(cascade = CascadeType.ALL)
    private NFTView nftView;
}

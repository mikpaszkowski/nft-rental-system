package com.rentalSystem.nft.domain.model.condition;

import com.rentalSystem.configuration.model.DateAudit;
import com.rentalSystem.nft.domain.model.rental.RentalView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conditions")
public class ConditionView extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String condition;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "RENTAL_ID_FK"))
    private RentalView rentalView;
}

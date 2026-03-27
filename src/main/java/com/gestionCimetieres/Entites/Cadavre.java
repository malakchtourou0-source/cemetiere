package com.gestionCimetieres.Entites;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "cadavre")
@PrimaryKeyJoinColumn(name = "id")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public abstract class Cadavre extends ElementInhumable {

    @Column(name = "date_deces")
    private LocalDate dateDeces;

    @Column(name = "place_deces")
    private String placeDeces;

    @Enumerated(EnumType.STRING)
    private Nationalite nationalite; // TUNISIEN | SUD_SAHARIEN | ETRANGER

    @ManyToOne
    @JoinColumn(name = "cause_deces_id")
    private CauseDeces causeDeces;
}
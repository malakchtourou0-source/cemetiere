package com.gestionCimetieres.Entites;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "cadavre_inconnu")
@PrimaryKeyJoinColumn(name = "id")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor @AllArgsConstructor
public class CadavreInconnu extends Cadavre {

    @Column(name = "numero_hebergement", nullable = false)
    private String numeroHebergement;

    @Column(name = "numero_medico_legal", nullable = false)
    private String numeroMedicoLegal;

    @Column(name = "date_sortie_service_medical")
    private LocalDate dateSortieServiceMedical;
}
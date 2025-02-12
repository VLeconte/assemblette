package com.assemblette.assemblette_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeputyDto {
    private Long id;
    private String prenom;
    private String nom;
    private String region;
    private String departement;
    private int numeroDeCirconscription;
    private String profession;
    private String groupePolitiqueComplet;
    private String groupePolitiqueAbrege;
}

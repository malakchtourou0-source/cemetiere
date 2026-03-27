package com.gestionCimetieres.Entites;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hopital_public")
@PrimaryKeyJoinColumn(name = "id")
@Data @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HopitalPublic extends Etablissement {}
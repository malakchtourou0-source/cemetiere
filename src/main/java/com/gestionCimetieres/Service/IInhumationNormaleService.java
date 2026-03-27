package com.gestionCimetieres.Service;

import com.gestionCimetieres.Entites.Cimetiere;
import com.gestionCimetieres.Entites.Tombe;
import com.gestionCimetieres.dto.InhumationNormaleRequest;
import com.gestionCimetieres.dto.InhumationNormaleResponse;
import java.util.List;


public interface IInhumationNormaleService {
	
	
    InhumationNormaleResponse  enregistrer(InhumationNormaleRequest request);
    InhumationNormaleResponse  consulter(Long permisId);
    List<Cimetiere>            listerCimetieres();
    List<Tombe>                listerTombesDisponibles(Long cimetiereId);
}
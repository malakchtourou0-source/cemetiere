package com.gestionCimetieres.Controller;
import com.gestionCimetieres.Entites.*;
import com.gestionCimetieres.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/impression")
@RequiredArgsConstructor
public class ImpressionController {

    private final PermisInhumationRepository  permisRepo;
    private final QuittancePaiementRepository quittanceRepo;
    private final OrdreMissionRepository      ordreMissionRepo;
    private final DemandeTransfertRepository  demandeTransfertRepo;

    // ── Fiche 5 — Permis d'inhumation ────────────────────────
    // GET /impression/permis/{id}
    @GetMapping("/permis/{id}")
    public String imprimerPermis(@PathVariable Long id, Model model) {

        PermisInhumation permis = permisRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("رخصة الدفن غير موجودة"));

        Cadavre cadavre = (Cadavre) permis.getElement();
        QuittancePaiement quittance =
                quittanceRepo.findByPermisInhumationId(id).orElse(null);

        model.addAttribute("permis",    permis);
        model.addAttribute("cadavre",   cadavre);
        model.addAttribute("quittance", quittance);

        // Nom complet
        if (cadavre instanceof CadavreConnu cc)
            model.addAttribute("nomComplet", cc.getNom() + " " + cc.getPrenom());
        else
            model.addAttribute("nomComplet", "مجهول الهوية");

        return "permis_inhumation"; // → /WEB-INF/jsp/permis_inhumation.jsp
    }

    // ── Fiche 1 — Ordre de mission ────────────────────────────
    // GET /impression/ordre-mission/{id}
    @GetMapping("/ordre/{id}")
    public String imprimerOrdreMission(@PathVariable Long id, Model model) {

        OrdreMission ordre = ordreMissionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("أمر المهمة غير موجود"));

        DemandeTransfert demande = ordre.getDemandeTransfert();

        model.addAttribute("ordre",   ordre);
        model.addAttribute("demande", demande);
        model.addAttribute("membres",
                ordre.getEquipe() != null ? ordre.getEquipe().getMembres() : null);

        return "ordre_mission"; // → /WEB-INF/jsp/ordre_mission.jsp
    }
}

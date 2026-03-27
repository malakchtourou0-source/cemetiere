package com.gestionCimetieres.Service;
import com.gestionCimetieres.Entites.*;
import com.gestionCimetieres.Repository.*;
import com.gestionCimetieres.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.gestionCimetieres.Entites.TypeMotif;

@Service
@RequiredArgsConstructor
public class InhumationNormaleService implements IInhumationNormaleService {

    private final CadavreRepository           cadavreRepo;
    private final TombeRepository             tombeRepo;
    private final CimetiereRepository         cimetiereRepo;
    private final PermisInhumationRepository  permisRepo;
    private final QuittancePaiementRepository quittanceRepo;
    private final AgentRepository             agentRepo;
    private final CauseDecesRepository        causeRepo;

    @Override
    @Transactional
    public InhumationNormaleResponse enregistrer(InhumationNormaleRequest req) {

        Agent agent = agentRepo.findById(req.getAgentId())
                .orElseThrow(() -> new RuntimeException("عون غير موجود"));

        Tombe tombe = tombeRepo.findById(req.getTombeId())
                .orElseThrow(() -> new RuntimeException("القبر غير موجود"));
        if (tombe.getOccupe())
            throw new RuntimeException("القبر مشغول بالفعل");
        tombe.setOccupe(true);
        tombe = tombeRepo.save(tombe);

        CauseDeces cause = null;
        if (req.getCauseDecesId() != null)
            cause = causeRepo.findById(req.getCauseDecesId()).orElse(null);

        // ── Créer cadavre ────────────────────────────────────
        Cadavre cadavre;
        if ("CONNU".equals(req.getTypeCadavre())) {
            CadavreConnu cc = new CadavreConnu();
            cc.setNom(req.getNom());
            cc.setPrenom(req.getPrenom());
            cc.setAge(req.getAge());
            cc.setCin(req.getCin());
            cc.setDateDeces(req.getDateDeces());
            cc.setPlaceDeces(req.getPlaceDeces());
            cc.setNationalite(Nationalite.valueOf(req.getNationalite()));
            cc.setCauseDeces(cause);
            cc.setTombe(tombe);
            cadavre = cadavreRepo.save(cc);
        } else {
            CadavreInconnu ci = new CadavreInconnu();
            ci.setNumeroHebergement(req.getNumeroHebergement());
            ci.setNumeroMedicoLegal(req.getNumeroMedicoLegal());
            ci.setDateSortieServiceMedical(req.getDateSortieServiceMedical());
            ci.setDateDeces(req.getDateDeces());
            ci.setPlaceDeces(req.getPlaceDeces());
            ci.setNationalite(Nationalite.valueOf(req.getNationalite()));
            ci.setCauseDeces(cause);
            ci.setTombe(tombe);
            cadavre = cadavreRepo.save(ci);
        }

        // ── Créer permis ─────────────────────────────────────
        // ✅ PermisInhumation n'a PAS de champ agent — supprimé
        PermisInhumation permis = new PermisInhumation();
        permis.setNumPermis(permisRepo.genererProchainNumero());
        permis.setDateDelivrance(req.getDateDelivrance());
        permis.setHeureDeces(req.getHeureDeces());
        permis.setNomDefunt(req.getNomDefunt());
        permis.setNomPere(req.getNomPere());
        permis.setNomMere(req.getNomMere());
        permis.setPlaceDeces(req.getPlaceDeces());
        permis.setCimetiere(tombe.getCimetiere());
        permis.setElement(cadavre);
        // ❌ permis.setAgent(agent) — champ inexistant dans PermisInhumation
        permis = permisRepo.save(permis);

        // ── Créer quittance ──────────────────────────────────
        QuittancePaiement quittance = null;
        if (req.getNumeroQuittance() != null && !req.getNumeroQuittance().isBlank()) {
            quittance = new QuittancePaiement();
            quittance.setDate(req.getDateDelivrance());
            quittance.setNumero(req.getNumeroQuittance());
            quittance.setPersonnePayeur(req.getPersonnePayeur());
            // ✅ motif est TypeMotif — si null on met PERMIS_INHUMATION par défaut
            quittance.setMotif(req.getMotif() != null ? req.getMotif() : TypeMotif.PERMIS_INHUMATION);
            quittance.setPermisInhumation(permis);
            quittance.setAgent(agent); // ✅ agent dans QuittancePaiement (pas PermisInhumation)
            quittance = quittanceRepo.save(quittance);
        }

        return toResponse(cadavre, tombe, permis, quittance, agent, cause);
    }

    @Override
    public InhumationNormaleResponse consulter(Long permisId) {
        PermisInhumation permis = permisRepo.findById(permisId)
                .orElseThrow(() -> new RuntimeException("رخصة الدفن غير موجودة"));
        Cadavre cadavre = (Cadavre) permis.getElement();
        QuittancePaiement quittance = quittanceRepo.findByPermisInhumationId(permisId).orElse(null);
        // agent récupéré depuis quittance si disponible
        Agent agent = quittance != null ? quittance.getAgent() : null;
        return toResponse(cadavre, cadavre.getTombe(), permis, quittance, agent, cadavre.getCauseDeces());
    }

    @Override
    public List<Cimetiere> listerCimetieres() { return cimetiereRepo.findAll(); }

    @Override
    public List<Tombe> listerTombesDisponibles(Long cimetiereId) {
        return tombeRepo.findByCimetiereIdAndOccupeFalse(cimetiereId);
    }

    private InhumationNormaleResponse toResponse(
            Cadavre cadavre, Tombe tombe, PermisInhumation permis,
            QuittancePaiement quittance, Agent agent, CauseDeces cause) {

        InhumationNormaleResponse res = new InhumationNormaleResponse();
        res.setCadavreId(cadavre.getId());
        res.setDateDeces(cadavre.getDateDeces());
        res.setPlaceDeces(cadavre.getPlaceDeces());
        if (cadavre.getNationalite() != null)
            res.setNationalite(cadavre.getNationalite().name());
        if (cause != null)
            res.setCauseDecesLibelle(cause.getLibelle());

        if (cadavre instanceof CadavreConnu cc) {
            res.setTypeCadavre("CONNU");
            res.setNomComplet(cc.getNom() + " " + cc.getPrenom());
        } else {
            res.setTypeCadavre("INCONNU");
            res.setNomComplet("مجهول الهوية");
        }

        res.setTombeNumero(tombe.getNumero());
        res.setTombeTaille(tombe.getTaille().name());
        if (tombe.getCimetiere() != null)
            res.setCimetiereNom(tombe.getCimetiere().getNom());

        res.setPermisId(permis.getId());
        res.setNumPermis(permis.getNumPermis());
        res.setDateDelivrance(permis.getDateDelivrance());
        if (agent != null)
            res.setAgentNom(agent.getNom() + " " + agent.getPrenom());
        if (quittance != null) {
            res.setQuittanceId(quittance.getId());
            res.setNumeroQuittance(quittance.getNumero());
            res.setMotifQuittance(quittance.getMotif()); // ✅ TypeMotif → TypeMotif, pas d'erreur
        }
        return res;
    }
}

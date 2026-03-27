package com.gestionCimetieres.Service;
import com.gestionCimetieres.Entites.*;
import com.gestionCimetieres.Repository.*;
import com.gestionCimetieres.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DemandeTransfertService implements IDemandeTransfertService {

    private final DemandeTransfertRepository demandeTransfertRepo;
    private final OrdreMissionRepository     ordreMissionRepo;
    private final AgentRepository            agentRepo;
    private final ForceSecuriteRepository    forceSecuriteRepo;
    private final ZoneRepository             zoneRepo;
    private final CadavreRepository          cadavreRepo;
    private final EquipeRepository           equipeRepo;
    private final MembreEquipeRepository     membreEquipeRepo;

    @Override
    @Transactional
    public DemandeTransfertResponse creer(DemandeTransfertRequest req) {
        Agent agent = agentRepo.findById(req.getAgentId())
                .orElseThrow(() -> new RuntimeException("عون غير موجود : id=" + req.getAgentId()));
        ForceSecurite force = forceSecuriteRepo.findById(req.getForceSecuriteId())
                .orElseThrow(() -> new RuntimeException("قوة أمن غير موجودة"));
        Zone zone = zoneRepo.findById(req.getZoneId())
                .orElseThrow(() -> new RuntimeException("منطقة غير موجودة"));

        List<Cadavre> cadavres = new ArrayList<>();
        if (req.getCadavreIds() != null && !req.getCadavreIds().isEmpty()) {
            cadavres = cadavreRepo.findAllById(req.getCadavreIds());
            if (cadavres.size() != req.getCadavreIds().size())
                throw new RuntimeException("بعض الجثث غير موجودة");
        }

        DemandeTransfert demande = new DemandeTransfert();
        demande.setDate(req.getDate());
        demande.setLieuRecuperation(req.getLieuRecuperation());
        demande.setNombreCadavres(req.getNombreCadavres());
        demande.setStatut("جارٍ");
        demande.setNumeroBureauOrdre(req.getNumeroBureauOrdre());
        demande.setDateBureauOrdre(req.getDateBureauOrdre());
        demande.setNumeroArchive(req.getNumeroArchive());
        demande.setAgent(agent);
        demande.setForceSecurite(force);
        demande.setZone(zone);
        demande.setCadavres(cadavres);

        return toResponse(demandeTransfertRepo.save(demande));
    }

    @Override
    @Transactional
    public DemandeTransfertResponse emettreOrdreMission(Long demandeId, OrdreMissionRequest req) {

        DemandeTransfert demande = demandeTransfertRepo.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("طلب نقل غير موجود : id=" + demandeId));

        // 1. Créer l'équipe
        Equipe equipe = null;
        if (req.getMembres() != null && !req.getMembres().isEmpty()) {
            equipe = new Equipe();
            equipe.setDate(req.getEquipeDate() != null ? req.getEquipeDate() : LocalDate.now());
            equipe = equipeRepo.save(equipe);

            for (OrdreMissionRequest.MembreDTO m : req.getMembres()) {
                if (m.getNom() == null || m.getNom().isBlank()) continue;
                MembreEquipe membre = new MembreEquipe();
                membre.setNom(m.getNom());
                membre.setCin(m.getCin());
                membre.setEquipe(equipe);
                membreEquipeRepo.save(membre);
            }
        }

        // 2. Créer l'ordre de mission
        OrdreMission ordre = new OrdreMission();
        ordre.setNumero(ordreMissionRepo.genererProchainNumero());
        ordre.setLieuDepart(req.getLieuDepart());
        ordre.setHeureDepart(req.getHeureDepart());
        ordre.setLieuArrivee(req.getLieuArrivee());
        ordre.setHeureRetour(req.getHeureRetour());
        ordre.setDateDepart(req.getDateDepart());
        ordre.setDateRetour(req.getDateRetour());
        ordre.setVehicule(req.getVehicule());
        ordre.setEquipe(equipe);
        ordre.setDemandeTransfert(demande);
        ordreMissionRepo.save(ordre);

        return toResponse(demandeTransfertRepo.findById(demandeId).orElseThrow());
    }

    @Override
    @Transactional
    public DemandeTransfertResponse completerOrdreMission(Long demandeId, OrdreMissionRequest req) {

        OrdreMission ordre = ordreMissionRepo.findByDemandeTransfertId(demandeId)
                .orElseThrow(() -> new RuntimeException("أمر المهمة غير موجود"));

        ordre.setLieuDepart(req.getLieuDepart());
        ordre.setHeureDepart(req.getHeureDepart());
        ordre.setLieuArrivee(req.getLieuArrivee());
        ordre.setHeureRetour(req.getHeureRetour());
        ordre.setDateDepart(req.getDateDepart());
        ordre.setDateRetour(req.getDateRetour());
        ordre.setVehicule(req.getVehicule());

        // Mettre à jour les membres si fournis
        if (req.getMembres() != null && !req.getMembres().isEmpty() && ordre.getEquipe() != null) {
            membreEquipeRepo.deleteAll(
                membreEquipeRepo.findByEquipeId(ordre.getEquipe().getId())
            );
            for (OrdreMissionRequest.MembreDTO m : req.getMembres()) {
                if (m.getNom() == null || m.getNom().isBlank()) continue;
                MembreEquipe mb = new MembreEquipe();
                mb.setNom(m.getNom());
                mb.setCin(m.getCin());
                mb.setEquipe(ordre.getEquipe());
                membreEquipeRepo.save(mb);
            }
        }
        ordreMissionRepo.save(ordre);

        return toResponse(demandeTransfertRepo.findById(demandeId).orElseThrow());
    }

    @Override
    public List<DemandeTransfertResponse> listerToutes() {
        return demandeTransfertRepo.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<DemandeTransfertResponse> listerParStatut(String statut) {
        return demandeTransfertRepo.findByStatut(statut).stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public DemandeTransfertResponse consulter(Long id) {
        return toResponse(demandeTransfertRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("طلب نقل غير موجود : id=" + id)));
    }

    @Override
    @Transactional
    public DemandeTransfertResponse mettreAJourStatut(Long id, String statut) {
        List<String> valides = List.of("جارٍ", "منجز", "معلّق");
        if (!valides.contains(statut))
            throw new RuntimeException("قيمة الحالة غير صحيحة");
        DemandeTransfert d = demandeTransfertRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("طلب نقل غير موجود"));
        d.setStatut(statut);
        return toResponse(demandeTransfertRepo.save(d));
    }

    @Override
    @Transactional
    public void supprimer(Long id) {
        if (!demandeTransfertRepo.existsById(id))
            throw new RuntimeException("طلب نقل غير موجود : id=" + id);
        demandeTransfertRepo.deleteById(id);
    }

    @Override public List<ForceSecurite> listerForces() { return forceSecuriteRepo.findAll(); }
    @Override public List<Zone>          listerZones()  { return zoneRepo.findAll();
    }
   
    private DemandeTransfertResponse toResponse(DemandeTransfert demande) {
        DemandeTransfertResponse res = new DemandeTransfertResponse();
        res.setId(demande.getId());
        res.setDate(demande.getDate());
        res.setLieuRecuperation(demande.getLieuRecuperation());
        res.setNombreCadavres(demande.getNombreCadavres());
        res.setStatut(demande.getStatut());
        res.setNumeroBureauOrdre(demande.getNumeroBureauOrdre());
        res.setDateBureauOrdre(demande.getDateBureauOrdre());
        res.setNumeroArchive(demande.getNumeroArchive());

        if (demande.getAgent() != null) {
            res.setAgentNom(demande.getAgent().getNom());
            res.setAgentPrenom(demande.getAgent().getPrenom());
        }
        if (demande.getForceSecurite() != null)
            res.setForceSecuriteNom(demande.getForceSecurite().getNomUnite());
        if (demande.getZone() != null)
            res.setZoneNom(demande.getZone().getNom());

        if (demande.getCadavres() != null) {
            List<String> noms = demande.getCadavres().stream().map(c -> {
                if (c instanceof CadavreConnu cc) return cc.getNom() + " " + cc.getPrenom();
                return "جثة مجهولة الهوية";
            }).collect(Collectors.toList());
            res.setCadavresNoms(noms);
        }

        ordreMissionRepo.findByDemandeTransfertId(demande.getId()).ifPresentOrElse(
            o -> {
                res.setOredreMissionId(o.getId());
                res.setOrdreMissionNumero(o.getNumero());
                res.setOrdreMissionEmis(true);
            },
            () -> res.setOrdreMissionEmis(false)
        );

        return res;
    }
}

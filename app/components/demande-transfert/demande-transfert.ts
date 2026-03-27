import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DemandeTransfertService } from '../../services/demande-transfert.service';
import { AuthService } from '../../services/auth.service';
import {
  ForceSecurite, Zone,
  CadavreForm, DemandeTransfertRequest, DemandeTransfertResponse, OrdreMissionRequest
} from '../../models/models';

@Component({
  selector: 'app-demande-transfert',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './demande-transfert.html',
  styleUrls: ['./demande-transfert.css']
})
export class DemandeTransfert implements OnInit {
  forces: ForceSecurite[] = [];
  zones: Zone[] = [];
  
  date = new Date().toISOString().split('T')[0];
  forceSecuriteId?: number;
  zoneId?: number;
  nombreCadavres = 1;
  lieuRecuperation = '';
  numeroBureauOrdre = '';
  dateBureauOrdre = '';
  numeroArchive = '';

  cadavres: CadavreForm[] = [{ type: 'CONNU' }];

  chargement = false;
  succes = false;
  erreur = '';
  agentNom = '';

  // Variable UNIFIÉE pour correspondre à votre HTML et aux erreurs précédentes
  dernierResultat?: DemandeTransfertResponse;

  // Ordre de mission
  afficherModalOrdre = false;
  ordreForm: OrdreMissionRequest = {};
  ordreChargement = false;
  ordreSucces = false;
  ordreErreur = '';
  
  // Initialisation de l'équipe
  membres: { nom: string; cin: string }[] = [
    { nom: '', cin: '' },
    { nom: '', cin: '' }
  ];

  constructor(
    private demandeService: DemandeTransfertService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.demandeService.getForces().subscribe({
      next: (f) => this.forces = f,
      error: (e) => console.error('Erreur forces:', e)
    });

    this.demandeService.getZones().subscribe({
      next: (z) => this.zones = z,
      error: (e) => console.error('Erreur zones:', e)
    });

    const agent = this.authService.getAgentConnecte();
    this.agentNom = agent ? `${agent.prenom} ${agent.nom}` : '';
  }

  onNombreChange(): void {
    const n = Number(this.nombreCadavres);
    while (this.cadavres.length < n) this.cadavres.push({ type: 'CONNU' });
    this.cadavres = this.cadavres.slice(0, n);
  }

  setType(i: number, type: 'CONNU' | 'INCONNU'): void {
    this.cadavres[i] = { ...this.cadavres[i], type };
  }

  private valider(): boolean {
    if (!this.forceSecuriteId || !this.zoneId || !this.lieuRecuperation.trim()) {
      this.erreur = 'يرجى ملء جميع الخانات الإجبارية (*)';
      return false;
    }
    return true;
  }

  // Méthode renommée en 'enregistrer' pour correspondre à votre HTML (Erreur 2339)
  enregistrer(): void {
    this.erreur = '';
    this.succes = false;
    if (!this.valider()) return;

    this.chargement = true;

    const req: DemandeTransfertRequest = {
      date: this.date,
      lieuRecuperation: this.lieuRecuperation,
      nombreCadavres: this.nombreCadavres,
      numeroBureauOrdre: this.numeroBureauOrdre || undefined,
      dateBureauOrdre: this.dateBureauOrdre || undefined,
      numeroArchive: this.numeroArchive || undefined,
      agentId: this.authService.getAgentId(),
      forceSecuriteId: this.forceSecuriteId!,
      zoneId: this.zoneId!,
      cadavreIds: [] 
    };

    this.demandeService.creer(req).subscribe({
      next: (res) => {
        this.chargement = false;
        this.succes = true;
        this.dernierResultat = res; // On stocke dans dernierResultat
      },
      error: (err) => {
        this.chargement = false;
        this.erreur = err.error?.message ?? 'خطأ في الاتصال بالسيرفر';
      }
    });
  }

  ouvrirModalOrdre(): void {
    this.ordreSucces = false;
    this.ordreErreur = '';
    this.ordreForm = {
      lieuDepart: 'مصلحة الحالة المدنية',
      lieuArrivee: this.dernierResultat?.lieuRecuperation ?? '',
      dateDepart: this.dernierResultat?.date ?? this.date,
    };
    this.afficherModalOrdre = true;
  }

  fermerModalOrdre(): void {
    this.afficherModalOrdre = false;
  }

  emettreOrdre(): void {
    if (!this.dernierResultat) return;

    const membresValides = this.membres.filter(m => m.nom.trim() !== '');
    if (membresValides.length === 0) {
      this.ordreErreur = 'يرجى إضافة مرافق واحد على الأقل';
      return;
    }

    this.ordreChargement = true;
    
    // On prépare la requête avec les membres saisis
    const request: OrdreMissionRequest = {
      ...this.ordreForm,
      membres: membresValides
    };

    this.demandeService.emettreOrdreMission(this.dernierResultat.id, request).subscribe({
      next: (res) => {
        this.ordreChargement = false;
        this.ordreSucces = true;
        this.dernierResultat = res; // Mise à jour pour avoir l'ID de l'ordre de mission
        this.afficherModalOrdre = false;
      },
      error: (err) => {
        this.ordreChargement = false;
        this.ordreErreur = err.error?.message ?? 'خطأ في إنشاء أمر المهمة';
      }
    });
  }

  ajouterMembre(): void {
    this.membres.push({ nom: '', cin: '' });
  }

  supprimerMembre(i: number): void {
    if (this.membres.length > 1) this.membres.splice(i, 1);
  }

  // Méthodes d'impression utilisant dernierResultat
  imprimerDemande(): void {
    if (this.dernierResultat?.id) {
      window.open(`http://localhost:9999/impression/demande/${this.dernierResultat.id}`, '_blank');
    }
  }



  resetFormulaire(): void {
    location.reload(); // Solution la plus propre pour réinitialiser tout le composant
  }
}
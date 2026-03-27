import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({ providedIn: 'root' })
export class InhumationNormaleService {
  private base = 'http://localhost:9999/api/inhumations-normales';
  constructor(private http: HttpClient) {}

  listerCimetieres(): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/cimetieres`);
  }
  listerTombesDisponibles(cimetiereId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/cimetieres/${cimetiereId}/tombes`);
  }
  enregistrer(req: any): Observable<any> {
    return this.http.post<any>(this.base, req);
  }
  consulter(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/${id}`);
  }
}
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-inhumation-normale',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inhumation-normale.html',
  styleUrls: ['./inhumation-normale.css']
})
export class InhumationNormale implements OnInit {

  //  Listes de référence 
  cimetieres: any[] = [];
  tombes:     any[] = [];

  //  Section 1 : défunt 
  typeCadavre: 'CONNU' | 'INCONNU' = 'CONNU';
  nom         = '';
  prenom      = '';
  age?: number;
  cin         = '';
  numeroHebergement        = '';
  numeroMedicoLegal        = '';
  dateSortieServiceMedical = '';
  dateDeces    = '';
  heureDeces   = '';
  placeDeces   = '';
  nationalite  = 'TUNISIEN';
  causeDecesId?: number;

  //  Section 2 : tombe 
  cimetiereId?: number;
  tombeId?:     number;

  //  Section 3 : permis
  dateDelivrance = new Date().toISOString().split('T')[0];
  nomDefunt      = '';
  nomPere        = '';
  nomMere        = '';

  //  Section 4 : quittance 
  numeroQuittance = '';
  personnePayeur  = '';
  motif           = 'PERMIS_INHUMATION'; // TypeMotif par défaut

  motifOptions = [
    { value: 'PERMIS_INHUMATION', label: 'رخصة دفن'      },
    { value: 'TRANSPORT',         label: 'نقل جثة'        },
    { value: 'ENLEVEMENT',        label: 'إزالة مشيمة'   },
    { value: 'SUBVENTION_TERRE',  label: 'إعانة قطعة أرض' },
  ];

  //  Causes statiques (SQL) 
  causes = [
    { id: 1, libelle: 'حادث مرور'   },
    { id: 2, libelle: 'غرق'          },
    { id: 3, libelle: 'سبب مجهول'  },
    { id: 4, libelle: 'وفاة طبيعية' },
    { id: 5, libelle: 'مرض'          },
  ];

  //  État UI 
  chargement       = false;
  succes           = false;
  erreur           = '';
  agentNom         = '';
  dernierResultat?: any;

  constructor(
    private service:     InhumationNormaleService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const agent   = this.authService.getAgentConnecte();
    this.agentNom = `${agent.prenom} ${agent.nom}`;

    this.service.listerCimetieres().subscribe({
      next: (c) => this.cimetieres = c,
      error: (e) => console.error('Erreur cimetières:', e)
    });
  }

  //  Charger tombes disponibles ─
  onCimetiereChange(): void {
    this.tombeId = undefined;
    this.tombes  = [];
    if (!this.cimetiereId) return;
    this.service.listerTombesDisponibles(this.cimetiereId).subscribe({
      next: (t) => this.tombes = t,
      error: (e) => console.error('Erreur tombes:', e)
    });
  }

  //  Type cadavre ─
  setType(type: 'CONNU' | 'INCONNU'): void {
    this.typeCadavre = type;
    if (type === 'INCONNU') {
      this.nom    = '';
      this.prenom = '';
      this.cin    = '';
      this.nomDefunt = '';
    }
  }

  //  Auto-remplir nomDefunt ─
  onNomChange(): void {
    if (this.typeCadavre === 'CONNU')
      this.nomDefunt = `${this.nom} ${this.prenom}`.trim();
  }

  //  Taille tombe ─
  getTailleLabel(taille: string): string {
    return taille === 'GRAND' ? 'كبير' : 'صغير';
  }

  //  Validation ─
  private valider(): boolean {
    if (this.typeCadavre === 'CONNU' && !this.nom.trim()) {
      this.erreur = 'يرجى إدخال لقب المتوفي'; return false;
    }
    if (this.typeCadavre === 'INCONNU' && !this.numeroHebergement.trim()) {
      this.erreur = 'يرجى إدخال رقم الإيواء'; return false;
    }
    if (!this.cimetiereId) {
      this.erreur = 'يرجى اختيار المقبرة'; return false;
    }
    if (!this.tombeId) {
      this.erreur = 'يرجى اختيار القبر'; return false;
    }
    if (!this.dateDelivrance) {
      this.erreur = 'يرجى إدخال تاريخ تسليم الرخصة'; return false;
    }
    return true;
  }

  //  Enregistrer 
  enregistrer(): void {
    this.erreur = '';
    this.succes = false;
    if (!this.valider()) return;

    this.chargement = true;

    const req: any = {
      typeCadavre:    this.typeCadavre,
      dateDeces:      this.dateDeces      || undefined,
      heureDeces:     this.heureDeces     || undefined,
      placeDeces:     this.placeDeces     || undefined,
      nationalite:    this.nationalite,
      causeDecesId:   this.causeDecesId   || undefined,
      tombeId:        this.tombeId,
      cimetiereId:    this.cimetiereId,
      dateDelivrance: this.dateDelivrance,
      nomDefunt:      this.nomDefunt      || undefined,
      nomPere:        this.nomPere        || undefined,
      nomMere:        this.nomMere        || undefined,
      numeroQuittance: this.numeroQuittance || undefined,
      personnePayeur:  this.personnePayeur  || undefined,
      motif:           this.motif,
      agentId:         this.authService.getAgentId(),
    };

    if (this.typeCadavre === 'CONNU') {
      req.nom    = this.nom;
      req.prenom = this.prenom;
      req.age    = this.age    || 0;
      req.cin    = this.cin    || undefined;
    } else {
      req.numeroHebergement        = this.numeroHebergement;
      req.numeroMedicoLegal        = this.numeroMedicoLegal        || undefined;
      req.dateSortieServiceMedical = this.dateSortieServiceMedical || undefined;
    }

    this.service.enregistrer(req).subscribe({
      next: (res: any) => {
        this.chargement      = false;
        this.succes          = true;
        this.dernierResultat = res;
      },
      error: (err) => {
        this.chargement = false;
        this.erreur = err.error?.message ?? 'خطأ في الخادم. تأكد من تشغيل Spring Boot.';
      }
    });
  }

  //  Impression ─
imprimer(): void {
  if (!this.dernierResultat?.permisId) return;
  window.open(
    `http://localhost:9999/impression/permis/${this.dernierResultat.permisId}`,
    '_blank'
  );
}

  //  Reset 
  reset(): void {
    this.typeCadavre   = 'CONNU';
    this.nom = this.prenom = this.cin = '';
    this.age = this.causeDecesId = this.cimetiereId = this.tombeId = undefined;
    this.dateDeces = this.heureDeces = this.placeDeces = '';
    this.nomDefunt = this.nomPere = this.nomMere = '';
    this.numeroHebergement = this.numeroMedicoLegal = this.dateSortieServiceMedical = '';
    this.numeroQuittance = this.personnePayeur = '';
    this.motif         = 'PERMIS_INHUMATION';
    this.tombes        = [];
    this.succes        = false;
    this.erreur        = '';
    this.dernierResultat = undefined;
    this.dateDelivrance  = new Date().toISOString().split('T')[0];
  }
}

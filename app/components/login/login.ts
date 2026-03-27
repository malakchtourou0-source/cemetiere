import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {

  login      = '';
  motDePasse = '';
  erreur     = '';
  chargement = false;

  constructor(private authService: AuthService, private router: Router) {}

  seConnecter(): void {
    if (!this.login || !this.motDePasse) {
      this.erreur = 'يرجى ملء جميع الحقول';  
      return;
    }
    this.erreur     = '';
    this.chargement = true;

    this.authService.login({ login: this.login, motDePasse: this.motDePasse })
      .subscribe({
        next: () => {
          this.chargement = false;
          this.router.navigate(['/demande-transfert']);
        },
        error: () => {
          this.chargement = false;
          this.erreur = 'معرّف الدخول أو كلمة المرور غير صحيحة';
        }
      });
  }
}

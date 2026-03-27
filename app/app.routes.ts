import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { DemandeTransfert } from './components/demande-transfert/demande-transfert';
import { authGuard } from './guards/auth.guards';
import { Layout } from './layout/layout';
import { InhumationNormale } from './components/inhumation-normale/inhumation-normale';

export const routes: Routes = [
  { path: 'login', component: Login },
  {
    path: '',
    component: Layout,
    canActivate: [authGuard],
    children: [
      { path: '',                  redirectTo: 'demande-transfert', pathMatch: 'full' },
      { path: 'demande-transfert', component: DemandeTransfert },
      { path: 'inhumation-normale', component: InhumationNormale }, 

    ]
  },
  { path: '**', redirectTo: 'login' }
];
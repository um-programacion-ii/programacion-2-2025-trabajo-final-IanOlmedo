import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'gestionEventosApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'evento',
    data: { pageTitle: 'gestionEventosApp.evento.home.title' },
    loadChildren: () => import('./evento/evento.routes'),
  },
  {
    path: 'asiento',
    data: { pageTitle: 'gestionEventosApp.asiento.home.title' },
    loadChildren: () => import('./asiento/asiento.routes'),
  },
  {
    path: 'venta',
    data: { pageTitle: 'gestionEventosApp.venta.home.title' },
    loadChildren: () => import('./venta/venta.routes'),
  },
  {
    path: 'sesion-usuario',
    data: { pageTitle: 'gestionEventosApp.sesionUsuario.home.title' },
    loadChildren: () => import('./sesion-usuario/sesion-usuario.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;

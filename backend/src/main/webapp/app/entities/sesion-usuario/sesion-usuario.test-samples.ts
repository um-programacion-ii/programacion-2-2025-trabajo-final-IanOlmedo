import dayjs from 'dayjs/esm';

import { ISesionUsuario, NewSesionUsuario } from './sesion-usuario.model';

export const sampleWithRequiredData: ISesionUsuario = {
  id: 9734,
  ultimaActualizacion: dayjs('2025-11-11T18:36'),
};

export const sampleWithPartialData: ISesionUsuario = {
  id: 9060,
  estadoFlujo: 'state membership overcooked',
  datosTemporales: 'flimsy expansion',
  ultimaActualizacion: dayjs('2025-11-11T13:04'),
};

export const sampleWithFullData: ISesionUsuario = {
  id: 4632,
  estadoFlujo: 'indeed',
  datosTemporales: 'personal',
  ultimaActualizacion: dayjs('2025-11-12T08:17'),
};

export const sampleWithNewData: NewSesionUsuario = {
  ultimaActualizacion: dayjs('2025-11-12T11:42'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

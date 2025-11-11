import dayjs from 'dayjs/esm';

import { IEvento, NewEvento } from './evento.model';

export const sampleWithRequiredData: IEvento = {
  id: 21654,
  titulo: 'inwardly chromakey',
  precioEntrada: 20852.4,
};

export const sampleWithPartialData: IEvento = {
  id: 27897,
  titulo: 'huzzah',
  fecha: dayjs('2025-11-11'),
  resumen: 'larva',
  precioEntrada: 32251.07,
};

export const sampleWithFullData: IEvento = {
  id: 26545,
  titulo: 'outlaw upon',
  fecha: dayjs('2025-11-11'),
  resumen: 'testing given er',
  precioEntrada: 11267.94,
};

export const sampleWithNewData: NewEvento = {
  titulo: 'wherever nor',
  precioEntrada: 9802.49,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

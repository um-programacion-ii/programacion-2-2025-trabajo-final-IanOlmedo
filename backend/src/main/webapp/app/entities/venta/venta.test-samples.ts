import dayjs from 'dayjs/esm';

import { IVenta, NewVenta } from './venta.model';

export const sampleWithRequiredData: IVenta = {
  id: 21245,
  fechaVenta: dayjs('2025-11-11T18:17'),
};

export const sampleWithPartialData: IVenta = {
  id: 28926,
  fechaVenta: dayjs('2025-11-12T06:43'),
  estado: 'PENDIENTE',
};

export const sampleWithFullData: IVenta = {
  id: 8723,
  fechaVenta: dayjs('2025-11-12T07:09'),
  estado: 'FALLIDA',
};

export const sampleWithNewData: NewVenta = {
  fechaVenta: dayjs('2025-11-11T14:14'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

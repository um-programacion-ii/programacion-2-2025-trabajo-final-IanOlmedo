import { IAsiento, NewAsiento } from './asiento.model';

export const sampleWithRequiredData: IAsiento = {
  id: 13756,
  fila: 'apropos pfft tremendously',
  numero: 17753,
};

export const sampleWithPartialData: IAsiento = {
  id: 7360,
  fila: 'apud',
  numero: 11096,
  estado: 'VENDIDO',
};

export const sampleWithFullData: IAsiento = {
  id: 26443,
  fila: 'geez unique than',
  numero: 5815,
  estado: 'LIBRE',
};

export const sampleWithNewData: NewAsiento = {
  fila: 'bitterly source impolite',
  numero: 1306,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

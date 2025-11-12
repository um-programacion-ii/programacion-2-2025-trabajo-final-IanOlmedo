import dayjs from 'dayjs/esm';

export interface IEvento {
  id: number;
  titulo?: string | null;
  fecha?: dayjs.Dayjs | null;
  resumen?: string | null;
  precioEntrada?: number | null;
}

export type NewEvento = Omit<IEvento, 'id'> & { id: null };

import { IEvento } from 'app/entities/evento/evento.model';
import { EstadoAsiento } from 'app/entities/enumerations/estado-asiento.model';

export interface IAsiento {
  id: number;
  fila?: string | null;
  numero?: number | null;
  estado?: keyof typeof EstadoAsiento | null;
  evento_con_asientos?: Pick<IEvento, 'id' | 'titulo'> | null;
}

export type NewAsiento = Omit<IAsiento, 'id'> & { id: null };

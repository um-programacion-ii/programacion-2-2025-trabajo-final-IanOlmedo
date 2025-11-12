import dayjs from 'dayjs/esm';
import { IEvento } from 'app/entities/evento/evento.model';
import { IAsiento } from 'app/entities/asiento/asiento.model';
import { IUser } from 'app/entities/user/user.model';
import { EstadoVenta } from 'app/entities/enumerations/estado-venta.model';

export interface IVenta {
  id: number;
  fechaVenta?: dayjs.Dayjs | null;
  estado?: keyof typeof EstadoVenta | null;
  evento?: Pick<IEvento, 'id' | 'titulo'> | null;
  asientos?: Pick<IAsiento, 'id' | 'numero'>[] | null;
  usuario?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewVenta = Omit<IVenta, 'id'> & { id: null };

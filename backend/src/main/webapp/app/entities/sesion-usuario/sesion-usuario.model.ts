import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface ISesionUsuario {
  id: number;
  estadoFlujo?: string | null;
  datosTemporales?: string | null;
  ultimaActualizacion?: dayjs.Dayjs | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewSesionUsuario = Omit<ISesionUsuario, 'id'> & { id: null };

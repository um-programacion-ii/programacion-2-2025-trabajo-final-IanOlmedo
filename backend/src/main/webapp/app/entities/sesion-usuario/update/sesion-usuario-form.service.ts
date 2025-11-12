import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISesionUsuario, NewSesionUsuario } from '../sesion-usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISesionUsuario for edit and NewSesionUsuarioFormGroupInput for create.
 */
type SesionUsuarioFormGroupInput = ISesionUsuario | PartialWithRequiredKeyOf<NewSesionUsuario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISesionUsuario | NewSesionUsuario> = Omit<T, 'ultimaActualizacion'> & {
  ultimaActualizacion?: string | null;
};

type SesionUsuarioFormRawValue = FormValueOf<ISesionUsuario>;

type NewSesionUsuarioFormRawValue = FormValueOf<NewSesionUsuario>;

type SesionUsuarioFormDefaults = Pick<NewSesionUsuario, 'id' | 'ultimaActualizacion'>;

type SesionUsuarioFormGroupContent = {
  id: FormControl<SesionUsuarioFormRawValue['id'] | NewSesionUsuario['id']>;
  estadoFlujo: FormControl<SesionUsuarioFormRawValue['estadoFlujo']>;
  datosTemporales: FormControl<SesionUsuarioFormRawValue['datosTemporales']>;
  ultimaActualizacion: FormControl<SesionUsuarioFormRawValue['ultimaActualizacion']>;
  user: FormControl<SesionUsuarioFormRawValue['user']>;
};

export type SesionUsuarioFormGroup = FormGroup<SesionUsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SesionUsuarioFormService {
  createSesionUsuarioFormGroup(sesionUsuario: SesionUsuarioFormGroupInput = { id: null }): SesionUsuarioFormGroup {
    const sesionUsuarioRawValue = this.convertSesionUsuarioToSesionUsuarioRawValue({
      ...this.getFormDefaults(),
      ...sesionUsuario,
    });
    return new FormGroup<SesionUsuarioFormGroupContent>({
      id: new FormControl(
        { value: sesionUsuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      estadoFlujo: new FormControl(sesionUsuarioRawValue.estadoFlujo),
      datosTemporales: new FormControl(sesionUsuarioRawValue.datosTemporales),
      ultimaActualizacion: new FormControl(sesionUsuarioRawValue.ultimaActualizacion, {
        validators: [Validators.required],
      }),
      user: new FormControl(sesionUsuarioRawValue.user),
    });
  }

  getSesionUsuario(form: SesionUsuarioFormGroup): ISesionUsuario | NewSesionUsuario {
    return this.convertSesionUsuarioRawValueToSesionUsuario(form.getRawValue() as SesionUsuarioFormRawValue | NewSesionUsuarioFormRawValue);
  }

  resetForm(form: SesionUsuarioFormGroup, sesionUsuario: SesionUsuarioFormGroupInput): void {
    const sesionUsuarioRawValue = this.convertSesionUsuarioToSesionUsuarioRawValue({ ...this.getFormDefaults(), ...sesionUsuario });
    form.reset(
      {
        ...sesionUsuarioRawValue,
        id: { value: sesionUsuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SesionUsuarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ultimaActualizacion: currentTime,
    };
  }

  private convertSesionUsuarioRawValueToSesionUsuario(
    rawSesionUsuario: SesionUsuarioFormRawValue | NewSesionUsuarioFormRawValue,
  ): ISesionUsuario | NewSesionUsuario {
    return {
      ...rawSesionUsuario,
      ultimaActualizacion: dayjs(rawSesionUsuario.ultimaActualizacion, DATE_TIME_FORMAT),
    };
  }

  private convertSesionUsuarioToSesionUsuarioRawValue(
    sesionUsuario: ISesionUsuario | (Partial<NewSesionUsuario> & SesionUsuarioFormDefaults),
  ): SesionUsuarioFormRawValue | PartialWithRequiredKeyOf<NewSesionUsuarioFormRawValue> {
    return {
      ...sesionUsuario,
      ultimaActualizacion: sesionUsuario.ultimaActualizacion ? sesionUsuario.ultimaActualizacion.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

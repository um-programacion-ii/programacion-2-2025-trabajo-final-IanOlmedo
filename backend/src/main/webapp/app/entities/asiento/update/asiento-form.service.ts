import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAsiento, NewAsiento } from '../asiento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAsiento for edit and NewAsientoFormGroupInput for create.
 */
type AsientoFormGroupInput = IAsiento | PartialWithRequiredKeyOf<NewAsiento>;

type AsientoFormDefaults = Pick<NewAsiento, 'id'>;

type AsientoFormGroupContent = {
  id: FormControl<IAsiento['id'] | NewAsiento['id']>;
  fila: FormControl<IAsiento['fila']>;
  numero: FormControl<IAsiento['numero']>;
  estado: FormControl<IAsiento['estado']>;
  evento_con_asientos: FormControl<IAsiento['evento_con_asientos']>;
};

export type AsientoFormGroup = FormGroup<AsientoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AsientoFormService {
  createAsientoFormGroup(asiento: AsientoFormGroupInput = { id: null }): AsientoFormGroup {
    const asientoRawValue = {
      ...this.getFormDefaults(),
      ...asiento,
    };
    return new FormGroup<AsientoFormGroupContent>({
      id: new FormControl(
        { value: asientoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fila: new FormControl(asientoRawValue.fila, {
        validators: [Validators.required],
      }),
      numero: new FormControl(asientoRawValue.numero, {
        validators: [Validators.required],
      }),
      estado: new FormControl(asientoRawValue.estado),
      evento_con_asientos: new FormControl(asientoRawValue.evento_con_asientos),
    });
  }

  getAsiento(form: AsientoFormGroup): IAsiento | NewAsiento {
    return form.getRawValue() as IAsiento | NewAsiento;
  }

  resetForm(form: AsientoFormGroup, asiento: AsientoFormGroupInput): void {
    const asientoRawValue = { ...this.getFormDefaults(), ...asiento };
    form.reset(
      {
        ...asientoRawValue,
        id: { value: asientoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AsientoFormDefaults {
    return {
      id: null,
    };
  }
}

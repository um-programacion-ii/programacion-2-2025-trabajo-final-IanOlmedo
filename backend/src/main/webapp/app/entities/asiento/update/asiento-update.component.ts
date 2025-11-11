import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEvento } from 'app/entities/evento/evento.model';
import { EventoService } from 'app/entities/evento/service/evento.service';
import { EstadoAsiento } from 'app/entities/enumerations/estado-asiento.model';
import { AsientoService } from '../service/asiento.service';
import { IAsiento } from '../asiento.model';
import { AsientoFormGroup, AsientoFormService } from './asiento-form.service';

@Component({
  selector: 'jhi-asiento-update',
  templateUrl: './asiento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AsientoUpdateComponent implements OnInit {
  isSaving = false;
  asiento: IAsiento | null = null;
  estadoAsientoValues = Object.keys(EstadoAsiento);

  eventosSharedCollection: IEvento[] = [];

  protected asientoService = inject(AsientoService);
  protected asientoFormService = inject(AsientoFormService);
  protected eventoService = inject(EventoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AsientoFormGroup = this.asientoFormService.createAsientoFormGroup();

  compareEvento = (o1: IEvento | null, o2: IEvento | null): boolean => this.eventoService.compareEvento(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asiento }) => {
      this.asiento = asiento;
      if (asiento) {
        this.updateForm(asiento);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const asiento = this.asientoFormService.getAsiento(this.editForm);
    if (asiento.id !== null) {
      this.subscribeToSaveResponse(this.asientoService.update(asiento));
    } else {
      this.subscribeToSaveResponse(this.asientoService.create(asiento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsiento>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(asiento: IAsiento): void {
    this.asiento = asiento;
    this.asientoFormService.resetForm(this.editForm, asiento);

    this.eventosSharedCollection = this.eventoService.addEventoToCollectionIfMissing<IEvento>(
      this.eventosSharedCollection,
      asiento.evento_con_asientos,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.eventoService
      .query()
      .pipe(map((res: HttpResponse<IEvento[]>) => res.body ?? []))
      .pipe(
        map((eventos: IEvento[]) => this.eventoService.addEventoToCollectionIfMissing<IEvento>(eventos, this.asiento?.evento_con_asientos)),
      )
      .subscribe((eventos: IEvento[]) => (this.eventosSharedCollection = eventos));
  }
}

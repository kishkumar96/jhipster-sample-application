import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IParticipantEntry, ParticipantEntry } from 'app/shared/model/participant-entry.model';
import { ParticipantEntryService } from './participant-entry.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';

@Component({
  selector: 'jhi-participant-entry-update',
  templateUrl: './participant-entry-update.component.html'
})
export class ParticipantEntryUpdateComponent implements OnInit {
  isSaving = false;

  sessions: ISession[] = [];
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    individualCode: [],
    firstName: [],
    lastName: [],
    country: [],
    gender: [],
    title: [],
    dateOfBirth: [],
    organization: [],
    sector: [],
    position: [],
    contactAddress: [],
    workPhone: [],
    faxNumber: [],
    homePhone: [],
    email: [],
    previousEmployment: [],
    specialGeneral: [],
    specialDisasterManagement: [],
    educationLevel: [],
    trainer: [],
    comments: [],
    sessionCodes: []
  });

  constructor(
    protected participantEntryService: ParticipantEntryService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participantEntry }) => {
      this.updateForm(participantEntry);

      this.sessionService
        .query()
        .pipe(
          map((res: HttpResponse<ISession[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ISession[]) => (this.sessions = resBody));
    });
  }

  updateForm(participantEntry: IParticipantEntry): void {
    this.editForm.patchValue({
      id: participantEntry.id,
      individualCode: participantEntry.individualCode,
      firstName: participantEntry.firstName,
      lastName: participantEntry.lastName,
      country: participantEntry.country,
      gender: participantEntry.gender,
      title: participantEntry.title,
      dateOfBirth: participantEntry.dateOfBirth,
      organization: participantEntry.organization,
      sector: participantEntry.sector,
      position: participantEntry.position,
      contactAddress: participantEntry.contactAddress,
      workPhone: participantEntry.workPhone,
      faxNumber: participantEntry.faxNumber,
      homePhone: participantEntry.homePhone,
      email: participantEntry.email,
      previousEmployment: participantEntry.previousEmployment,
      specialGeneral: participantEntry.specialGeneral,
      specialDisasterManagement: participantEntry.specialDisasterManagement,
      educationLevel: participantEntry.educationLevel,
      trainer: participantEntry.trainer,
      comments: participantEntry.comments,
      sessionCodes: participantEntry.sessionCodes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const participantEntry = this.createFromForm();
    if (participantEntry.id !== undefined) {
      this.subscribeToSaveResponse(this.participantEntryService.update(participantEntry));
    } else {
      this.subscribeToSaveResponse(this.participantEntryService.create(participantEntry));
    }
  }

  private createFromForm(): IParticipantEntry {
    return {
      ...new ParticipantEntry(),
      id: this.editForm.get(['id'])!.value,
      individualCode: this.editForm.get(['individualCode'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      country: this.editForm.get(['country'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      title: this.editForm.get(['title'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      organization: this.editForm.get(['organization'])!.value,
      sector: this.editForm.get(['sector'])!.value,
      position: this.editForm.get(['position'])!.value,
      contactAddress: this.editForm.get(['contactAddress'])!.value,
      workPhone: this.editForm.get(['workPhone'])!.value,
      faxNumber: this.editForm.get(['faxNumber'])!.value,
      homePhone: this.editForm.get(['homePhone'])!.value,
      email: this.editForm.get(['email'])!.value,
      previousEmployment: this.editForm.get(['previousEmployment'])!.value,
      specialGeneral: this.editForm.get(['specialGeneral'])!.value,
      specialDisasterManagement: this.editForm.get(['specialDisasterManagement'])!.value,
      educationLevel: this.editForm.get(['educationLevel'])!.value,
      trainer: this.editForm.get(['trainer'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      sessionCodes: this.editForm.get(['sessionCodes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParticipantEntry>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ISession): any {
    return item.id;
  }

  getSelected(selectedVals: ISession[], option: ISession): ISession {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

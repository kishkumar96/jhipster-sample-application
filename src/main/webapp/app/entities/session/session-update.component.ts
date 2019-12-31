import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { ICourse } from 'app/shared/model/course.model';
import { CourseService } from 'app/entities/course/course.service';

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;

  courses: ICourse[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    sessionCode: [],
    nameOfActivity: [],
    location: [],
    startDate: [],
    endDate: [],
    attendeesNumber: [],
    sessionType: [],
    description: [],
    comment: [],
    courseCode: []
  });

  constructor(
    protected sessionService: SessionService,
    protected courseService: CourseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);

      this.courseService
        .query()
        .pipe(
          map((res: HttpResponse<ICourse[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICourse[]) => (this.courses = resBody));
    });
  }

  updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      sessionCode: session.sessionCode,
      nameOfActivity: session.nameOfActivity,
      location: session.location,
      startDate: session.startDate,
      endDate: session.endDate,
      attendeesNumber: session.attendeesNumber,
      sessionType: session.sessionType,
      description: session.description,
      comment: session.comment,
      courseCode: session.courseCode
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  private createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      sessionCode: this.editForm.get(['sessionCode'])!.value,
      nameOfActivity: this.editForm.get(['nameOfActivity'])!.value,
      location: this.editForm.get(['location'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      attendeesNumber: this.editForm.get(['attendeesNumber'])!.value,
      sessionType: this.editForm.get(['sessionType'])!.value,
      description: this.editForm.get(['description'])!.value,
      comment: this.editForm.get(['comment'])!.value,
      courseCode: this.editForm.get(['courseCode'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
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

  trackById(index: number, item: ICourse): any {
    return item.id;
  }
}

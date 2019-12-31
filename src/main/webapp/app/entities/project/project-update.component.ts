import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IProject, Project } from 'app/shared/model/project.model';
import { ProjectService } from './project.service';

@Component({
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html'
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    projectCode: [],
    projectName: [],
    objective: [],
    workPlan: [],
    kra: [],
    output: [],
    evaluation: [],
    budget: [],
    headOffProject: [],
    startDate: [],
    endDate: [],
    targetAudience: [],
    overviewAboutProject: []
  });

  constructor(protected projectService: ProjectService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.updateForm(project);
    });
  }

  updateForm(project: IProject): void {
    this.editForm.patchValue({
      id: project.id,
      projectCode: project.projectCode,
      projectName: project.projectName,
      objective: project.objective,
      workPlan: project.workPlan,
      kra: project.kra,
      output: project.output,
      evaluation: project.evaluation,
      budget: project.budget,
      headOffProject: project.headOffProject,
      startDate: project.startDate,
      endDate: project.endDate,
      targetAudience: project.targetAudience,
      overviewAboutProject: project.overviewAboutProject
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const project = this.createFromForm();
    if (project.id !== undefined) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  private createFromForm(): IProject {
    return {
      ...new Project(),
      id: this.editForm.get(['id'])!.value,
      projectCode: this.editForm.get(['projectCode'])!.value,
      projectName: this.editForm.get(['projectName'])!.value,
      objective: this.editForm.get(['objective'])!.value,
      workPlan: this.editForm.get(['workPlan'])!.value,
      kra: this.editForm.get(['kra'])!.value,
      output: this.editForm.get(['output'])!.value,
      evaluation: this.editForm.get(['evaluation'])!.value,
      budget: this.editForm.get(['budget'])!.value,
      headOffProject: this.editForm.get(['headOffProject'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      targetAudience: this.editForm.get(['targetAudience'])!.value,
      overviewAboutProject: this.editForm.get(['overviewAboutProject'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
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
}

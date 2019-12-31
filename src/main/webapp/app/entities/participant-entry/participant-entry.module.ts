import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { ParticipantEntryComponent } from './participant-entry.component';
import { ParticipantEntryDetailComponent } from './participant-entry-detail.component';
import { ParticipantEntryUpdateComponent } from './participant-entry-update.component';
import { ParticipantEntryDeleteDialogComponent } from './participant-entry-delete-dialog.component';
import { participantEntryRoute } from './participant-entry.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(participantEntryRoute)],
  declarations: [
    ParticipantEntryComponent,
    ParticipantEntryDetailComponent,
    ParticipantEntryUpdateComponent,
    ParticipantEntryDeleteDialogComponent
  ],
  entryComponents: [ParticipantEntryDeleteDialogComponent]
})
export class JhipsterSampleApplicationParticipantEntryModule {}

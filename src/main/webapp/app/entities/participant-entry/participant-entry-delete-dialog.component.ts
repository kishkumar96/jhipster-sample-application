import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParticipantEntry } from 'app/shared/model/participant-entry.model';
import { ParticipantEntryService } from './participant-entry.service';

@Component({
  templateUrl: './participant-entry-delete-dialog.component.html'
})
export class ParticipantEntryDeleteDialogComponent {
  participantEntry?: IParticipantEntry;

  constructor(
    protected participantEntryService: ParticipantEntryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.participantEntryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('participantEntryListModification');
      this.activeModal.close();
    });
  }
}

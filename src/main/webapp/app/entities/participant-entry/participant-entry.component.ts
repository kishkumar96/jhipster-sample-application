import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParticipantEntry } from 'app/shared/model/participant-entry.model';
import { ParticipantEntryService } from './participant-entry.service';
import { ParticipantEntryDeleteDialogComponent } from './participant-entry-delete-dialog.component';

@Component({
  selector: 'jhi-participant-entry',
  templateUrl: './participant-entry.component.html'
})
export class ParticipantEntryComponent implements OnInit, OnDestroy {
  participantEntries?: IParticipantEntry[];
  eventSubscriber?: Subscription;

  constructor(
    protected participantEntryService: ParticipantEntryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.participantEntryService.query().subscribe((res: HttpResponse<IParticipantEntry[]>) => {
      this.participantEntries = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParticipantEntries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParticipantEntry): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParticipantEntries(): void {
    this.eventSubscriber = this.eventManager.subscribe('participantEntryListModification', () => this.loadAll());
  }

  delete(participantEntry: IParticipantEntry): void {
    const modalRef = this.modalService.open(ParticipantEntryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.participantEntry = participantEntry;
  }
}

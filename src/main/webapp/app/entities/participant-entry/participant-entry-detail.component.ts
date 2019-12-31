import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParticipantEntry } from 'app/shared/model/participant-entry.model';

@Component({
  selector: 'jhi-participant-entry-detail',
  templateUrl: './participant-entry-detail.component.html'
})
export class ParticipantEntryDetailComponent implements OnInit {
  participantEntry: IParticipantEntry | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ participantEntry }) => {
      this.participantEntry = participantEntry;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

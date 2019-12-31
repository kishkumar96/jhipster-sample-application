import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ParticipantEntryDetailComponent } from 'app/entities/participant-entry/participant-entry-detail.component';
import { ParticipantEntry } from 'app/shared/model/participant-entry.model';

describe('Component Tests', () => {
  describe('ParticipantEntry Management Detail Component', () => {
    let comp: ParticipantEntryDetailComponent;
    let fixture: ComponentFixture<ParticipantEntryDetailComponent>;
    const route = ({ data: of({ participantEntry: new ParticipantEntry(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ParticipantEntryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ParticipantEntryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParticipantEntryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load participantEntry on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.participantEntry).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

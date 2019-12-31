import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ParticipantEntryComponent } from 'app/entities/participant-entry/participant-entry.component';
import { ParticipantEntryService } from 'app/entities/participant-entry/participant-entry.service';
import { ParticipantEntry } from 'app/shared/model/participant-entry.model';

describe('Component Tests', () => {
  describe('ParticipantEntry Management Component', () => {
    let comp: ParticipantEntryComponent;
    let fixture: ComponentFixture<ParticipantEntryComponent>;
    let service: ParticipantEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ParticipantEntryComponent],
        providers: []
      })
        .overrideTemplate(ParticipantEntryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParticipantEntryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParticipantEntryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ParticipantEntry(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.participantEntries && comp.participantEntries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

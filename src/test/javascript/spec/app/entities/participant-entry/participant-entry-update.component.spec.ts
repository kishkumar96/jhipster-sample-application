import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { ParticipantEntryUpdateComponent } from 'app/entities/participant-entry/participant-entry-update.component';
import { ParticipantEntryService } from 'app/entities/participant-entry/participant-entry.service';
import { ParticipantEntry } from 'app/shared/model/participant-entry.model';

describe('Component Tests', () => {
  describe('ParticipantEntry Management Update Component', () => {
    let comp: ParticipantEntryUpdateComponent;
    let fixture: ComponentFixture<ParticipantEntryUpdateComponent>;
    let service: ParticipantEntryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [ParticipantEntryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ParticipantEntryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParticipantEntryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParticipantEntryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParticipantEntry(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ParticipantEntry();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

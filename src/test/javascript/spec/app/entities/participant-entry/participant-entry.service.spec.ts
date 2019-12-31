import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ParticipantEntryService } from 'app/entities/participant-entry/participant-entry.service';
import { IParticipantEntry, ParticipantEntry } from 'app/shared/model/participant-entry.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Sector } from 'app/shared/model/enumerations/sector.model';
import { Specialgeneral } from 'app/shared/model/enumerations/specialgeneral.model';
import { Specialdisastermanagement } from 'app/shared/model/enumerations/specialdisastermanagement.model';
import { Educationlevel } from 'app/shared/model/enumerations/educationlevel.model';
import { Trainer } from 'app/shared/model/enumerations/trainer.model';

describe('Service Tests', () => {
  describe('ParticipantEntry Service', () => {
    let injector: TestBed;
    let service: ParticipantEntryService;
    let httpMock: HttpTestingController;
    let elemDefault: IParticipantEntry;
    let expectedResult: IParticipantEntry | IParticipantEntry[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ParticipantEntryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ParticipantEntry(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Country.ANTARTICA,
        Gender.MALE,
        Title.MR,
        currentDate,
        'AAAAAAA',
        Sector.CIVIL_SERVICE,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        Specialgeneral.ADMINISTRATION,
        Specialdisastermanagement.COORDINATION,
        Educationlevel.PRIMARY,
        Trainer.YES,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ParticipantEntry', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .create(new ParticipantEntry())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ParticipantEntry', () => {
        const returnedFromService = Object.assign(
          {
            individualCode: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            country: 'BBBBBB',
            gender: 'BBBBBB',
            title: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            organization: 'BBBBBB',
            sector: 'BBBBBB',
            position: 'BBBBBB',
            contactAddress: 'BBBBBB',
            workPhone: 1,
            faxNumber: 1,
            homePhone: 1,
            email: 'BBBBBB',
            previousEmployment: 'BBBBBB',
            specialGeneral: 'BBBBBB',
            specialDisasterManagement: 'BBBBBB',
            educationLevel: 'BBBBBB',
            trainer: 'BBBBBB',
            comments: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ParticipantEntry', () => {
        const returnedFromService = Object.assign(
          {
            individualCode: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            country: 'BBBBBB',
            gender: 'BBBBBB',
            title: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            organization: 'BBBBBB',
            sector: 'BBBBBB',
            position: 'BBBBBB',
            contactAddress: 'BBBBBB',
            workPhone: 1,
            faxNumber: 1,
            homePhone: 1,
            email: 'BBBBBB',
            previousEmployment: 'BBBBBB',
            specialGeneral: 'BBBBBB',
            specialDisasterManagement: 'BBBBBB',
            educationLevel: 'BBBBBB',
            trainer: 'BBBBBB',
            comments: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ParticipantEntry', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

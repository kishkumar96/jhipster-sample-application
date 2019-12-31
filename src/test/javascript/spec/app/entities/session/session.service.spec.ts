import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SessionService } from 'app/entities/session/session.service';
import { ISession, Session } from 'app/shared/model/session.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { SessionType } from 'app/shared/model/enumerations/session-type.model';

describe('Service Tests', () => {
  describe('Session Service', () => {
    let injector: TestBed;
    let service: SessionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISession;
    let expectedResult: ISession | ISession[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SessionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Session(
        0,
        'AAAAAAA',
        'AAAAAAA',
        Country.ANTARTICA,
        currentDate,
        currentDate,
        0,
        SessionType.IN_COUNTRY_TECHNICAL_ASSISTANCE,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Session', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Session())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Session', () => {
        const returnedFromService = Object.assign(
          {
            sessionCode: 'BBBBBB',
            nameOfActivity: 'BBBBBB',
            location: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            attendeesNumber: 1,
            sessionType: 'BBBBBB',
            description: 'BBBBBB',
            comment: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
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

      it('should return a list of Session', () => {
        const returnedFromService = Object.assign(
          {
            sessionCode: 'BBBBBB',
            nameOfActivity: 'BBBBBB',
            location: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            attendeesNumber: 1,
            sessionType: 'BBBBBB',
            description: 'BBBBBB',
            comment: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate
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

      it('should delete a Session', () => {
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

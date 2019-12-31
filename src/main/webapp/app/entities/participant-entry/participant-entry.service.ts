import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IParticipantEntry } from 'app/shared/model/participant-entry.model';

type EntityResponseType = HttpResponse<IParticipantEntry>;
type EntityArrayResponseType = HttpResponse<IParticipantEntry[]>;

@Injectable({ providedIn: 'root' })
export class ParticipantEntryService {
  public resourceUrl = SERVER_API_URL + 'api/participant-entries';

  constructor(protected http: HttpClient) {}

  create(participantEntry: IParticipantEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participantEntry);
    return this.http
      .post<IParticipantEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(participantEntry: IParticipantEntry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(participantEntry);
    return this.http
      .put<IParticipantEntry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParticipantEntry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParticipantEntry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(participantEntry: IParticipantEntry): IParticipantEntry {
    const copy: IParticipantEntry = Object.assign({}, participantEntry, {
      dateOfBirth:
        participantEntry.dateOfBirth && participantEntry.dateOfBirth.isValid()
          ? participantEntry.dateOfBirth.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth ? moment(res.body.dateOfBirth) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((participantEntry: IParticipantEntry) => {
        participantEntry.dateOfBirth = participantEntry.dateOfBirth ? moment(participantEntry.dateOfBirth) : undefined;
      });
    }
    return res;
  }
}

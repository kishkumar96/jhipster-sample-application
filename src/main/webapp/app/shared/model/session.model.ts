import { Moment } from 'moment';
import { ICourse } from 'app/shared/model/course.model';
import { IParticipantEntry } from 'app/shared/model/participant-entry.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { SessionType } from 'app/shared/model/enumerations/session-type.model';

export interface ISession {
  id?: number;
  sessionCode?: string;
  nameOfActivity?: string;
  location?: Country;
  startDate?: Moment;
  endDate?: Moment;
  attendeesNumber?: number;
  sessionType?: SessionType;
  description?: string;
  comment?: string;
  courseCode?: ICourse;
  individualCodes?: IParticipantEntry[];
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public sessionCode?: string,
    public nameOfActivity?: string,
    public location?: Country,
    public startDate?: Moment,
    public endDate?: Moment,
    public attendeesNumber?: number,
    public sessionType?: SessionType,
    public description?: string,
    public comment?: string,
    public courseCode?: ICourse,
    public individualCodes?: IParticipantEntry[]
  ) {}
}

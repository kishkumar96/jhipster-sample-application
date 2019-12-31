import { Moment } from 'moment';
import { ISession } from 'app/shared/model/session.model';
import { Country } from 'app/shared/model/enumerations/country.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { Title } from 'app/shared/model/enumerations/title.model';
import { Sector } from 'app/shared/model/enumerations/sector.model';
import { Specialgeneral } from 'app/shared/model/enumerations/specialgeneral.model';
import { Specialdisastermanagement } from 'app/shared/model/enumerations/specialdisastermanagement.model';
import { Educationlevel } from 'app/shared/model/enumerations/educationlevel.model';
import { Trainer } from 'app/shared/model/enumerations/trainer.model';

export interface IParticipantEntry {
  id?: number;
  individualCode?: string;
  firstName?: string;
  lastName?: string;
  country?: Country;
  gender?: Gender;
  title?: Title;
  dateOfBirth?: Moment;
  organization?: string;
  sector?: Sector;
  position?: string;
  contactAddress?: string;
  workPhone?: number;
  faxNumber?: number;
  homePhone?: number;
  email?: string;
  previousEmployment?: string;
  specialGeneral?: Specialgeneral;
  specialDisasterManagement?: Specialdisastermanagement;
  educationLevel?: Educationlevel;
  trainer?: Trainer;
  comments?: string;
  sessionCodes?: ISession[];
}

export class ParticipantEntry implements IParticipantEntry {
  constructor(
    public id?: number,
    public individualCode?: string,
    public firstName?: string,
    public lastName?: string,
    public country?: Country,
    public gender?: Gender,
    public title?: Title,
    public dateOfBirth?: Moment,
    public organization?: string,
    public sector?: Sector,
    public position?: string,
    public contactAddress?: string,
    public workPhone?: number,
    public faxNumber?: number,
    public homePhone?: number,
    public email?: string,
    public previousEmployment?: string,
    public specialGeneral?: Specialgeneral,
    public specialDisasterManagement?: Specialdisastermanagement,
    public educationLevel?: Educationlevel,
    public trainer?: Trainer,
    public comments?: string,
    public sessionCodes?: ISession[]
  ) {}
}

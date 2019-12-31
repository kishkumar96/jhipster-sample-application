import { ISession } from 'app/shared/model/session.model';
import { IProject } from 'app/shared/model/project.model';
import { Method } from 'app/shared/model/enumerations/method.model';
import { Period } from 'app/shared/model/enumerations/period.model';

export interface ICourse {
  id?: number;
  courseCode?: string;
  courseTitle?: string;
  method?: Method;
  duration?: number;
  period?: Period;
  description?: string;
  comments?: string;
  sessions?: ISession[];
  projectCode?: IProject;
}

export class Course implements ICourse {
  constructor(
    public id?: number,
    public courseCode?: string,
    public courseTitle?: string,
    public method?: Method,
    public duration?: number,
    public period?: Period,
    public description?: string,
    public comments?: string,
    public sessions?: ISession[],
    public projectCode?: IProject
  ) {}
}

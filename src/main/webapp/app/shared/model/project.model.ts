import { Moment } from 'moment';
import { ICourse } from 'app/shared/model/course.model';

export interface IProject {
  id?: number;
  projectCode?: string;
  projectName?: string;
  objective?: string;
  workPlan?: string;
  kra?: string;
  output?: string;
  evaluation?: string;
  budget?: number;
  headOffProject?: string;
  startDate?: Moment;
  endDate?: Moment;
  targetAudience?: string;
  overviewAboutProject?: string;
  courses?: ICourse[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public projectCode?: string,
    public projectName?: string,
    public objective?: string,
    public workPlan?: string,
    public kra?: string,
    public output?: string,
    public evaluation?: string,
    public budget?: number,
    public headOffProject?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public targetAudience?: string,
    public overviewAboutProject?: string,
    public courses?: ICourse[]
  ) {}
}

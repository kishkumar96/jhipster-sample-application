import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.JhipsterSampleApplicationSessionModule)
      },
      {
        path: 'project',
        loadChildren: () => import('./project/project.module').then(m => m.JhipsterSampleApplicationProjectModule)
      },
      {
        path: 'participant-entry',
        loadChildren: () =>
          import('./participant-entry/participant-entry.module').then(m => m.JhipsterSampleApplicationParticipantEntryModule)
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.JhipsterSampleApplicationCourseModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterSampleApplicationEntityModule {}

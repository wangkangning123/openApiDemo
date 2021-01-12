import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'people',
        loadChildren: () => import('./people/people.module').then(m => m.OpenApiDemoPeopleModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class OpenApiDemoEntityModule {}

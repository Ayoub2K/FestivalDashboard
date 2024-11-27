import { Routes } from '@angular/router';

import { PerformancesComponent } from './performances/performances.component';
import { ReviewsComponent } from './reviews/reviews.component';
import { CreateReviewComponent } from './create-review/create-review.component';
import { DjsComponent } from './djs/djs.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './services/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'performance', component: PerformancesComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'review', component: ReviewsComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'create-review', component: CreateReviewComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'dj', component: DjsComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] }, // Protected route
  { path: 'login', component: LoginComponent },
];

import { map } from 'rxjs/operators';
import {forkJoin, Observable} from 'rxjs';
import {ApiService} from '../services/api.service';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {AsyncPipe, DatePipe, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-reviews',
  standalone: true,
  imports: [
    AsyncPipe,
    NgForOf,
    NgIf,
    DatePipe,
    FormsModule,
    RouterLink,
  ],
  templateUrl: './reviews.component.html',
  styleUrl: './reviews.component.css'
})
export class ReviewsComponent implements OnInit{
  Reviews$: Observable<any[]> | undefined;

  constructor(private apiService: ApiService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadReviewsWithPerformances();
  }

  protected loadReviewsWithPerformances(): void {
    // Using forkJoin to get both performances and reviews at the same time
    this.Reviews$ = forkJoin({
      performances: this.apiService.getAllPerformances(),
      reviews: this.apiService.getAllReviews()
    }).pipe(
      map(({ performances, reviews }) => {
        return reviews.map(review => {
          // Find the matching performance for each review
          const matchingPerformance = performances.find(performance => performance.id === review.performanceId);
          return {
            ...review,
            performanceName: matchingPerformance ? matchingPerformance.name : 'Unknown Performance'
          };
        }).reverse();  // Reverse the reviews array to flip the order
      })
    );
  }
}

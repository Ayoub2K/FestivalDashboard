import { Component, TemplateRef, ViewChild } from '@angular/core';
import { AsyncPipe, DatePipe, NgForOf, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { ApiService } from '../services/api.service';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router'; // Import the Router service

@Component({
  selector: 'app-create-review',
  standalone: true,
  imports: [AsyncPipe, DatePipe, FormsModule, NgForOf, NgIf],
  templateUrl: './create-review.component.html',
  styleUrl: './create-review.component.css',
})
export class CreateReviewComponent {
  Performances$: Observable<any[]> | undefined;
  hidePerformancesWithoutSongs: boolean = false;
  selectedDJ: string = '';
  selectedDate: string = '';

  reviewName: string = '';
  reviewText: string = '';
  currentPerformanceId: number | null = null;

  @ViewChild('reviewDialog') reviewDialog!: TemplateRef<any>;

  constructor(
    private apiService: ApiService,
    private dialog: MatDialog,
    private router: Router // Inject the Router service
  ) {}

  ngOnInit(): void {
    this.loadPerformances();
  }

  loadPerformances(): void {
    this.Performances$ = this.apiService.getAllPerformances().pipe(
      map((performances) => {
        return this.filterPerformances(performances);
      })
    );
  }

  filterPerformances(performances: any[]): any[] {
    let filteredPerformances = performances;

    if (this.hidePerformancesWithoutSongs) {
      filteredPerformances = filteredPerformances.filter(
        (performance) =>
          performance.songs && performance.songs.length > 0
      );
    }

    if (this.selectedDJ) {
      filteredPerformances = filteredPerformances.filter(
        (performance) => performance.dj.name === this.selectedDJ
      );
    }

    if (this.selectedDate) {
      filteredPerformances = filteredPerformances.filter((performance) => {
        const performanceDate = new Date(performance.dayOfPerformance)
          .toISOString()
          .split('T')[0];
        return performanceDate === this.selectedDate;
      });
    }

    return filteredPerformances;
  }

  openReviewModal(performanceId: number): void {
    this.currentPerformanceId = performanceId;
    this.dialog.open(this.reviewDialog);
  }

  closeModal(): void {
    this.dialog.closeAll();
  }

  submitReview(): void {
    if (!this.currentPerformanceId || !this.reviewName || !this.reviewText) {
      alert('Please fill out all fields.');
      return;
    }

    const reviewData = {
      name: this.reviewName,
      review: this.reviewText,
      performanceId: this.currentPerformanceId,
    };

    this.apiService.createReview(reviewData).subscribe({
      next: (response) => {
        // Redirect to /review page after successful submission
        this.closeModal();
        this.router.navigate(['/review']);
      },
      error: (err) => {
        alert('Failed to submit review. Please try again.');
      },
    });
  }
}

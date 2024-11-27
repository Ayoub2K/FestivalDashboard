import {Component, OnInit} from '@angular/core';
import {PerformanceListComponent} from "../performance-list/performance-list.component";
import {AsyncPipe, DatePipe, NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {Observable} from 'rxjs';
import {ApiService} from '../services/api.service';
import {MatDialog} from '@angular/material/dialog';
import {map} from 'rxjs/operators';
import {PerformanceUpdateModalComponent} from '../performance-update-modal/performance-update-modal.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    PerformanceListComponent,
    AsyncPipe,
    DatePipe,
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  Performances$: Observable<any[]> | undefined;
  hidePerformancesWithoutSongs: boolean = false;
  selectedDJ: string = ''; // New property for DJ selection
  selectedDate: string = ''; // New property for date selection

  constructor(private apiService: ApiService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadPerformances();
  }

  protected loadPerformances(): void {
    this.Performances$ = this.apiService.getAllPerformances().pipe(
      map((performances) => {
        return this.filterPerformances(performances);
      })
    );
  }

  private filterPerformances(performances: any[]): any[] {
    let filteredPerformances = performances;

    // Filter by songs
    if (this.hidePerformancesWithoutSongs) {
      filteredPerformances = filteredPerformances.filter(performance =>
        performance.songs && performance.songs.length > 0
      );
    }

    // Filter by DJ
    if (this.selectedDJ) {
      filteredPerformances = filteredPerformances.filter(performance =>
        performance.dj.name === this.selectedDJ
      );
    }

    // Filter by date
    if (this.selectedDate) {
      filteredPerformances = filteredPerformances.filter(performance => {
        const performanceDate = new Date(performance.dayOfPerformance)
          .toISOString()
          .split('T')[0];
        return performanceDate === this.selectedDate; // Compare with selected date
      });
    }

    // Limit to a maximum of 3 performances
    return filteredPerformances.slice(0, 3);
  }

  // Method to open the update modal
  openUpdateModal(performance: any): void {
    const dialogRef = this.dialog.open(PerformanceUpdateModalComponent, {
      width: '400px',
      data: { performance }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('The Performance was updated');
        this.loadPerformances(); // Reload Performance after update
      }
    });
  }

  onDeletePerformance(id: number): void {
    this.apiService.deletePerformance(id).subscribe({
      next: () => {
        console.log('Performance deleted successfully');
        this.loadPerformances();
      },
      error: (err) => {
        console.error("Error deleting Performance:", err);
      }
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import {AsyncPipe, DatePipe, NgForOf, NgIf, NgOptimizedImage} from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { DjUpdateModalComponent } from '../dj-update-modal/dj-update-modal.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dj-list',
  standalone: true,
  imports: [
    AsyncPipe,
    NgForOf,
    NgIf,
    DatePipe,
    FormsModule,
    NgOptimizedImage,
  ],
  templateUrl: './dj-list.component.html',
  styleUrls: ['./dj-list.component.css']
})
export class DjListComponent implements OnInit {
  djsWithPerformances$: Observable<any[]> | undefined;
  selectedGenre: string = ''; // Property to store the selected genre
  hideDjsWithoutPerformances: boolean = false; // New property for the checkbox

  constructor(private apiService: ApiService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadDjs();
  }

  loadDjs(): void {
    this.djsWithPerformances$ = forkJoin({
      djs: this.apiService.getAllDjs(),
      performances: this.apiService.getAllPerformances(),
    }).pipe(
      map(({ djs, performances }) => {
        // Combine DJs with their performances
        const combinedDJs = djs.map(dj => ({
          ...dj,
          performances: performances.filter(performance => performance.dj.id === dj.id),
        }));

        // Return filtered DJs based on the selected genre and the checkbox option
        return this.filterDJs(combinedDJs);
      })
    );
  }

  // Method to filter DJs based on selected genre and whether to hide those without performances
  private filterDJs(djs: any[]): any[] {
    let filteredDJs = djs;

    // Filter by genre
    if (this.selectedGenre) {
      filteredDJs = filteredDJs.filter(dj => dj.genre === this.selectedGenre);
    }

    // Filter out DJs without performances if the checkbox is checked
    if (this.hideDjsWithoutPerformances) {
      filteredDJs = filteredDJs.filter(dj => dj.performances && dj.performances.length > 0);
    }

    return filteredDJs;
  }

  // Method to open the update modal
  openUpdateModal(dj: any): void {
    const dialogRef = this.dialog.open(DjUpdateModalComponent, {
      width: '400px',
      data: { dj }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        console.log('The DJ was updated');
        this.loadDjs(); // Reload DJs after update
      }
    });
  }

  onDeleteDj(id: number): void {
    this.apiService.deleteDj(id).subscribe({
      next: () => {
        console.log('DJ deleted successfully');
        this.loadDjs(); // Reload DJs after deletion
      },
      error: (err) => {
        console.error("Error deleting DJ:", err);
      }
    });
  }
}

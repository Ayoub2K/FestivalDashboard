import {Component, ViewChild} from '@angular/core';
import {PerformanceListComponent} from '../performance-list/performance-list.component';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ApiService} from '../services/api.service';

@Component({
  selector: 'app-performances',
  standalone: true,
  imports: [
    PerformanceListComponent,
    FormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './performances.component.html',
  styleUrl: './performances.component.css'
})
export class PerformancesComponent {
  @ViewChild(PerformanceListComponent) PerformanceListComponent!: PerformanceListComponent;

  performanceData = {
    name: '',
    dayOfPerformance: '2024-09-20',
    startTime: '2024-09-20T12:00:14.581858',
    endTime: '2024-09-20T13:00:14.581858',
    externalDJId: 0,
    songs: ['Random song']
  };

  showForm = false; // This property tracks the visibility of the form

  constructor(private performanceService: ApiService) {}

  createPerformance() {
    console.log(this.performanceData)
    this.performanceService.createPerformance(this.performanceData).subscribe(
      (response) => {
        console.log('Performance created successfully', response);
        this.PerformanceListComponent.loadPerformances();
        this.toggleForm()
      },
      (error) => {
        console.error('Error creating Performance', error);
        // Handle error, show an error message to the user
      }
    );
  }

  toggleForm() {
    this.showForm = !this.showForm; // Toggles the form visibility
  }

  // Method to add a new song
  addSong() {
    this.performanceData.songs.push(''); // Add an empty string for a new song
  }

  // Method to remove a song by index
  removeSong(index: number) {
    if (this.performanceData.songs.length > 1) {
      this.performanceData.songs.splice(index, 1); // Remove the song at the specified index
    } else {
      alert('You must have at least one song.'); // Ensure at least one song remains
    }
  }


}

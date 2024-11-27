import { Component, ViewChild } from '@angular/core';
import { DjListComponent } from "../dj-list/dj-list.component"; // Make sure this path is correct
import { ApiService } from "../services/api.service";
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-djs',
  standalone: true,
  imports: [
    DjListComponent,
    FormsModule,
    CommonModule,
  ],
  templateUrl: './djs.component.html',
  styleUrls: ['./djs.component.css']
})
export class DjsComponent {
  @ViewChild(DjListComponent) djListComponent!: DjListComponent; // Reference to the DjListComponent

  djData = {
    name: '',
    genre: '',
    age: 0,
    performances: []
  };

  showForm = false;

  constructor(private djService: ApiService) {}

  createDj() {
    this.djService.createDj(this.djData).subscribe(
      (response) => {
        console.log('DJ created successfully', response);
        this.resetForm(); // Call reset form
        this.djListComponent.loadDjs(); // Call loadDjs() right after resetting the form
      },
      (error) => {
        console.error('Error creating DJ', error);
        // Handle error, show an error message to the user
      }
    );
  }

  toggleForm() {
    this.showForm = !this.showForm; // Toggles the form visibility
  }

  resetForm() {
    this.djData = {
      name: '',
      genre: '',
      age: 0,
      performances: []
    };
    this.showForm = false; // Hide the form after reset
  }
}

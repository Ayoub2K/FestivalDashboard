import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from '../services/api.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-dj-update-modal',
  templateUrl: './dj-update-modal.component.html',
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrls: ['./dj-update-modal.component.css']
})
export class DjUpdateModalComponent {
  djData: { id: number, name: string, genre: string, age: number, performances: any[] };

  constructor(
    private apiService: ApiService,
    public dialogRef: MatDialogRef<DjUpdateModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    // Set the initial values for djData using the passed-in data
    this.djData = { ...data.dj };
  }

  onSubmit() {
    this.apiService.updateDj(this.djData).subscribe(
      (response) => {
        console.log('DJ updated successfully', response);
        this.dialogRef.close(true); // Close the dialog and pass a success flag
      },
      (error) => {
        console.error('Error updating DJ', error);
      }
    );
  }
}

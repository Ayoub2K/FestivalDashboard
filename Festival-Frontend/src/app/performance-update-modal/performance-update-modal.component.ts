import { Component, Inject, AfterViewInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ApiService } from '../services/api.service';
import { FormsModule } from '@angular/forms';
import { formatISO } from 'date-fns';
import 'flatpickr/dist/flatpickr.min.css';

@Component({
  selector: 'app-performance-update-modal',
  templateUrl: './performance-update-modal.component.html',
  standalone: true,
  imports: [FormsModule],
  styleUrls: ['./performance-update-modal.component.css']
})
export class PerformanceUpdateModalComponent implements AfterViewInit {
  performanceData: {
    id: number;
    name: string;
    dayOfPerformance: string;
    startTime: string;
    endTime: string;
    songs: any[];
  };

  constructor(
    private apiService: ApiService,
    public dialogRef: MatDialogRef<PerformanceUpdateModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.performanceData = { ...data.performance };
  }

  async ngAfterViewInit() {
    const flatpickr = (await import('flatpickr')).default;

    flatpickr('#dayOfPerformance', {
      dateFormat: 'Y-m-d',
      onChange: (selectedDates, dateStr) => {
        this.performanceData.dayOfPerformance = dateStr;
      }
    });

    flatpickr('#startTime', {
      enableTime: true,
      noCalendar: true,
      dateFormat: 'H:i',
      time_24hr: true,
      onChange: (selectedDates) => {
        const selectedTime = selectedDates[0];
        if (!this.performanceData.dayOfPerformance) {
          console.error("Day of performance must be set before selecting time.");
          return;
        }

        const dateParts = this.performanceData.dayOfPerformance.split('-');
        const selectedDateTime = new Date(
          parseInt(dateParts[0]),
          parseInt(dateParts[1]) - 1,
          parseInt(dateParts[2]),
          selectedTime.getHours(),
          selectedTime.getMinutes(),
          0,
          0
        );

        this.performanceData.startTime = formatISO(selectedDateTime, { representation: 'complete' });
        console.log("Formatted startTime:", this.performanceData.startTime);
      }
    });

    flatpickr('#endTime', {
      enableTime: true,
      noCalendar: true,
      dateFormat: 'H:i',
      time_24hr: true,
      onChange: (selectedDates) => {
        const selectedTime = selectedDates[0];
        if (!this.performanceData.dayOfPerformance) {
          console.error("Day of performance must be set before selecting time.");
          return;
        }

        const dateParts = this.performanceData.dayOfPerformance.split('-');
        const selectedDateTime = new Date(
          parseInt(dateParts[0]),
          parseInt(dateParts[1]) - 1,
          parseInt(dateParts[2]),
          selectedTime.getHours(),
          selectedTime.getMinutes(),
          0,
          0
        );

        this.performanceData.endTime = formatISO(selectedDateTime, { representation: 'complete' });
        console.log("Formatted endTime:", this.performanceData.endTime);
      }
    });
  }

  onSubmit() {
    // Convert startTime and endTime to LocalDateTime compatible format (without timezone)
    const formatLocalDateTime = (isoString: string) => {
      const date = new Date(isoString);
      return date.toISOString().slice(0, 19); // Format: 'YYYY-MM-DDTHH:mm:ss'
    };

    // Format performanceData's startTime and endTime
    this.performanceData.startTime = formatLocalDateTime(this.performanceData.startTime);
    this.performanceData.endTime = formatLocalDateTime(this.performanceData.endTime);

    console.log('Submitting performance data:', this.performanceData); // Log the full data object for debugging

    this.apiService.updatePerformance(this.performanceData).subscribe(
      (response) => {
        console.log('Performance updated successfully', response);
        this.dialogRef.close(true); // Close the dialog and pass a success flag
      },
      (error) => {
        console.error('Error updating performance', error);
      }
    );
  }
}

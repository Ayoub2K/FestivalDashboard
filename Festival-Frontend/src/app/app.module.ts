import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient, withFetch} from '@angular/common/http'; // Import provideHttpClient

import { AppComponent } from './app.component';
import { DjListComponent } from './dj-list/dj-list.component';
import { AppRoutingModule } from './app-routing.module';
import {MatDialogModule} from '@angular/material/dialog';
import {MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker'; // Adjust the path if necessary

@NgModule({
  declarations: [
    // Declare your component here
  ],
  imports: [
    BrowserModule,
    AppComponent,
    DjListComponent,
    AppRoutingModule,
    MatDialogModule,
  ],
  providers: [
    provideHttpClient(withFetch())
  ],
  bootstrap: []
})
export class AppModule { }

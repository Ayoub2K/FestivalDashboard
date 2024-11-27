import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {DjListComponent} from './dj-list/dj-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, DjListComponent, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Festival-Frontend';
}

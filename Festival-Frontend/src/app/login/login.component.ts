import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service'; // Import the AuthService

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  standalone: true,
  imports: [FormsModule]
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router, private authService: AuthService) {}

  login() {
    if (this.authService.login(this.username, this.password)) {
      // Redirect to /home on successful login
      this.router.navigate(['/home']);
    } else {
      alert('Invalid credentials');
    }
  }
}

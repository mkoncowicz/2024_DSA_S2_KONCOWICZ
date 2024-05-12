import { Component } from '@angular/core';
import {UserService} from "../services/user.service";
import {RouterLink, RouterOutlet} from "@angular/router";
import {FormsModule, NgForm} from "@angular/forms";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-log-in',
  standalone: true,
  imports: [
    RouterLink,
    RouterOutlet,
    FormsModule
  ],
  templateUrl: './log-in.component.html',
  styleUrl: './log-in.component.css'
})
export class LogInComponent {
  email: string = '';
  password: string = '';

  constructor(private userService: UserService, private authService: AuthService) {
  }

  logIn(logInForm: NgForm) {
    this.authService.logIn(logInForm.value.email, logInForm.value.password);
  }

  handleSubmit(event: Event): void {
    event.preventDefault();
    console.log('Submitting:', { email: this.email, password: this.password });
    // Implement submit logic here
  }

  handleGoogleLogin(): void {
    console.log('Google Login initiated');
    // Implement the Google login logic here
  }
}

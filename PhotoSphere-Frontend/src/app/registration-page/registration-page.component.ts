import { Component } from '@angular/core';
import {User} from "../models/user.model";
import {UserService} from "../services/user.service";
import {RouterLink, RouterOutlet, Router} from "@angular/router";
import {CommonModule} from "@angular/common";
import {FormsModule, NgForm} from "@angular/forms";
import {RegistrationRequest} from "../open-api-services/models/registration-request";
import {AuthenticationService} from "../open-api-services/services/authentication.service";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    RouterLink,
    RouterOutlet,
    CommonModule,
    FormsModule
  ],
  templateUrl: './registration-page.component.html',
  styleUrl: './registration-page.component.css'
})
export class RegistrationPageComponent {
  email: string = '';
  password: string = '';
  //user: User = this.userService.blankUser;
  errorMsg: Array<string> = [];
  gender: string[] = ["Male", "Female", "Other"];
  registerRequest: RegistrationRequest = {
    dayOfBirth: '',
    email: '',
    firstName: '',
    gender: '',
    lastName: '',
    password: '',
    nickname: ''
  };
  constructor(private userService: UserService, private router: Router, private authService: AuthenticationService) {
  }



  signUp(signUpForm: NgForm) {
    this.errorMsg = [];
    this.authService.register({
      body: this.registerRequest
    })
      .subscribe({
        next: () => {
          this.router.navigate(['/logging']);
        },
        error: (err) => {
          this.errorMsg = err.error.validationErrors;
        }
      });
}
}

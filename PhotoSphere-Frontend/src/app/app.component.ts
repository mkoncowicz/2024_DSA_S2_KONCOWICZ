import { Component } from '@angular/core';
import {CommonModule} from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {RegistrationPageComponent} from "./registration-page/registration-page.component";
import {LogInComponent} from "./log-in/log-in.component";
import {PopularPhotoPageComponent} from "./popular-photo-page/popular-photo-page.component";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RegistrationPageComponent, LogInComponent, PopularPhotoPageComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'PhotoSphere-Frontend';
}

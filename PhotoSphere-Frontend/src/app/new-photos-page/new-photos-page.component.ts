import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";

@Component({
  selector: 'app-new-photos-page',
  standalone: true,
  imports: [
    NavBarComponent
  ],
  templateUrl: './new-photos-page.component.html',
  styleUrl: './new-photos-page.component.css'
})
export class NewPhotosPageComponent {

}

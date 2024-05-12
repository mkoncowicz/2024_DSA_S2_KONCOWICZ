import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";

@Component({
  selector: 'app-add-new-photo',
  standalone: true,
  imports: [
    NavBarComponent
  ],
  templateUrl: './add-new-photo.component.html',
  styleUrl: './add-new-photo.component.css'
})
export class AddNewPhotoComponent {

}

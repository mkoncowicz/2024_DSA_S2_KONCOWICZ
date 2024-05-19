import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {UserService} from "../services/user.service";
import {CommonModule, NgIf} from "@angular/common";

@Component({
  selector: 'app-add-new-photo',
  standalone: true,
  imports: [
    NavBarComponent,
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './add-new-photo.component.html',
  styleUrl: './add-new-photo.component.css'
})
export class AddNewPhotoComponent {

  selectedPhoto: string | ArrayBuffer | null = null;

  constructor(private userService: UserService) {
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();

      reader.onload = () => {
        this.selectedPhoto = reader.result;
      };

      reader.readAsDataURL(file);
    }
  }

  triggerFileInput(): void {
    const fileInput = document.getElementById('upload-photo') as HTMLInputElement;
    fileInput.click();
  }

  onCreatePost(addPhotoForm: NgForm): void {

  }

  checkOnly(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const checkboxes = document.getElementsByName('visibility') as NodeListOf<HTMLInputElement>;
    checkboxes.forEach((item) => {
      if (item !== checkbox) item.checked = false;
    });
  }
}

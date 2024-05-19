import { Component } from '@angular/core';
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {NgIf} from "@angular/common";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-edit-photo',
  standalone: true,
  imports: [
    FormsModule,
    NavBarComponent,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './edit-photo.component.html',
  styleUrl: './edit-photo.component.css'
})
export class EditPhotoComponent {
  editedPhoto: string | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.editedPhoto = params['photo'] || null;
    });
  }

  onEditPost(editPhotoForm: NgForm): void {

  }

  checkOnly(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const checkboxes = document.getElementsByName('visibility') as NodeListOf<HTMLInputElement>;
    checkboxes.forEach((item) => {
      if (item !== checkbox) item.checked = false;
    });
  }

  onItemClick(item: string): void {
    console.log(`${item} clicked`);
    if (item == 'Delete') {
    }
  }
}

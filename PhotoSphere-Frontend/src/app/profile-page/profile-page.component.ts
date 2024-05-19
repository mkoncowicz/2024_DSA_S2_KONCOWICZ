import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [
    NavBarComponent
  ],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {

  constructor(private router: Router, protected authService: AuthService) {}

  filterIconSrc: string = 'assets/icons/filter_category.png';
  imageSrc: string = 'assets/icons/google-logo.png';
  filterName: string = "Lake";


  onNavButtonClick(item: string): void {
    console.log(`${item} clicked`);
    if(item == 'Public') {
      this.router.navigate(['/PublicPhotos']);
    }
    if(item == 'Private') {
      this.router.navigate(['/PrivatePhotos']);
    }
    if(item == 'Saved') {
      this.router.navigate(['/SavedPhotos']);
    }
    if(item == 'Edit') {
      this.router.navigate(['/EditProfile']);
    }
  }
  onFilterClick(): void {
    console.log('Filter button clicked');
  }

  onImageArticleClick(imageSrc: string): void {
    console.log('Image article clicked');
    this.router.navigate(['/ZoomInPhoto'], { queryParams: { photo: imageSrc } });
  }

  onPlaceholderClick(): void {
    console.log('Placeholder article clicked');
  }
}

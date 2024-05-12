import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent {

  constructor(private router: Router) {}


  onNavItemClick(item: string): void {
    console.log(`${item} clicked`);
    if(item == 'Popular') {
      this.router.navigate(['/PopularPhotoPage'])
    }
    if(item == 'New'){
      this.router.navigate(['/NewPhotoPage'])
    }
    if(item == 'Create'){
      this.router.navigate(['/AddNewPhoto'])
    }
    if(item == 'Account'){
      this.router.navigate(['/ProfilePage'])
    }

  }
}

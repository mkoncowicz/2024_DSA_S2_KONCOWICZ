import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CommonModule} from "@angular/common";
import {AuthService} from "../services/auth.service";
import {FormsModule} from "@angular/forms";
import {Tag} from "../models/tag.model";
import {TagService} from "../services/tag.service";


@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css'
})
export class NavBarComponent implements OnInit {
  searchQuery: string = '';
  allTags: Tag[] = [];
  filteredTags: Tag[] = [];

  constructor(
    private router: Router,
    public authService: AuthService,
    private tagService: TagService
  ) {}

  ngOnInit() {
    this.tagService.getAllTags().subscribe(
      (tags: Tag[]) => {
        this.allTags = tags;
      },
      (error) => {
        console.error('Failed to load tags: ', error);
      }
    );
  }

  onNavItemClick(item: string): void {
    console.log(`${item} clicked`);
    if(item == 'Help') {
      this.router.navigate(['/HelpPage']);
    }
    if(item == 'Popular') {
      this.router.navigate(['/PopularPhotoPage']);
    }
    if(item == 'New'){
      this.router.navigate(['/NewPhotosPage']);
    }
    if(item == 'Create'){
      this.router.navigate(['/AddNewPhoto']);
    }
    if(item == 'Account'){
      const userId = this.authService.loggedUser.id;
      this.router.navigate(['/ProfilePage', userId]);
    }
  }

  logOut(): void {
    this.authService.logOut();
  }

  onKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter' && this.searchQuery.trim() !== '') {
      this.router.navigate(['/SearchResults'], { queryParams: { tag: this.searchQuery.trim() } });
    }
  }

  onSearchQueryChange(): void {
    if (this.searchQuery.trim() === '') {
      this.filteredTags = [];
    } else {
      const query = this.searchQuery.toLowerCase();
      this.filteredTags = this.allTags.filter(tag => tag.name.toLowerCase().startsWith(query));
    }
  }

  selectTag(tag: Tag): void {
    this.searchQuery = tag.name;
    this.filteredTags = [];
    this.router.navigate(['/SearchResults'], { queryParams: { tag: this.searchQuery.trim() } });
  }
}

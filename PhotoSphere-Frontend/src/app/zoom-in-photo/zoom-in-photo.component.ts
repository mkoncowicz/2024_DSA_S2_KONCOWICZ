import { Component, OnInit } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {Router, ActivatedRoute} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-zoom-in-photo',
  standalone: true,
  imports: [
    NavBarComponent,
    NgIf
  ],
  templateUrl: './zoom-in-photo.component.html',
  styleUrl: './zoom-in-photo.component.css'
})
export class ZoomInPhotoComponent {
  likeIconSrc: string = "assets/icons/like.png";
  dislikeIconSrc: string = "assets/icons/dislike.png";
  binIconSrc: string = "assets/icons/bin.png";
  funnyIconSrc: string = "assets/icons/funny.png";
  sadIconSrc: string = "assets/icons/sad.png";
  shockedIconSrc: string = "assets/icons/shocked.png";

  selectedPhoto: string | null = null;

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.selectedPhoto = params['photo'] || null;
    });
    this.scrollToTop();
  }

  scrollToTop() {
    window.scrollTo(0, 0);
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
    if (item == 'Edit') {
      this.router.navigate(['/EditPhoto'], { queryParams: { photo: this.selectedPhoto}});
    }
    if (item == 'Save') {
    }
  }
}

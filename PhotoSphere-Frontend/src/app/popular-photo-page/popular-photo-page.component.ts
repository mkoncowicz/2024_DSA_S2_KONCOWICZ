import { Component } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";

@Component({
  selector: 'app-popular-photo-page',
  standalone: true,
  imports: [
    NavBarComponent
  ],
  templateUrl: './popular-photo-page.component.html',
  styleUrl: './popular-photo-page.component.css'
})
export class PopularPhotoPageComponent {
  // imgSrc: string = '';
  // featuredItems = [
  //   { image: 'path/to/image1.jpg' },
  //   { image: 'path/to/image2.jpg' },
  //   { image: 'path/to/image3.jpg' }
  // ];
  // moreItems = [
  //   { image: 'path/to/additional-image1.jpg' },
  //   { image: 'path/to/additional-image2.jpg' },
  //   { image: 'path/to/additional-image3.jpg' }
  // ];

}


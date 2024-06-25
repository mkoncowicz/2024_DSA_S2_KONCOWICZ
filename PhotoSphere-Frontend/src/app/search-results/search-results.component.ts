import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { PostService } from '../services/post.service';
import { Post } from '../models/post.model';
import { NavBarComponent } from "../nav-bar/nav-bar.component";
import { NgForOf } from "@angular/common";

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [
    NavBarComponent,
    NgForOf
  ],
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {
  tag: string = '';
  posts: Post[] = [];
  postImages: { postId: number, imageUrl: string }[] = [];

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.tag = params['tag'] || '';
      if (this.tag) {
        this.searchPostsByTagName();
      }
    });
  }

  searchPostsByTagName(): void {
    this.postService.searchPostsByTagName(this.tag).subscribe(
      (posts: Post[]) => {
        this.posts = posts;
        this.postImages = [];
        this.loadPostImages();
      },
      (error) => {
        console.error('Failed to load posts: ', error);
      }
    );
  }

  loadPostImages(): void {
    this.posts.forEach(post => {
      this.postService.downloadPostImage(post.id).subscribe(
        (imageBlob: Blob) => {
          const url = URL.createObjectURL(imageBlob);
          this.postImages.push({ postId: post.id, imageUrl: url });
        },
        (error) => {
          console.error(`Failed to load image for post ID ${post.id}:`, error);
        }
      );
    });
  }

  onPhotoClicked(postId: number, imageUrl: string): void {
    this.router.navigate(['/ZoomInPhoto'], { queryParams: { photo: imageUrl, postId } });
  }
}

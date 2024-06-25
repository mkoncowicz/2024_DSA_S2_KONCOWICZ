import { Component, OnInit } from '@angular/core';
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {NgForOf} from "@angular/common";
import {PostService} from "../services/post.service";
import {Router} from "@angular/router";
import {Post} from "../models/post.model";
import {CommentService} from "../services/comment.service";
import {ReactionService} from "../services/reaction.service";
import {forkJoin, map, Observable, switchMap} from "rxjs";

@Component({
  selector: 'app-popular-photo-page',
  standalone: true,
  imports: [
    NavBarComponent,
    NgForOf
  ],
  templateUrl: './popular-photo-page.component.html',
  styleUrl: './popular-photo-page.component.css'
})
export class PopularPhotoPageComponent implements OnInit {

  posts: Post[] = [];
  postImages: { postId: number, imageUrl: string, popularityScore: number }[] = [];

  constructor(
    private postService: PostService,
    private router: Router,
    private commentService: CommentService,
    private reactionService: ReactionService
  ) {}

  ngOnInit(): void {
    this.loadPopularPosts();
  }

  loadPopularPosts(): void {
    this.postService.getAllPosts().pipe(
      switchMap((posts: Post[]) => {
        this.posts = posts.filter(post => !post.private);
        return forkJoin(this.posts.map(post => this.calculatePopularityScore(post)));
      }),
      switchMap((popularityData) => {
        this.postImages = popularityData;
        const imageObservables = this.postImages.map(postImage =>
          this.postService.downloadPostImage(postImage.postId).pipe(
            map((imageBlob: Blob) => {
              const url = URL.createObjectURL(imageBlob);
              postImage.imageUrl = url;
              return postImage;
            })
          )
        );
        return forkJoin(imageObservables);
      })
    ).subscribe(
      (postImages) => {
        this.postImages = postImages;
        this.sortPostImagesByPopularity();
      },
      (error) => {
        console.error('Failed to load popular posts: ', error);
      }
    );
  }

  calculatePopularityScore(post: Post): Observable<{ postId: number, imageUrl: string, popularityScore: number }> {
    return forkJoin([
      this.reactionService.getPostReactions(post.id),
      this.commentService.getCommentsByPostId(post.id)
    ]).pipe(
      map(([reactions, comments]) => {
        const reactionCount = reactions.length;
        const commentCount = comments.length;
        const popularityScore = reactionCount + commentCount;
        return {
          postId: post.id,
          imageUrl: '',
          popularityScore
        };
      })
    );
  }

  sortPostImagesByPopularity(): void {
    this.postImages.sort((a, b) => b.popularityScore - a.popularityScore);
  }

  onPhotoClicked(postId: number, imageUrl: string): void {
    this.router.navigate(['/ZoomInPhoto'], { queryParams: { photo: imageUrl, postId } });
  }
}


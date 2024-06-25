import {Component, OnInit} from '@angular/core';
import {FormsModule, NgForm, ReactiveFormsModule} from "@angular/forms";
import {NavBarComponent} from "../nav-bar/nav-bar.component";
import {NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";
import {Post} from "../models/post.model";
import {PostService} from "../services/post.service";
import {AuthService} from "../services/auth.service";

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
export class EditPhotoComponent implements OnInit {
  editedPhoto: string | null = null;
  post: Partial<Post> = {
    caption: '',
    description: '',
    category: '',
    private: true,
  };

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private router: Router,
    private authService: AuthService) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const postId = params['postId'];
      if (postId) {
        this.loadPostData(postId);
      }
      this.editedPhoto = params['photo'] || null;
    });
  }

  loadPostData(postId: number): void {
    this.postService.getPostById(postId).subscribe(
      (post: Post) => {
        this.post = {
          id: post.id,
          caption: post.caption,
          description: post.description,
          category: post.category,
          private: post.private
        };
      },
      error => {
        console.error('Failed to load post data:', error);
      }
    );
  }
  onEditPost(editPhotoForm: NgForm): void {
    if (editPhotoForm.valid && this.post.id !== undefined) {
      const updatedFields = {
        caption: this.post.caption,
        description: this.post.description,
        category: this.post.category,
        isPrivate: this.post.private
      };

      this.postService.updatePostFields(this.post.id, updatedFields).subscribe(
        () => {
          console.log('Post updated successfully');
          this.router.navigate(['/ZoomInPhoto'], { queryParams: { photo: this.editedPhoto, postId: this.post.id } });
        },
        error => {
          console.error('Failed to update post:', error);
        }
      );
    }
  }

  onDeletePost(): void {
    if (this.post.id) {
      this.postService.deletePostById(this.post.id).subscribe(
        () => {
          console.log('Post deleted successfully');
          const userId = this.authService.loggedUser.id;
          this.router.navigate(['/ProfilePage', userId]);
        },
        error => {
          console.error('Failed to delete post:', error);
        }
      );
    }
  }
}

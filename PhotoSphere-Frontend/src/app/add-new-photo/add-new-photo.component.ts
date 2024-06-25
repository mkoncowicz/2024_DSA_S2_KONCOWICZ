import { Component, OnInit } from '@angular/core';
import { NavBarComponent } from "../nav-bar/nav-bar.component";
import { FormsModule, NgForm, ReactiveFormsModule } from "@angular/forms";
import { PostService } from "../services/post.service";
import {CommonModule, NgForOf, NgIf} from "@angular/common";
import { AuthService } from '../services/auth.service';
import { Post } from "../models/post.model";
import { Router } from "@angular/router";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { CategoryService } from '../services/category.service';  // Import CategoryService

@Component({
  selector: 'app-add-new-photo',
  standalone: true,
  imports: [
    NavBarComponent,
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './add-new-photo.component.html',
  styleUrls: ['./add-new-photo.component.css']
})
export class AddNewPhotoComponent implements OnInit {

  postImageSrc: SafeUrl | string = 'assets/icons/placeholder.png';
  selectedPhoto: string | ArrayBuffer | null = null;
  post: Post = {
    id: 0,
    userId: this.authService.loggedUser.id,
    caption: '',
    imageUrl: '',
    category: '',
    description: '',
    private: true,
    createdAt: new Date(),
  };
  isSubmitting: boolean = false;
  tagsInput: string = '';
  categories: string[] = [];  // Array to hold categories

  constructor(
    private postService: PostService,
    private authService: AuthService,
    private sanitizer: DomSanitizer,
    private router: Router,
    private categoryService: CategoryService  // Inject CategoryService
  ) { }

  ngOnInit(): void {
    this.loadCategories();  // Load categories on initialization
  }

  loadCategories(): void {
    this.categoryService.getCategories().subscribe({
      next: categories => {
        this.categories = categories;
        console.log('Categories loaded:', this.categories);  // Add a console log to debug
      },
      error: err => {
        console.error('Error fetching categories', err);
      }
    });
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
    if (fileInput) {
      fileInput.click();
    }
  }

  onCreatePost(addPhotoForm: NgForm): void {
    if (this.isSubmitting) return;  // Prevent multiple submissions
    this.isSubmitting = true;

    if (!addPhotoForm.valid || !this.selectedPhoto) {
      this.isSubmitting = false;
      return;
    }

    const formValues = addPhotoForm.value;

    this.post.caption = formValues.Title;
    this.post.description = formValues.description;
    this.post.category = formValues.category;
    this.post.private = formValues.visibility === 'true';
    this.post.createdAt = new Date();

    this.postService.createPost(this.post).subscribe({
      next: createdPost => {
        console.log('Post created successfully:', createdPost);
        this.post.id = createdPost.id;
        if (this.selectedPhoto) {
          this.uploadImageAndSavePost(createdPost.id);
        } else {
          var userId = this.authService.loggedUser.id;
          this.router.navigate(['/ProfilePage', userId]);
        }
      },
      error: err => {
        console.error('Error creating post', err);
        this.isSubmitting = false;
      }
    });
  }

  uploadImageAndSavePost(postId: number): void {
    const fileInput = document.getElementById('upload-photo') as HTMLInputElement;
    if (fileInput.files && fileInput.files[0]) {
      const formData = new FormData();
      formData.append('image', fileInput.files[0]);
      this.postService.uploadPostImage(postId, formData).subscribe({
        next: (response) => {
          this.post.imageUrl = response.imageUrl;
          console.log('Image uploaded successfully:', response.imageUrl);
          this.saveTags();
        },
        error: err => {
          console.error('Error uploading image', err);
          this.isSubmitting = false;
          const userId = this.authService.loggedUser.id;
          this.router.navigate(['/ProfilePage', userId]);
        }
      });
    } else {
      console.log('No file selected');
    }
  }

  saveTags(): void {
    const tags = this.tagsInput.split(',').map(tag => tag.trim()).filter(tag => tag);

    if (tags.length > 0) {
      this.postService.addTagsToPost(this.post.id, tags).subscribe({
        next: () => {
          console.log('Tags saved successfully');
          const userId = this.authService.loggedUser.id;
          this.router.navigate(['/ProfilePage', userId]);
        },
        error: err => {
          console.error('Error saving tags', err);
          this.isSubmitting = false;
        }
      });
    }
  }
}

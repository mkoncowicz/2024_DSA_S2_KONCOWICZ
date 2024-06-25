import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { NavBarComponent } from "../nav-bar/nav-bar.component";
import { AuthService } from "../services/auth.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {UserService} from "../services/user.service";
import {PostService} from "../services/post.service";
import {NgForOf, NgIf} from "@angular/common";
import {User} from "../models/user.model";
import {Post} from "../models/post.model";

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [
    NavBarComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent implements OnInit {

  filterIconSrc: string = 'assets/icons/filter_category.png';
  profileImageSrc: SafeUrl | string = 'assets/icons/placeholder.png';

  profileUsername: string| null = null;
  profileDescription: string| null = null;
  filterName: string = "";
  currentUserId: number | undefined;
  postImages: { postId: number, url: string }[] = [];
  categories: string[] = [];
  showCategoryList: boolean = false;
  isCurrentUserProfile: boolean = false;

  constructor(private route: ActivatedRoute,
              private router: Router,
              protected authService: AuthService,
              private userService: UserService,
              private sanitizer: DomSanitizer,
              private postService: PostService
  ){}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.currentUserId = +params['id'];
      this.isCurrentUserProfile = this.currentUserId === this.authService.loggedUser.id;
      this.loadUserProfileImage();
      this.loadUserPostImages();
      this.loadUserCategories();
      this.fetchProfileUsernameDescription(this.currentUserId);
    });
  }

  loadUserPostImages(isPrivate: boolean | null = null): void {
    if (this.currentUserId !== undefined) {
      const filterPrivatePosts = this.isCurrentUserProfile ? isPrivate : false;
      this.postService.getPostIdsByUserId(this.currentUserId).subscribe(
        (postIds: number[]) => {
          this.postImages = [];
          postIds.forEach(postId => this.loadPostImage(postId, filterPrivatePosts));
        },
        (error) => {
          console.error('Failed to load post IDs:', error);
        }
      );
    } else {
      console.error('User ID is undefined');
    }
  }

  loadPostImage(postId: number, isPrivate: boolean | null): void {
    if (postId !== undefined) {
      this.postService.getPostById(postId).subscribe(
        (post: Post) => {
          const postIsPrivate = (post as Post).private;
          if (isPrivate === null || postIsPrivate === isPrivate) {
            this.postService.downloadPostImage(postId).subscribe(
              (imageBlob: Blob) => {
                if (imageBlob && imageBlob.size > 0) {
                  try {
                    const url = URL.createObjectURL(imageBlob);
                    this.postImages.push({ postId, url });
                    this.postImages.sort((a, b) => b.postId - a.postId);
                  } catch (error) {
                    console.error('Failed to create object URL:', error);
                  }
                } else {
                  console.error(`Received empty blob for post ID ${postId}`);
                }
              },
              (error) => {
                console.error(`Failed to load image for post ID ${postId}:`, error);
              }
            );
          }
        },
        (error) => {
          console.error(`Failed to load post for post ID ${postId}:`, error);
        }
      );
    } else {
      console.error('Post ID is undefined');
    }
  }

  loadUserProfileImage(): void {
    if (this.currentUserId !== undefined) {
      this.userService.downloadUserImage(this.currentUserId).subscribe(
        (imageBlob: Blob) => {
          if (imageBlob && imageBlob.size > 0) {
            try {
              const url = URL.createObjectURL(imageBlob);
              this.profileImageSrc = this.sanitizer.bypassSecurityTrustUrl(url);
            } catch (error) {
              console.error('Failed to create object URL for profile image:', error);
            }
          } else {
            console.error('Received empty blob for profile image');
          }
        },
        (error) => {
          console.error('Failed to load user image:', error);
        }
      );
    } else {
      console.error('User ID is undefined');
    }
  }

  loadUserCategories(): void {
    if (this.currentUserId !== undefined) {
      this.postService.getCategoriesByUserId(this.currentUserId).subscribe(
        (categories: string[]) => {
          this.categories = categories;
        },
        (error) => {
          console.error('Failed to load categories: ', error);
        }
      );
    } else {
      console.error('User ID is undefined');
    }
  }

  fetchProfileUsernameDescription(userId: number) {
    this.userService.getUserById(userId).subscribe(
      (user: User) => {
        this.profileUsername = user.nickname;
        this.profileDescription = user.description;
      },
      error => {
        console.error('Failed to fetch creator username: ', error);
      }
    );
  }

  onNavButtonClick(item: string): void {
    console.log(`${item} clicked`);
    if(item == 'Public') {
      this.loadUserPostImages(false);
    }
    if(item == 'Private') {
      this.loadUserPostImages(true);
    }
    if(item == 'Liked') {
      this.loadLikedPosts();
    }
    if(item == 'Edit') {
      this.router.navigate(['/EditProfile']);
    }
  }

  loadLikedPosts(): void {
    if (this.currentUserId !== undefined) {
      this.postService.getPostReactionsByUserId(this.currentUserId).subscribe(
        (reactions: any[]) => {
          const likedPostIds = reactions
            .filter(reaction => reaction.reaction === 'like')
            .map(reaction => reaction.postId);

          this.postImages = [];
          likedPostIds.forEach(postId => this.loadPostImage(postId, null));
        },
        (error) => {
          console.error('Failed to load liked posts:', error);
        }
      );
    } else {
      console.error('User ID is undefined');
    }
  }

  onFilterClick(): void {
    console.log('Filter button clicked');
    this.showCategoryList = !this.showCategoryList;
  }

  onCategorySelected(category: string): void {
    console.log(`Category selected: ${category}`);
    this.filterName = category === 'All' ? '' : category;
    this.showCategoryList = false;
    if (category === 'All') {
      this.loadUserPostImages();
    } else {
      this.loadFilteredPosts(category);
    }
  }

  loadFilteredPosts(category: string): void {
    if (category) {
      this.postService.getPostsByCategory(category).subscribe(
        (posts) => {
          const userPosts = posts.filter(post => post.userId === this.currentUserId);
          this.postImages = [];
          userPosts.forEach(post => this.loadPostImage(post.id, null));
        },
        (error) => {
          console.error('Failed to load posts by category: ', error);
        }
      );
    } else {
      this.loadUserPostImages();
    }
  }

  onImageArticleClick(postId: number, imageSrc: string): void {
    console.log('Image article clicked');
    this.router.navigate(['/ZoomInPhoto'], { queryParams: { photo: imageSrc, postId } });
  }
}

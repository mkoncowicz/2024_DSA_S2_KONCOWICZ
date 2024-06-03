import {Component, OnInit} from '@angular/core';
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-example-s3-api',
  standalone: true,
  imports: [],
  templateUrl: './example-s3-api.component.html',
  styleUrl: './example-s3-api.component.css'
})
export class ExampleS3ApiComponent implements OnInit{
  imageUrl: SafeUrl | string = 'assets/icons/placeholder.png'; // Placeholder until image loads

  constructor(
    private userService: UserService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.loadUserImage(1); // Replace 1 with the actual user ID
  }

  loadUserImage(userId: number): void {
    this.userService.downloadUserImage(userId).subscribe(
      (imageBlob: Blob) => {
        const url = URL.createObjectURL(imageBlob);
        this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(url);
      },
      (error) => {
        console.error('Failed to load user image:', error);
      }
    );
  }
}

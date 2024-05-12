import {Component, OnInit} from '@angular/core';
import {Observable, switchMap} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../services/user.service";
import {User} from "../models/user.model";
import {AsyncPipe, CommonModule, DatePipe} from "@angular/common";

@Component({
  selector: 'app-user-page',
  standalone: true,
  imports: [
    CommonModule,
    AsyncPipe,
    DatePipe
  ],
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent implements OnInit {
  user$!: Observable<User>; // Correctly declare user$ as an Observable of User

  constructor(private route: ActivatedRoute, private userService: UserService) {}

  ngOnInit(): void {
    this.user$ = this.route.paramMap.pipe(
      switchMap(params => {
        const userId = Number(params.get('id')); // Extract the user ID from the URL
        console.log(`Fetching user with ID: ${userId}`)
        return this.userService.getUserById(userId); // Fetch the user details
      })
    );
  }
}

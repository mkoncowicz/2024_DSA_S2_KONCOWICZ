import {Component, OnInit} from '@angular/core';
import {User} from "../models/user.model";
import {UserService} from "../services/user.service";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './users.component.html',
  styleUrl: './users.component.css'
})
export class UsersComponent implements OnInit {
  private apiUrl = 'http://localhost:4200/users';
  users!: User[];
  constructor(public userService: UserService) {}
  ngOnInit() {
    this.userService.getAllUsers().subscribe(users => {
      this.users = users;
    });
  }

  delete(userId: number | undefined) {
    this.userService.deleteUserById(userId).subscribe({
      next: () => {
        window.location.href = `${this.apiUrl}`;
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

  getUserDetails(userId: number | undefined) {
    this.userService.getUserById(userId).subscribe({
      next: () => {
        window.location.href = `${this.apiUrl}/${userId}`;
      },
      error: error => {
        console.error('There was an error!', error);
      }
    });
  }

}

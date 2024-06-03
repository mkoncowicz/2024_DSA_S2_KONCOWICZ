import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {User} from "../models/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }


  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl)
  }

  getUserByEmail(email: string): Observable<User> {
    const url = `${this.apiUrl}/by-email/${email}`;
    return this.http.get<User>(url);
  }

  getUserById(userId: number | undefined): Observable<User> {
    if (userId === undefined) {
      console.error('User ID is undefined');
      return throwError(() => new Error('User ID is undefined, cannot get user.'));
    }

    return this.http.get<User>(`${this.apiUrl}/${userId}`)
  }

  deleteUserById(userId: number | undefined): Observable<any> {
    if (userId === undefined) {
      console.error('User ID is undefined');
      return throwError(() => new Error('User ID is undefined, cannot delete user.'));
    }

    return this.http.delete(`${this.apiUrl}/${userId}`)
  }

  downloadUserImage(userId: number): Observable<Blob> {
    const url = `${this.apiUrl}/${userId}/download-image`;
    return this.http.get(url, { responseType: 'blob' });
  }

  blankUser : User = {
  id: 0,
  username: "",
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  gender: "",
  dayOfBirth: []
}

}

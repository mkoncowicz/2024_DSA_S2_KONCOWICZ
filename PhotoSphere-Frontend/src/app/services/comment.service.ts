import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Comment } from '../models/comment.model';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private apiUrl = 'http://localhost:8080/api/comments';  // Corrected the base URL

  constructor(private http: HttpClient) {}

  getAllComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.apiUrl).pipe(
      catchError(this.handleError<Comment[]>('getAllComments', []))
    );
  }

  getCommentById(id: number): Observable<Comment> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Comment>(url).pipe(
      catchError(this.handleError<Comment>(`getCommentById id=${id}`))
    );
  }

  createComment(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(this.apiUrl, comment).pipe(
      catchError(this.handleError<Comment>('createComment'))
    );
  }

  updateComment(id: number, updates: Partial<Comment>): Observable<Comment> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Comment>(url, updates).pipe(
      catchError(this.handleError<Comment>('updateComment'))
    );
  }

  deleteCommentById(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url).pipe(
      catchError(this.handleError<void>('deleteCommentById'))
    );
  }

  getCommentsByPostId(postId: number): Observable<Comment[]> {
    const url = `${this.apiUrl}/post/${postId}`;
    return this.http.get<Comment[]>(url).pipe(
      catchError(this.handleError<Comment[]>('getCommentsByPostId', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}

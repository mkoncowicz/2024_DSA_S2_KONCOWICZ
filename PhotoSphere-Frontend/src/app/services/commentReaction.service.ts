import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CommentReaction } from '../models/commentReaction.model';

@Injectable({
  providedIn: 'root'
})
export class CommentReactionService {
  private apiUrl = 'http://localhost:8080/api/comment-reactions';

  constructor(private http: HttpClient) {}

  getAllCommentReactions(): Observable<CommentReaction[]> {
    return this.http.get<CommentReaction[]>(this.apiUrl);
  }

  getCommentReactionById(id: number): Observable<CommentReaction> {
    return this.http.get<CommentReaction>(`${this.apiUrl}/${id}`);
  }

  createOrUpdateCommentReaction(commentReaction: CommentReaction): Observable<CommentReaction> {
    return this.http.post<CommentReaction>(this.apiUrl, commentReaction);
  }

  deleteCommentReactionById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getReactionCountsByCommentId(commentId: number): Observable<Map<string, number>> {
    return this.http.get<{ [key: string]: number }>(`${this.apiUrl}/comment/${commentId}/reaction-summary`).pipe(
      map(response => {
        const reactionCounts = new Map<string, number>();
        for (const key in response) {
          if (response.hasOwnProperty(key)) {
            reactionCounts.set(key, response[key]);
          }
        }
        return reactionCounts;
      })
    );
  }
}

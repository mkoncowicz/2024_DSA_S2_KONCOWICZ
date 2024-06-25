import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReactionService {
  private apiUrl = 'http://localhost:8080/api/post-reactions';

  constructor(private http: HttpClient) {}

  getReactionCountsByPostId(postId: number): Observable<Map<string, number>> {
    return this.http.get<{ [key: string]: number }>(`${this.apiUrl}/post/${postId}/reaction-summary`).pipe(
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

  createOrUpdateReaction(reaction: { postId: number; userId: number; reaction: string; createdAt: Date }): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}`, reaction);
  }

  getPostReactions(postId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/post/${postId}`);
  }
}

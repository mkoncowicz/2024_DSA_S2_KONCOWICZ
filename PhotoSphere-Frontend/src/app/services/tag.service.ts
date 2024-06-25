import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tag} from "../models/tag.model";

@Injectable({
  providedIn: "root"
})
export class TagService {
  private apiUrl = 'http://localhost:8080/api/tags';

  constructor(private  http: HttpClient) {}

  getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(this.apiUrl);
  }

  getTagById(tagId: number): Observable<Tag> {
    return this.http.get<Tag>(`${this.apiUrl}/${tagId}`);
  }

  getTagByName(tagName: string): Observable<Tag> {
    return this.http.get<Tag>(`${this.apiUrl}/name/${tagName}`);
  }

  createTag(tag: Tag): Observable<Tag> {
    return this.http.post<Tag>(this.apiUrl, tag);
  }
}

import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Post} from "../model/post";

@Injectable({
  providedIn: 'root'
})
export class PostClient {
  constructor(
    private http: HttpClient,
  ) {
  }

  getTop50Posts = (): Observable<Post[]> => this.http.get('/api/top-50-posts') as Observable<Post[]>;
}

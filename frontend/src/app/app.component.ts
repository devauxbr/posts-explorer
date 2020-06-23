import {Component, OnInit} from '@angular/core';
import {PostClient} from "../service/post.client";
import {Post} from "../model/post";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  loading = false;
  data: Post[] = [];

  displayedColumns: (keyof Post)[] = [
    'id',
    'userId',
    'title',
    'body'
  ];

  constructor(
    private postClient: PostClient,
  ) {
  }

  ngOnInit(): void {
    this.loading = true;
    this.postClient.getTop50Posts().subscribe(posts => {
      this.data = posts;
    });
  }

  // Force typing in template
  getPost = (el) => el as Post;
}

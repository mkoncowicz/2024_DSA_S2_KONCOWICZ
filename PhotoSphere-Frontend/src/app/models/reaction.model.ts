export interface Reaction {
  id?: number;  // Assuming the backend might provide an ID for each reaction
  postId: number;
  userId: number;
  reaction: string;
  createdAt: Date;
}

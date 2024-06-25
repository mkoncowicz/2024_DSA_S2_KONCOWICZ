export interface CommentReaction {
  id?: number;
  commentId: number;
  userId: number;
  reaction: string;
  createdAt: Date;
}

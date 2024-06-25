
export interface Post {
  id: number;
  userId?: number;
  caption: string;
  imageUrl: string;
  category: string;
  description: string;
  private: boolean;
  createdAt: Date;
}

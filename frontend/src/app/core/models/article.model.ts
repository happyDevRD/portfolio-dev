export interface Article {
    id: number;
    title: string;
    slug: string;
    summary: string;
    content: string;
    coverImageUrl?: string;
    tags: string[];
    publishedAt: string;
    readingTimeMinutes: number;
}

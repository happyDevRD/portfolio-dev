export interface Project {
    id: number;
    title: string;
    description: string;
    imageUrl: string;
    projectUrl?: string;
    githubUrl?: string;
    startDate: string;
    endDate?: string;
    tags?: string[];
    challenge?: string;
    solution?: string;
    features?: string[];
}

import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { ProjectsComponent } from './features/projects/projects.component';
import { ResumeComponent } from './features/resume/resume.component';
import { ContactComponent } from './features/contact/contact.component';
import { BlogComponent } from './features/blog/blog/blog.component';
import { BlogPostComponent } from './features/blog/blog-post/blog-post.component';

export const routes: Routes = [
    { path: '', component: HomeComponent, data: { animation: 0 } },
    { path: 'projects', component: ProjectsComponent, data: { animation: 1 } },
    { path: 'projects/:id', loadComponent: () => import('./features/projects/project-detail/project-detail.component').then(m => m.ProjectDetailComponent), data: { animation: 6 } },
    { path: 'blog', component: BlogComponent, data: { animation: 2 } },
    { path: 'blog/:slug', component: BlogPostComponent, data: { animation: 3 } },
    { path: 'resume', component: ResumeComponent, data: { animation: 4 } },
    { path: 'contact', component: ContactComponent, data: { animation: 5 } },
    { path: '**', redirectTo: '' }
];

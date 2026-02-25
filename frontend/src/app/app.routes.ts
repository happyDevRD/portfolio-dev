import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./features/home/home.component').then(m => m.HomeComponent),
        title: 'Eleazar Garcia | Desarrollador Full Stack',
        data: { animation: 0 }
    },
    {
        path: 'projects',
        loadComponent: () => import('./features/projects/projects.component').then(m => m.ProjectsComponent),
        title: 'Proyectos | Eleazar Garcia',
        data: { animation: 1 }
    },
    {
        path: 'projects/:id',
        loadComponent: () => import('./features/projects/project-detail/project-detail.component').then(m => m.ProjectDetailComponent),
        title: 'Detalle del Proyecto | Eleazar Garcia',
        data: { animation: 6 }
    },
    {
        path: 'blog',
        loadComponent: () => import('./features/blog/blog/blog.component').then(m => m.BlogComponent),
        title: 'Bitácora | Eleazar Garcia',
        data: { animation: 2 }
    },
    {
        path: 'blog/:slug',
        loadComponent: () => import('./features/blog/blog-post/blog-post.component').then(m => m.BlogPostComponent),
        title: 'Artículo | Eleazar Garcia',
        data: { animation: 3 }
    },
    {
        path: 'resume',
        loadComponent: () => import('./features/resume/resume.component').then(m => m.ResumeComponent),
        title: 'Currículum | Eleazar Garcia',
        data: { animation: 4 }
    },
    {
        path: 'contact',
        loadComponent: () => import('./features/contact/contact.component').then(m => m.ContactComponent),
        title: 'Contacto | Eleazar Garcia',
        data: { animation: 5 }
    },
    {
        path: '**',
        loadComponent: () => import('./features/not-found/not-found.component').then(m => m.NotFoundComponent),
        title: 'Página no encontrada | Eleazar Garcia',
        data: { animation: 99 }
    }
];

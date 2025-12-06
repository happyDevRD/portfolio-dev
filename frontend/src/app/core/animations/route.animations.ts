import { trigger, transition, style, query, animate, group } from '@angular/animations';

export const slideInAnimation =
    trigger('routeAnimations', [
        // Move Forward (Increment) -> New enter from Right, Old leave to Left
        transition(':increment', [
            style({ position: 'relative' }),
            query(':enter, :leave', [
                style({
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    width: '100%'
                })
            ], { optional: true }),
            query(':enter', [
                style({ left: '100%', opacity: 0 })
            ], { optional: true }),
            group([
                query(':leave', [
                    animate('400ms ease-out', style({ left: '-100%', opacity: 0 }))
                ], { optional: true }),
                query(':enter', [
                    animate('400ms ease-out', style({ left: '0%', opacity: 1 }))
                ], { optional: true })
            ])
        ]),

        // Move Backward (Decrement) -> New enter from Left, Old leave to Right
        transition(':decrement', [
            style({ position: 'relative' }),
            query(':enter, :leave', [
                style({
                    position: 'absolute',
                    top: 0,
                    left: 0,
                    width: '100%'
                })
            ], { optional: true }),
            query(':enter', [
                style({ left: '-100%', opacity: 0 })
            ], { optional: true }),
            group([
                query(':leave', [
                    animate('400ms ease-out', style({ left: '100%', opacity: 0 }))
                ], { optional: true }),
                query(':enter', [
                    animate('400ms ease-out', style({ left: '0%', opacity: 1 }))
                ], { optional: true })
            ])
        ])
    ]);

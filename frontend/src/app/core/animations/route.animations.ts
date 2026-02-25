import { trigger, transition, style, query, animate, group } from '@angular/animations';

/**
 * Animación de transición entre rutas: desplazamiento horizontal suave.
 * - Avanzar: nuevo entra desde la derecha, anterior sale a la izquierda.
 * - Retroceder: nuevo entra desde la izquierda, anterior sale a la derecha.
 */
export const slideInAnimation = trigger('routeAnimations', [
  transition(':increment', [
    style({ position: 'relative' }),
    query(':enter, :leave', [
      style({
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        minHeight: '100%'
      })
    ], { optional: true }),
    query(':enter', [style({ opacity: 0, transform: 'translateX(32px)' })], { optional: true }),
    group([
      query(':leave', [
        animate('280ms ease-out', style({ opacity: 0, transform: 'translateX(-32px)' }))
      ], { optional: true }),
      query(':enter', [
        animate('280ms ease-out', style({ opacity: 1, transform: 'translateX(0)' }))
      ], { optional: true })
    ])
  ]),
  transition(':decrement', [
    style({ position: 'relative' }),
    query(':enter, :leave', [
      style({
        position: 'absolute',
        top: 0,
        left: 0,
        width: '100%',
        minHeight: '100%'
      })
    ], { optional: true }),
    query(':enter', [style({ opacity: 0, transform: 'translateX(-32px)' })], { optional: true }),
    group([
      query(':leave', [
        animate('280ms ease-out', style({ opacity: 0, transform: 'translateX(32px)' }))
      ], { optional: true }),
      query(':enter', [
        animate('280ms ease-out', style({ opacity: 1, transform: 'translateX(0)' }))
      ], { optional: true })
    ])
  ])
]);

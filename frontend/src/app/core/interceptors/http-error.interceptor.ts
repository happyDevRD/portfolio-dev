import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

import { environment } from '../../../environments/environment';

/**
 * Registra errores HTTP de la API de forma centralizada (sin bloquear la UI).
 * Los componentes pueden seguir manejando errores con catchError si lo necesitan.
 */
export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((err: unknown) => {
      if (err instanceof HttpErrorResponse) {
        const payload = err.error;
        const id =
          typeof payload === 'object' && payload !== null && 'errorId' in payload
            ? String((payload as { errorId?: string }).errorId ?? '')
            : '';
        const msg = `[HTTP ${err.status}] ${req.method} ${req.url}${id ? ` errorId=${id}` : ''}`;
        if (!environment.production) {
          console.warn(msg, err.error);
        } else {
          console.warn(msg);
        }
      }
      return throwError(() => err);
    })
  );
};

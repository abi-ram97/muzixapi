import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthenticationService } from '../authentication/authentication.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
    constructor(private authservice: AuthenticationService) { }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (req.url.includes('http://localhost')) {
            let request = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${this.authservice.getToken()}`
                }
            });
            return next.handle(request);
        }
        else
            return next.handle(req);
    }

}

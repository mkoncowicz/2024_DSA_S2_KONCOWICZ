import {ApplicationConfig} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app.routes';
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  provideHttpClient,
  withInterceptors,
  withInterceptorsFromDi
} from "@angular/common/http";
import {provideOAuthClient} from "angular-oauth2-oidc";
import {provideAnimationsAsync} from "@angular/platform-browser/animations/async";
import {HttpTokenInterceptor} from "./open-api-services/interceptor/http-token.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    provideOAuthClient(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpTokenInterceptor,
      multi: true

    },
    provideHttpClient(withInterceptorsFromDi()),

    provideAnimationsAsync()],
};

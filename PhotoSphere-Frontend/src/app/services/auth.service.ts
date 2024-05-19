import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "./user.service";
import {catchError, map, Observable, of} from "rxjs";
import {Router} from "@angular/router";
import {User} from "../models/user.model";
import {AuthConfig, OAuthService} from "angular-oauth2-oidc";


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public isLoggedIn: boolean = false;
  public loggedUser: User = this.userService.blankUser;
  errorMessage: string = "";

  constructor(private userService: UserService, private router: Router, private oAuthService: OAuthService) {
    this.initConfiguration();
  }

  initConfiguration(){
    const authConfig: AuthConfig ={
      issuer: 'https://accounts.google.com',
      strictDiscoveryDocumentValidation: false,
      clientId: '1000659349717-m312artpd9ak2dj45u39pq3rbs08s2mk.apps.googleusercontent.com',
      redirectUri: window.location.origin + '/PopularPhotoPage',
      scope: 'openid profile email',
    };

    this.oAuthService.configure(authConfig);
    this.oAuthService.setupAutomaticSilentRefresh();
    this.oAuthService.loadDiscoveryDocumentAndTryLogin();
  }

  googleLogin(){
    this.oAuthService.initImplicitFlow();
  }

  googleLogOut(){
    this.oAuthService.revokeTokenAndLogout();
    this.oAuthService.logOut();
  }

  googleGetProfile(){
    return this.oAuthService.getIdentityClaims();
  }

  googleGetToken(){
    return this.oAuthService.getAccessToken();
  }


  logIn(email: string, password: string) {
    this.errorMessage = "";
    this.userService.getUserByEmail(email).pipe(
      map((user) => {
        if (user) {
          if (password === user.password) {
            this.loggedUser = user;
            this.isLoggedIn = true;
            this.router.navigate(['/PopularPhotoPage']);
          } else {
            //console.error('Incorrect password');
            alert('Incorrect login data');
          }
        } else {
          //console.error('User not found');
          alert('User not found');
        }
      }),
      catchError((error) => {
        //console.error('Error fetching user:', error);
        alert('Error fetching user');
        return of(null);
      })
    ).subscribe();
  }

  logOut() {
    this.isLoggedIn = false;
  }
}

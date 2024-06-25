import { Routes } from '@angular/router';
import {RegistrationPageComponent} from "./registration-page/registration-page.component";
import {LogInComponent} from "./log-in/log-in.component";
import {UsersComponent} from "./users/users.component";
import {UserPageComponent} from "./user-page/user-page.component";
import {PopularPhotoPageComponent} from "./popular-photo-page/popular-photo-page.component";
import{ NewPhotosPageComponent} from "./new-photos-page/new-photos-page.component";
import {AddNewPhotoComponent} from "./add-new-photo/add-new-photo.component";
import {ProfilePageComponent} from "./profile-page/profile-page.component";
import {EditPhotoComponent} from "./edit-photo/edit-photo.component";
import {ZoomInPhotoComponent} from "./zoom-in-photo/zoom-in-photo.component";
import {EditProfileComponent} from "./edit-profile/edit-profile.component";
import {ExampleS3ApiComponent} from "./example-s3-api/example-s3-api.component";
import {SearchResultsComponent} from "./search-results/search-results.component";
import {authGuard} from "./open-api-services/guard/auth.guard";
import {HelpPageComponent} from "./help-page/help-page.component";

export const routes: Routes = [
  {path: '', redirectTo: 'logging', pathMatch: 'full'},
  {path: 'registration', component: RegistrationPageComponent},
  {path: 'logging', component: LogInComponent},
  {path: 'users', component: UsersComponent, canActivate: [authGuard]},
  {path: 'users/:id', component: UserPageComponent, canActivate: [authGuard]},
  {path: 'users', component: PopularPhotoPageComponent, canActivate: [authGuard]},
  {path: 'PopularPhotoPage', component: PopularPhotoPageComponent, canActivate: [authGuard]},
  {path: 'NewPhotosPage', component: NewPhotosPageComponent, canActivate: [authGuard]},
  {path: 'AddNewPhoto', component: AddNewPhotoComponent, canActivate: [authGuard]},
  {path: 'ProfilePage/:id', component: ProfilePageComponent, canActivate: [authGuard]},
  {path: 'ProfilePage', component: PopularPhotoPageComponent, canActivate: [authGuard]},
  {path: 'EditPhoto', component: EditPhotoComponent, canActivate: [authGuard]},
  {path: 'ZoomInPhoto', component: ZoomInPhotoComponent, canActivate: [authGuard]},
  {path: 'EditProfile', component: EditProfileComponent, canActivate: [authGuard]},
  {path: 'SearchResults', component: SearchResultsComponent, canActivate: [authGuard]},
  {path: 'ApiTest', component: ExampleS3ApiComponent, canActivate: [authGuard]},
  {path: 'HelpPage', component: HelpPageComponent, canActivate: [authGuard]},
  {path: '', redirectTo: 'logging', pathMatch: 'full'}
];


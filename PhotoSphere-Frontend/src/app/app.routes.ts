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

export const routes: Routes = [
  {path: 'registration', component: RegistrationPageComponent},
  {path: 'logging', component: LogInComponent},
  {path: 'users', component: UsersComponent},
  {path: 'users/:id', component: UserPageComponent},
  {path: 'PopularPhotoPage', component: PopularPhotoPageComponent},
  {path: 'NewPhotosPage', component: NewPhotosPageComponent},
  {path: 'AddNewPhoto', component: AddNewPhotoComponent},
  {path: 'ProfilePage', component: ProfilePageComponent},
  {path: 'EditPhoto', component: EditPhotoComponent},
  {path: 'ZoomInPhoto', component: ZoomInPhotoComponent},
  {path: 'EditProfile', component: EditProfileComponent},
  {path: 'ApiTest', component: ExampleS3ApiComponent},
  {path: '', redirectTo: 'logging', pathMatch: 'full'},
];


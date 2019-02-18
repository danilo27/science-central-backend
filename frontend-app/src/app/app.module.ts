import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { RepositoryComponent } from './repository/repository.component';
import { UsersComponent } from './users/users.component';


import { AuthenticationService } from './services/authentication/authentication.service';
import { ProfileChangeService } from './services/profile_change/profile-change.service';
import { RepositoryService } from './services/repository/repository.service';
import { UserService } from './services/users/user.service';

import { RegistrationComponent } from './registration/registration.component';

import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { DynamicFormComponent } from './dynamic-form/dynamic-form.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { MagazineComponent } from './magazine/magazine.component';
import { LoginComponent } from './login/login.component';
import { ChiefEditorHomeComponent } from './chief-editor-home/chief-editor-home.component';
import { EditorHomeComponent } from './editor-home/editor-home.component';
import { ReviewerHomeComponent } from './reviewer-home/reviewer-home.component';
import { NotifierModule, NotifierOptions } from 'angular-notifier';
import { AssignComponent } from './assign/assign.component';
import { SearchComponent } from './search/search.component';

import { ReactiveFormsModule } from '@angular/forms';
import { AddUddComponent } from './add-udd/add-udd.component';
const ChildRoutes =
  [
  ]

  const RepositoryChildRoutes =
  [
    
  ]

const Routes = [
  
  
  {
    path: "repository",
    component: RepositoryComponent,
    children: RepositoryChildRoutes
  },
  {
    path: "users",
    component: UsersComponent,
    canActivate: [Admin]
  },
  {
    path: "registrate",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "login",
    component: LoginComponent,
    canActivate: [Notauthorized]
  },
  {
    path: "home",
    component: HomeComponent
  },
  {
    path: "magazine",
    component: MagazineComponent, data : {m : 'some value'}
  },
  {
    path: "chief_editor",
    component: ChiefEditorHomeComponent
  },
  {
    path: "editor",
    component: EditorHomeComponent
  },
  {
    path: "reviewer",
    component: ReviewerHomeComponent
  },
  {
    path: "assign",
    component: AssignComponent
  },
  {
    path: "search",
    component: SearchComponent
  },
  {
    path: "add-udd",
    component: AddUddComponent
  }
]

/**
 * Custom angular notifier options
 */
const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'left',
      distance: 12
    },
    vertical: {
      position: 'top',
      distance: 65,
      gap: 10
    }
  },
  theme: 'material',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    RepositoryComponent,
    UsersComponent,
    RegistrationComponent,
    DynamicFormComponent,
    HeaderComponent,
    HomeComponent,
    MagazineComponent,
    LoginComponent,
    ChiefEditorHomeComponent,
    EditorHomeComponent,
    ReviewerHomeComponent,
    AssignComponent,
    SearchComponent,
    AddUddComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule, 
    HttpModule,
    NotifierModule.withConfig(customNotifierOptions),
    ReactiveFormsModule 
  ],
  
  providers:  [
    Admin,
    Authorized,
    Notauthorized
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }

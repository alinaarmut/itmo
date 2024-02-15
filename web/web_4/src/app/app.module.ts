import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { TableModule } from 'primeng/table';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { DropdownModule } from 'primeng/dropdown';
import { FormsModule } from '@angular/forms';
import { KeyFilterModule } from 'primeng/keyfilter';
import { InputTextModule } from 'primeng/inputtext';
import { AppComponent } from './app.component';
import { StartComponent} from "./components/start/start.component";
import { HeaderComponent} from "./components/header/header.component";
import { LoginComponent} from "./components/login/login.component";
import { RegistrationComponent} from "./components/registration/registration.component";
import { MainComponent} from "./components/main/main.component";
import {RouterModule, Routes} from "@angular/router";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import {ListboxModule} from "primeng/listbox";

const appRoutes: Routes = [
  { path: '', component: StartComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'main', component: MainComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    StartComponent,
    HeaderComponent,
    LoginComponent,
    MainComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    TableModule,
    HttpClientModule,
    KeyFilterModule,
    InputTextModule,
    BrowserAnimationsModule,
    AutoCompleteModule,
    DropdownModule,
    RouterModule.forRoot(appRoutes),
    ListboxModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { InputComponent } from './input/input.component';
import { ShowHidePasswordComponent } from './show-hide-password/show-hide-password.component';
import { UserInformationComponent } from './user-information/user-information.component';
import { NavbarComponent } from './navbar/navbar.component';



@NgModule({
  declarations: [InputComponent,ShowHidePasswordComponent,UserInformationComponent,NavbarComponent],
  imports: [
    CommonModule,
    IonicModule
  ],
  exports:[InputComponent,ShowHidePasswordComponent,UserInformationComponent,NavbarComponent]
})
export class SharedComponentsModule { }

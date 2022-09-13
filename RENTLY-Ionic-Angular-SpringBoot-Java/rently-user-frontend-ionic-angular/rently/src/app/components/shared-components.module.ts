import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { InputComponent } from './input/input.component';
import { ShowHidePasswordComponent } from './show-hide-password/show-hide-password.component';
import { NavbarComponent } from './navbar/navbar.component';



@NgModule({
  declarations: [InputComponent,ShowHidePasswordComponent,NavbarComponent],
  imports: [
    CommonModule,
    IonicModule
  ],
  exports:[InputComponent,ShowHidePasswordComponent,NavbarComponent]
})
export class SharedComponentsModule { }

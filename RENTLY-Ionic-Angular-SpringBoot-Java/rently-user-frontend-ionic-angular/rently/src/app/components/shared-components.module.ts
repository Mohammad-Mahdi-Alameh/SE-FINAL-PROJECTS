import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { NavbarComponent } from './navbar/navbar.component';
import { ShowHidePasswordComponent } from './show-hide-password/show-hide-password.component';



@NgModule({
  declarations: [ShowHidePasswordComponent,NavbarComponent],
  imports: [
    CommonModule,
    IonicModule
  ],
  exports:[ShowHidePasswordComponent,NavbarComponent]
})
export class SharedComponentsModule { }

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  user: any = {};

  constructor(
    private auth: AuthService,
    private router: Router,
    private alertCtrl: AlertController
  ) {}

  ngOnInit() {}

  register() {
    this.auth.register(this.user).subscribe(async (res) => {
      if (res) {
        this.router.navigateByUrl('');
      } else {
        const alert = await this.alertCtrl.create({
          header: '',
          message: 'Fill the missing fields !',
          buttons: ['OK'],
        });
        await alert.present();
      }
    });
  }}

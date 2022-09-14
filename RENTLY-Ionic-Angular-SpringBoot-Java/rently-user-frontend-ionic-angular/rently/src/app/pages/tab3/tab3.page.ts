import { ChangeDetectorRef, Component, ElementRef, ViewChild } from '@angular/core';
import { AlertController, AnimationController } from '@ionic/angular';
import { Observable, of } from 'rxjs';
import { Vehicle } from 'src/app/interfaces/vehicle';
import { FoundationService } from 'src/app/services/foundation/foundation.service';
import { VehicleService } from 'src/app/services/vehicle/vehicle.service';

@Component({
  selector: 'app-tab3',
  templateUrl: 'tab3.page.html',
  styleUrls: ['tab3.page.scss']
})
export class Tab3Page {
  headerStatus:boolean=false;
  message:string=""
  searchTerm:string;
  vehicles: Vehicle[];
  observableVehicles: Observable<any[]>;
  @ViewChild('myfab', { read: ElementRef }) carBtn: ElementRef;
constructor(public vehicleService: VehicleService,private alertCtrl: AlertController){}

  ngOnInit() {
    this.getCars();

  }
  
  getCars() {
    this.vehicleService.getCars().subscribe(async (res) => {
      if (res.length) {
        const alert = await this.alertCtrl.create({
          header: '',
          message: 'No data to show !',
          buttons: ['OK'],
        });
        await alert.present();
      } else {
        const alert = await this.alertCtrl.create({
          header: '',
          message: 'Error Fetching data !',
          buttons: ['OK'],
        });
        await alert.present();
      }
    });
  }
  getVehicles(): void {
    this.vehicleService.getCars()
        .subscribe(vehicles => this.vehicles = vehicles);
    this.observableVehicles = of(this.vehicles);
  }
 
}


import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AlertController, AnimationController, ModalController } from '@ionic/angular';
import { Observable, of } from 'rxjs';
// import { runInThisContext } from 'vm';
import { Vehicle } from '../../interfaces/vehicle';
import { VehicleService } from '../../services/vehicle/vehicle.service';
import { FoundationService } from '../../services/foundation/foundation.service';

@Component({
  selector: 'app-tab1',
  templateUrl: 'tab1.page.html',
  styleUrls: ['tab1.page.scss']
})
// , AfterViewInit
export class Tab1Page implements OnInit{

  headerStatus:boolean=false;
  // backdropVisible:boolean= false;
  message:string=""
  searchTerm:string;
  vehicles: Vehicle[];
  observableVehicles: Observable<any[]>;
  @ViewChild('myfab', { read: ElementRef }) carBtn: ElementRef;
  // cart = {};
  // cartAnimation: Animation;
//, private modalCtrl: ModalController
constructor(public vehicleService: VehicleService, private animationCtrl: AnimationController,public foundation: FoundationService ,private changeDetectorRef : ChangeDetectorRef,private modalCtrl: ModalController,
private alertCtrl: AlertController){}

  ngOnInit() {
    this.getMotorcycles();

  }
  
  getMotorcycles() {
    this.vehicleService.getMotorcycles().subscribe(async (res) => {
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
    this.vehicleService.getMotorcycles()
        .subscribe(vehicles => this.vehicles = vehicles);
    this.observableVehicles = of(this.vehicles);
  }
 
}

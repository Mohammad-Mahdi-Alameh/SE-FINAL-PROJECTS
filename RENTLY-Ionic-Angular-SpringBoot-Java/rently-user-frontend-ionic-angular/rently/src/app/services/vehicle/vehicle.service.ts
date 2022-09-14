import { Injectable } from '@angular/core';

import { Vehicle } from '../../interfaces/vehicle';
// import { VEHICLES } from '../../data/vehicles';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  baseUrl : string  ="http://localhost:8080/api/v1"
  
  authenticatedOptions = {
    header: new HttpHeaders({ 'Authorization': 'Bearer ' + localStorage.getItem("token") })
  };

  selectedVehicle?: Vehicle;
  onSelect(vehicle: Vehicle): void {
  this.selectedVehicle = vehicle;
  }

  constructor(private http: HttpClient, private router: Router) { }

  // getVehicles(): Observable<Vehicle[]> {
  //   const vehicles = of(VEHICLES);
  //   return vehicles;
  // }

  getCars(){
    return this.http
      .get<any>(
        this.baseUrl +'/vehicles'
      )
      .pipe(catchError(this.handleError<any>('Error occured')));
  }
  getMotorcycles():Observable<Vehicle[]>{
    return this.http
      .get<any>(
        this.baseUrl +'/motorcycles'
      )
      .pipe(catchError(this.handleError<any>('Error occured')));
  }
  // getVehicle():Observable<Vehicle>{

  // }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      console.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rent-history',
  templateUrl: './rent-history.page.html',
  styleUrls: ['./rent-history.page.scss'],
})
export class RentHistoryPage implements OnInit {
  rentHistory:string="Rent History";
  constructor() { }

  ngOnInit() {
  }

}

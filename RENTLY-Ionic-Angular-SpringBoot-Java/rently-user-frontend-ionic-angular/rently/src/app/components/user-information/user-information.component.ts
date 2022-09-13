import { CommonModule } from '@angular/common';
import { Component, ContentChild, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-information',
  templateUrl: './user-information.component.html',
  styleUrls: ['./user-information.component.scss'],
})
export class UserInformationComponent implements OnInit {
  @Input() button?: string;
  // @Input() class?: string;
  @Input() withImage?: boolean;
  @Input() navbarTitle?: string;
  
  
  constructor() { }

  ngOnInit() {}

}

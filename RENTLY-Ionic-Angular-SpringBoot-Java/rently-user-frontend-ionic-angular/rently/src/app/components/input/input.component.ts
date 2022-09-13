import { Component, Input, OnInit, } from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss'],
})
export class InputComponent implements OnInit {
@Input() withoutPassword:boolean;
@Input() placeholder:string;
@Input() type:string;
@Input() label:string;

  constructor() { }

  ngOnInit() {
    if(this.type =="password"){
      this.withoutPassword=false;
    }else{
      this.withoutPassword=true;
    }
  }

}

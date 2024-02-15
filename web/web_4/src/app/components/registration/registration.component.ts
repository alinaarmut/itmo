import {Component, OnInit} from '@angular/core'
import {Data} from "../../data";
import {DataService} from "../../data.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})

export class RegistrationComponent implements OnInit{
  loginValue: string = '';
  passwordValue: string = '';
  isTextVisible: boolean = false;
  errorMessage: string = '';

  constructor(public dataRegister: Data, private dataService: DataService, private router: Router) {
  }

  ngOnInit() {
    if (localStorage.getItem('sessionId')){
      this.router.navigate(['/main']);
    }
  }
  checkInput() {
    const containsRussian = /[а-яА-Я]/.test(<string>this.dataRegister.username) || /[а-яА-Я]/.test(<string>this.dataRegister.password);
    // @ts-ignore
    const loginLen = this.dataRegister.username.length > 30;
    this.isTextVisible = containsRussian || loginLen;

    if (containsRussian){
      this.errorMessage = 'Only english letters in login and password!';
    }
    if (loginLen){
      this.errorMessage = 'Max login length is 30';
    }
  }

  tryRegistration(){
    console.log(this.dataRegister);
    this.dataService.registerUser(this.dataRegister).subscribe(
      (response) => {
        this.isTextVisible = false;
        if (response.statusCode == 201){
          localStorage.setItem('username', this.dataRegister.username);
          localStorage.setItem('sessionId', response.jwt);
          console.log('Data sent successfully', response);
          this.router.navigate(['/main']);
        }else{
          this.errorMessage = response.message;
          this.isTextVisible = true;
        }
      },
      (error) => {
        this.errorMessage = 'Something went wrong, try again.';
        this.isTextVisible = true;
      }
    );
  }

}

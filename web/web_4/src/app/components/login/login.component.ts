import {Component, OnInit} from '@angular/core'
import {Data} from "../../data";
import {DataService} from "../../data.service";
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent implements OnInit {
  loginValue: string = '';
  passwordValue: string = '';
  isTextVisible: boolean = false;
  errorMessage: string = '';

  constructor(public dataLogin: Data, private dataService: DataService, private router: Router) {
  }

  ngOnInit() {
    if (localStorage.getItem('sessionId')){
      this.router.navigate(['/main']);
    }
  }
  checkInput() {
    const containsRussian = /[а-яА-Я]/.test(<string>this.dataLogin.username) || /[а-яА-Я]/.test(<string>this.dataLogin.password);
    // @ts-ignore
    const loginLen = this.dataLogin.username.length > 30;
    this.isTextVisible = containsRussian || loginLen;
    if (containsRussian){
      this.errorMessage = 'Only english letters in login and password!';
    }
    if (loginLen){
      this.errorMessage = 'Max login length is 30';
    }
  }

  tryLogin(){
    console.log(this.dataLogin);
    this.dataService.loginUser(this.dataLogin).subscribe(
        (response) => {
          this.isTextVisible = false;
          if (response.statusCode == 200){
            localStorage.setItem('username', this.dataLogin.username);
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

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm:FormGroup;
  constructor(private fb:FormBuilder,private authService:AuthenticationService,
    private router:Router,private snackBar:MatSnackBar) { }

  ngOnInit() {
    this.loginForm = this.fb.group({
      userId:['',[Validators.required]],
      password:['',Validators.required]
    });
  }
  login(){
    this.authService.loginUser(this.loginForm.value).subscribe(
      response=>{
        if(response['token']){
          this.authService.setToken(response['token']);
          this.router.navigate(['muzix/top-tracks']);
        }
      },
      (error:Response)=>{
        if(error.status === 401)
          this.snackBar.open('Invalid credentials','',{duration:1000});
      }
    );
  }
}

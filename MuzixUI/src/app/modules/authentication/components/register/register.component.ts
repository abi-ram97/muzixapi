import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm:FormGroup;
  constructor(private fb:FormBuilder,private authService:AuthenticationService,private router:Router) { }

  ngOnInit() {
    this.registerForm = this.fb.group({
      firstName:[''],
      lastName:[''],
      userId:['',[Validators.required,Validators.minLength(5)]],
      password:['',[Validators.required]]
    });
  }
  register(){
    this.authService.registerUser(this.registerForm.value).subscribe(
      response=>{
        this.router.navigate(['/login']);
      }
    );
  }

}

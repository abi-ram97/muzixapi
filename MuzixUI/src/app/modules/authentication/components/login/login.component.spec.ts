import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { LoginComponent } from './login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { By } from '@angular/platform-browser';
import { Location } from '@angular/common'

let userData ={
  userId:'test',
  password:'test'
}
describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let location:Location;
  class dummy{}
  class AuthServiceStub {
        constructor() { }
    login(credentials) {
      if(credentials.userId == userData.userId) {
        return of(credentials.userId);
      } else {
        return of(false);
      }
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports:[ReactiveFormsModule,SharedModule,
      RouterTestingModule.withRoutes([{path:'',component: dummy}])
    ],
    providers:[{provide: AuthenticationService, useClass: AuthServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    location = TestBed.get(Location);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should have two input fields',()=>{
    let userId = fixture.debugElement.query(By.css('#userId')).nativeElement;
    let password = fixture.debugElement.query(By.css('#password')).nativeElement;
    let loginButton = fixture.debugElement.query(By.css('.login-user')).nativeElement;

    expect(userId).toBeTruthy();
    expect(password).toBeTruthy();
    expect(loginButton).toBeTruthy();
  });
  it('should be able to login',()=>{
    let userId = fixture.debugElement.query(By.css('#userId')).nativeElement;
    let password = fixture.debugElement.query(By.css('#password')).nativeElement;
    let loginButton = fixture.debugElement.query(By.css('.login-user')).nativeElement;
    
    userId.value = 'test';
    password.value = 'test';
    loginButton.click();

    expect(location.path()).toBe('');
  });
});

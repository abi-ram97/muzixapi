import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { RegisterComponent } from './register.component';
import { Location } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthenticationService } from '../../authentication.service';
import { By } from '@angular/platform-browser';

let userData = {
  firstName: 'test',
  lastName: 'user',
  userId: 'testuser',
  password: 'testuser'
};
describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let location: Location;
  class dummy { }
  class AuthServiceStub {
    constructor() { }
    registerUser(credentials) {
      if (credentials.userId == userData.userId) {
        return of('User registered successfully');
      } else {
        return of(false);
      }
    }
  }
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterComponent ],
      imports:[ReactiveFormsModule,SharedModule,
      RouterTestingModule.withRoutes([{path:'',component: dummy}])
    ],
    providers:[{provide: AuthenticationService, useClass: AuthServiceStub}]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    location = TestBed.get(Location);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have four input fields',()=>{
    let firstName = fixture.debugElement.query(By.css('#firstName')).nativeElement;
    let lastName = fixture.debugElement.query(By.css('#lastName')).nativeElement;
    let userId = fixture.debugElement.query(By.css('#userId')).nativeElement;
    let password = fixture.debugElement.query(By.css('#password')).nativeElement;
    let registerButton = fixture.debugElement.query(By.css('.register-user')).nativeElement;

    expect(firstName).toBeTruthy();
    expect(lastName).toBeTruthy();
    expect(userId).toBeTruthy();
    expect(password).toBeTruthy();
    expect(registerButton).toBeTruthy();
  });

  it('should be able register user',()=>{
    let firstName = fixture.debugElement.query(By.css('#firstName')).nativeElement;
    let lastName = fixture.debugElement.query(By.css('#lastName')).nativeElement;
    let userId = fixture.debugElement.query(By.css('#userId')).nativeElement;
    let password = fixture.debugElement.query(By.css('#password')).nativeElement;
    let registerButton = fixture.debugElement.query(By.css('.register-user')).nativeElement;

    firstName.value = 'test';
    lastName.value = 'user';
    userId.value = 'testuser';
    password.value = 'testuser';
    registerButton.click();

    expect(location.path()).toBe('');
  });
});

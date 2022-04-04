import { TestBed, inject, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AuthenticationService } from './authentication.service';

let data = {
  firstname:'test',
  lastName:'user',
  userId:'test',
  password:'test'
};
let user ={
  userId:'test',
  password:'test'
};
describe('AuthenticationService', () => {
  
  let authService:AuthenticationService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthenticationService]
    });
    authService = TestBed.get(AuthenticationService);
  });

  it('should be created', () => {
    inject([AuthenticationService], (service: AuthenticationService) => {
      expect(service).toBeDefined();
    });

  });

  it('should register user data',fakeAsync(()=>{
    inject([AuthenticationService,HttpTestingController],(backend:HttpTestingController)=>{
      const mockreq = backend.expectOne(authService.serverEndPoint);
      expect(mockreq.request.url).toEqual(authService.serverEndPoint,'request url shuold match');
      expect(mockreq.request.method).toEqual('POST');
      mockreq.flush(data);
      backend.verify();
    });
    authService.registerUser(data).subscribe(
      (res:any)=>{
        expect(res).toBeDefined();
      }
    );
  }));
  it('should login user data',fakeAsync(()=>{
    inject([AuthenticationService,HttpTestingController],(backend:HttpTestingController)=>{
      const mockreq = backend.expectOne(authService.serverEndPoint);
      expect(mockreq.request.url).toEqual(authService.serverEndPoint,'request url shuold match');
      expect(mockreq.request.method).toEqual('POST');
      mockreq.flush(user);
      backend.verify();
    });
    authService.loginUser(user).subscribe((res:any)=>{
      expect(res).toBeDefined();
    }); 
  }));
});

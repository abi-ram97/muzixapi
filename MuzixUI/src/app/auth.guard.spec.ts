import { TestBed, async, inject } from '@angular/core/testing';

import { AuthGuard } from './auth.guard';
import { AuthenticationService } from './modules/authentication/authentication.service';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AuthGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[RouterTestingModule,HttpClientTestingModule],
      providers: [AuthGuard,AuthenticationService]
    });
  });

  it('should ...', inject([AuthGuard,AuthenticationService], (guard: AuthGuard) => {
    expect(guard).toBeTruthy();
  }));
});

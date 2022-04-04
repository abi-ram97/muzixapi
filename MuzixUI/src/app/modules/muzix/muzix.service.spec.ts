import { TestBed, inject, fakeAsync } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { MuzixService } from './muzix.service';

describe('MuzixService', () => {
  let muzixService:MuzixService;
  let data = {
    trackId:'123456',
    name:'track1',
    artistName:'artist1'
  };
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[MuzixService]
    });
    muzixService = TestBed.get(MuzixService);
  });

  it('should be created', () => {
    inject([MuzixService],(service:MuzixService)=>{
      expect(service).toBeDefined();
    });
  });
  it('should be able to save tracks',fakeAsync(()=>{
    inject([MuzixService,HttpTestingController],(backend:HttpTestingController)=>{
      const req = backend.expectOne(muzixService.serverEndPoint);
      expect(req.request.url).toContain(muzixService.serverEndPoint,'Request url match failed');
      expect(req.request.method).toContain('POST');
      req.flush(data);
      backend.verify();
    });
    muzixService.addToPlayList(data).subscribe(
      (res:any)=>{
        expect(res).toBeDefined();
        expect(res.trackId).toContain('123456');
      }
    );
  }));
  it('should be able to get tracks',fakeAsync(()=>{
    inject([MuzixService,HttpTestingController],(backend:HttpTestingController)=>{
      const req = backend.expectOne(muzixService.serverEndPoint);
      expect(req.request.url).toContain(muzixService.serverEndPoint,'Request url match failed');
      expect(req.request.method).toContain('GET','Request method match failed');
      req.flush(data);
      backend.verify();
    });
    muzixService.getMyPlayList().subscribe(
      (res:any)=>{
        expect(res).toBeDefined();
        expect(res.trackId).toContain('123456');
      }
    );
  }));
  it('should be able to delete track',fakeAsync(()=>{
    let response = 'Deleted successfully';
    inject([MuzixService,HttpTestingController],(backend:HttpTestingController)=>{
      const req = backend.expectOne(muzixService.serverEndPoint);
      expect(req.request.url).toContain(muzixService.serverEndPoint,'Request url match failed');
      expect(req.request.method).toContain('DELETE','Request method match failed');
      req.flush(response);
      backend.verify();
    });
    muzixService.deleteFromPlayList(data).subscribe(
      (res:any)=>{
        expect(res).toBeDefined();
        expect(res).toContain('Deleted successfully');
      }
    );
  }));
});

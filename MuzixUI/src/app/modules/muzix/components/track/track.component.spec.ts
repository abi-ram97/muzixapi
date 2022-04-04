import { TestBed, ComponentFixture, async } from '@angular/core/testing';
import { TrackComponent } from './track.component';
import { Track } from '../../track';
import { By } from '@angular/platform-browser';
import { SharedModule } from '../../../shared/shared.module';

describe('Track component',()=>{
    let component:TrackComponent;
    let fixture:ComponentFixture<TrackComponent>;
    let track :Track;
    beforeEach(async(()=>{
        TestBed.configureTestingModule({
            declarations:[TrackComponent],
            imports:[SharedModule]
        }).compileComponents();
    }));

    beforeEach(()=>{
        fixture = TestBed.createComponent(TrackComponent);
        component = fixture.componentInstance;
        track = new Track();
    });

    it('should add to playlist',()=>{
        track.trackId = '12345';
        component.track = track;
        fixture.detectChanges();
        component.addTrack.subscribe(
            data=>expect(data).toBe(track)
        );
        let addButton:HTMLElement = fixture.debugElement.query(By.css('.track-actions')).nativeElement;
        addButton.click();

    });
    it('should delete from playlist',()=>{
        track.trackId = '12345';
        component.track = track;
        component.usePlayList = true;
        fixture.detectChanges();
        component.addTrack.subscribe(
            data=>expect(data).toBe(track)
        );
        let deleteButton:HTMLElement = fixture.debugElement.query(By.css('.track-actions')).nativeElement;
        deleteButton.click();

    });
});
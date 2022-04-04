import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Track } from 'src/app/modules/muzix/track';

@Component({
  selector: 'muzix-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.css']
})
export class TrackComponent implements OnInit {
  @Input()
  track:Track;
  @Input()
  usePlayList:boolean;
  @Output()
  addTrack = new EventEmitter();
  @Output()
  removeTrack = new EventEmitter();
  constructor() { }

  ngOnInit() {
  }
  addToPlayList(){
    this.addTrack.emit(this.track);
  }
  deleteFromPlayList(){
    this.removeTrack.emit(this.track);
  }

}

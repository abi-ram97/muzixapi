import { Component, OnInit } from '@angular/core';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Track } from 'src/app/modules/muzix/track';

@Component({
  selector: 'muzix-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {
  tracks:Array<Track>;
  usePlayList:boolean;
  constructor(private muzixService:MuzixService,private snackBar:MatSnackBar) { 
    this.tracks = [];
    this.usePlayList = true;
  }

  ngOnInit() {
    this.muzixService.getMyPlayList().subscribe(
      response=>{
        this.tracks.push(...response);
        if(this.tracks.length<1)
          this.snackBar.open('PlayList is empty','',{duration:2000});
      }
    );
  }

}

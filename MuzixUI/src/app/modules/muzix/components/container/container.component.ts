import { Component, OnInit, Input } from '@angular/core';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';
import { Track } from 'src/app/modules/muzix/track';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'muzix-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent implements OnInit {
  @Input()
  tracks:Array<Track>;
  @Input()
  usePlayList:boolean;
  
  constructor(private muzixService:MuzixService,private snackBar:MatSnackBar) { 
    
  }

  ngOnInit() {
  
  }
  addToPlayList(track){
    let message = `${track.name} added to playlist`;
    this.muzixService.addToPlayList(track).subscribe(
      response=>{
        this.snackBar.open(message,'',{duration:2000});
      }
    );
  }
  deleteFromPlayList(track){
    let message = `${track.name} removed from playlist`;
    this.tracks.splice(this.tracks.indexOf(track),1);
    this.muzixService.deleteFromPlayList(track).subscribe(
      response=>{
        this.snackBar.open(message,'',{duration:2000});
      }
    );
  }

}

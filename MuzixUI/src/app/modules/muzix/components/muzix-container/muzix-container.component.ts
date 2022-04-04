import { Component, OnInit } from '@angular/core';
import { MuzixService } from '../../muzix.service';
import { Track } from '../../track';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'muzix-muzix-container',
  templateUrl: './muzix-container.component.html',
  styleUrls: ['./muzix-container.component.css']
})
export class MuzixContainerComponent implements OnInit {
  tracks:Array<Track>;
  method:string;
  constructor(private muzixService:MuzixService,private route:ActivatedRoute) { 
    this.tracks = [];
    this.route.data.subscribe(data=>{
      this.method = data.method;}
    );
  }

  ngOnInit() {
    this.muzixService.getTopTracks(this.method).subscribe(
      (response:Array<Track>)=>{
        this.tracks.push(...response);
      }
    );
  }

}

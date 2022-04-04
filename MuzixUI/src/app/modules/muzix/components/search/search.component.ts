import { Component, OnInit } from '@angular/core';
import { MuzixService } from 'src/app/modules/muzix/muzix.service';
import { Track } from 'src/app/modules/muzix/track';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'muzix-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  tracks:Array<Track>;
  constructor(private muzixService:MuzixService,private snackBar:MatSnackBar) { 
    this.tracks = [];
  }

  ngOnInit() {
  }

  onEnter(searchKey:string){
    this.muzixService.getSearch(searchKey).subscribe(
      (response:Array<Track>)=>{
        this.tracks = response;
        if(this.tracks.length === 0)
          this.snackBar.open('No results found','',{duration:2000});
      }
    );
  }
}

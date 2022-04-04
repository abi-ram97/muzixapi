import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators/map';
import { Track } from 'src/app/modules/muzix/track';
import { Observable } from 'rxjs';

@Injectable()
export class MuzixService {
  apiEndPoint:string;
  apiKey:string;
  serverEndPoint:string;
  constructor(private http:HttpClient) { 
    this.apiKey = 'api_key=0e82bee45de9d24bad7a388c196c5cfb';
    this.apiEndPoint = 'http://ws.audioscrobbler.com/2.0/?method=';
    this.serverEndPoint = 'http://localhost:8082/api/v1/muzixservice/muzix';
  }

  getTopTracks(method:string){
    const url = `${this.apiEndPoint}${method}&${this.apiKey}&format=json&limit=10`;
    return this.http.get(url).pipe(
      map(this.pickTracks),
      map(this.transFormer.bind(this))
    );
  }

  pickTracks(object){
    return object['tracks']['track'];
  }
 
  transFormer(objects){
    return objects.map(track=>{
      track.trackId = track.mbid;
      track.imageUrl = track.image[2]['#text'];
      if(track.artist.name===undefined)
        track.artistName = track.artist;
      else
        track.artistName = track.artist.name;
      return track;
    });
  }
  addToPlayList(track){
    return this.http.post(this.serverEndPoint,track);
  }
  deleteFromPlayList(track){
    const url = `${this.serverEndPoint}/${track.id}`;
    return this.http.delete(url,{responseType:'text'});
  }
  getMyPlayList():Observable<Array<Track>>{
    return this.http.get<Array<Track>>(this.serverEndPoint);
  }
  getSearch(searchKey:string){
    const url =`${this.apiEndPoint}track.search&track=${searchKey}&${this.apiKey}&format=json&limit=10`;
    return this.http.get(url).pipe(
      map(object=>{
        return object['results']['trackmatches']['track'];
      }),
      map(this.transFormer.bind(this))
    );
  }
}

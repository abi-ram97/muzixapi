import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MuzixRoutingModule } from './muzix-routing.module';
import { SharedModule } from 'src/app/modules/shared/shared.module';
import { TrackComponent } from './components/track/track.component';
import { ContainerComponent } from './components/container/container.component';
import { MuzixService } from './muzix.service';
import { PlaylistComponent } from './components/playlist/playlist.component';
import { SearchComponent } from './components/search/search.component';
import { MuzixContainerComponent } from './components/muzix-container/muzix-container.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './interceptor';

@NgModule({
  declarations: [TrackComponent, ContainerComponent, PlaylistComponent, SearchComponent, MuzixContainerComponent],
  imports: [
    CommonModule,
    MuzixRoutingModule,
    SharedModule
  ],
  providers:[MuzixService,
    {provide:HTTP_INTERCEPTORS,useClass:TokenInterceptor,multi:true}
  ]
})
export class MuzixModule { }

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MuzixContainerComponent } from 'src/app/modules/muzix/components/muzix-container/muzix-container.component';
import { PlaylistComponent } from 'src/app/modules/muzix/components/playlist/playlist.component';
import { SearchComponent } from 'src/app/modules/muzix/components/search/search.component';
import { AuthGuard } from 'src/app/auth.guard';

const routes: Routes = [
  {path:'muzix',children:[
    {path:'',pathMatch:'full',redirectTo:'/muzix/top-tracks',canActivate:[AuthGuard]},
    {path:'top-tracks',component:MuzixContainerComponent,
          data:{method:'geo.gettoptracks&country=india'},canActivate:[AuthGuard]},
    {path:'trending',component:MuzixContainerComponent,
          data:{method:'chart.gettoptracks'},canActivate:[AuthGuard]},
    {path:'favorites',component:PlaylistComponent,canActivate:[AuthGuard]},
    {path:'search',component:SearchComponent,canActivate:[AuthGuard]}
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MuzixRoutingModule { }

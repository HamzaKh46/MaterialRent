import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostProductComponent } from './components/post-product/post-product.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { ReservationsComponent } from './components/reservations/reservations.component';
import { SearchProductComponent } from './components/search-product/search-product.component';

const routes: Routes = [
  { path: '', component: AdminComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'product', component: PostProductComponent },
  { path: 'product/:id/edit', component: UpdateProductComponent },
  { path: 'reservations', component: ReservationsComponent },
  { path: 'search', component: SearchProductComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }

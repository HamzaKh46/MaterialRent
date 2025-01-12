import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { StudentComponent } from './student.component';
import { ProductsComponent } from './components/products/products.component';
import { ViewBookingsComponent } from './components/view-bookings/view-bookings.component';
import { SearchProductComponent } from './components/search-product/search-product.component';

const routes: Routes = [
  { path: '', component: StudentComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'bookings', component: ViewBookingsComponent},
  { path: 'product/search', component: SearchProductComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentRoutingModule { }

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudentRoutingModule } from './student-routing.module';
import { StudentComponent } from './student.component';
import { ProductsComponent } from './components/products/products.component';
import { DemoNgZorroAntdModule } from '../../DemoNgZorroAntdModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ViewBookingsComponent } from './components/view-bookings/view-bookings.component';
import { SearchProductComponent } from './components/search-product/search-product.component';


@NgModule({
  declarations: [
    StudentComponent,
    ProductsComponent,
    ViewBookingsComponent,
    SearchProductComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    StudentRoutingModule,
    DemoNgZorroAntdModule,
    ReactiveFormsModule
  ]
})
export class StudentModule { }

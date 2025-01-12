import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { PostProductComponent } from   './components/post-product/post-product.component';
import { DemoNgZorroAntdModule } from '../../DemoNgZorroAntdModule';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UpdateProductComponent } from './components/update-product/update-product.component';
import { ReservationsComponent } from './components/reservations/reservations.component';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { SearchProductComponent } from './components/search-product/search-product.component';


@NgModule({
  declarations: [
    AdminComponent,
    DashboardComponent,
    PostProductComponent,
    UpdateProductComponent,
    ReservationsComponent,
    SearchProductComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    DemoNgZorroAntdModule,
    ReactiveFormsModule,
    FormsModule,
    DemoNgZorroAntdModule 

  ]
})
export class AdminModule { }

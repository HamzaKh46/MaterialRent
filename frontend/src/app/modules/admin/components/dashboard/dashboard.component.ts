import { Component } from '@angular/core';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';
import { NzModalService } from 'ng-zorro-antd/modal';

@Component({
  selector: 'app-dashboard',
  standalone: false,
  
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  currentPage = 1;
  products = [];
  total:any;
  loading = false;

  constructor(
    private adminService: AdminService,
    private message: NzMessageService,
    private modalService: NzModalService
  ) {
    this.getProducts();
  }

  getProducts() {
    this.adminService.getProducts(this.currentPage -1).subscribe(res=>{
      console.log(res);
      this.products = res.productDtoList;
      this.total = res.totalPages * 1;
  })
}
pageIndexChange(value:any) {
  this.currentPage = value;
  this.getProducts();
}

showConfirm(productId:number) {
  this.modalService.confirm({
    nzTitle: 'Are you sure delete this product?',
    nzContent: '<b style="color: red;">This action cannot be undone</b>',
    nzOkText: 'Yes',
    nzCancelText: 'No',
    nzOnOk: () => this.deleteProduct(productId)

  });

}

deleteProduct(productId:number) {
  this.adminService.deleteProduct(productId).subscribe(res=>{
    this.message.success('Product deleted successfully', { nzDuration: 4000 });
    this.getProducts();
  },error=>{
    this.message.error('Product deletion failed', { nzDuration: 4000 });
  })}

}

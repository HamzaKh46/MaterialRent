import { Component } from '@angular/core';
import { StudentService } from '../../service/student.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalService } from 'ng-zorro-antd/modal';
import { UserStorageService } from '../../../../auth/services/storage/user-storage.service';

@Component({
  selector: 'app-products',
  standalone: false,
  
  templateUrl: './products.component.html',
  styleUrl: './products.component.scss'
})
export class ProductsComponent {

  currentPage = 1;
    products = [];
    total:any;
    loading = false;
  
    constructor(
      private studentService: StudentService,
      private message: NzMessageService,
      private modalService: NzModalService
    ) {
      this.getProducts();
    }
  
    getProducts() {
      this.studentService.getProducts(this.currentPage -1).subscribe(res=>{
        console.log(res);
        this.products = res.productDtoList;
        this.total = res.totalPages * 1;
    })
  }
  pageIndexChange(value:any) {
    this.currentPage = value;
    this.getProducts();

  }
  isVisibleMiddle = false;
  date: Date[] = [];
  checkInDate: Date;
  checkOutDate: Date;
  id :number;
  onChange(result: Date[]) {
    if(result.length == 2) {
      this.checkInDate = result[0];
      this.checkOutDate = result[1];
    }

  }

  handleCancelMiddle() {
    this.isVisibleMiddle = false;
  }

  handleOkMiddle(): void {
    const obj = {
      userId: UserStorageService.getUserId(),
      productId: this.id,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate
    }

    this.studentService.bookProduct(obj).subscribe(res=>{
      this.message.success('Booking submitted for approval!', { nzDuration: 4000 });
      this.isVisibleMiddle = false;
    },error=>{
      this.message.error('Booking failed', { nzDuration: 4000 });
    });
  }

  showModalMiddle(id:number): void {
    this.id = id;
    this.isVisibleMiddle = true;
  }

}

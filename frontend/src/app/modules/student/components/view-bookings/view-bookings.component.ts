import { Component } from '@angular/core';
import { StudentService } from '../../service/student.service';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-view-bookings',
  standalone: false,
  
  templateUrl: './view-bookings.component.html',
  styleUrl: './view-bookings.component.scss'
})
export class ViewBookingsComponent {

  currentPage:any = 1;
  total:any;
  bookings:any;

  constructor(
    private studentService: StudentService,
    private message: NzMessageService
  ) {
    this.getBookings();
  }

  getBookings() {
    this.studentService.getMyBookings(this.currentPage -1).subscribe(res=>{
      console.log(res);
      this.bookings = res.reservationDtoList;
      this.total = res.totalPages * 5;

    },error=>{
      this.message.error('Booking deletion failed', { nzDuration: 4000 });


    })
  }

  pageIndexChange(value:any) {
    this.currentPage = value;
    this.getBookings();
  }

}

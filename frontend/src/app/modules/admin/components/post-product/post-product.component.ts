import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';
import { error } from '@ant-design/icons-angular';

@Component({
  selector: 'app-post-product',
  standalone: false,
  
  templateUrl: './post-product.component.html',
  styleUrl: './post-product.component.scss'
})
export class PostProductComponent {

  productDetailsForm :FormGroup;

  constructor(
    private fb: FormBuilder,
    private message: NzMessageService,
    private router: Router,
    private adminService: AdminService
  ) { 
    this.productDetailsForm = this.fb.group({
      name: ["",Validators.required],
      type: ["",Validators.required],
      price: ["",Validators.required],
  })

}

submitForm() {
  this.adminService.postProductDetails(this.productDetailsForm.value).subscribe(res=>{
    this.message.success('Product created successfully', { nzDuration: 4000 });
    this.router.navigateByUrl('/admin/dashboard');

  },error=>{
    this.message.error('Product creation failed', { nzDuration: 4000 });
  })
  }
}
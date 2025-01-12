import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';
import { error } from '@ant-design/icons-angular';

@Component({
  selector: 'app-update-product',
  standalone: false,
  
  templateUrl: './update-product.component.html',
  styleUrl: './update-product.component.scss'
})
export class UpdateProductComponent {

  updateProductForm :FormGroup;
  id: number;
  
    constructor(
      private fb: FormBuilder,
      private message: NzMessageService,
      private router: Router,
      private adminService: AdminService,
      private activatedroute: ActivatedRoute
    ) { 
      this.id = this.activatedroute.snapshot.params['id'];
      this.updateProductForm = this.fb.group({
        name: ["",Validators.required],
        type: ["",Validators.required],
        price: ["",Validators.required],
    });

    this.getProductById();

  }

  submitForm() {
    this.adminService.updateProductDetails(this.id, this.updateProductForm.value).subscribe(res=>{
      this.message.success('Product updated successfully', { nzDuration: 4000 });
      this.router.navigateByUrl('/admin/dashboard');

    },error=>{
      this.message.error('Product update failed', { nzDuration: 4000 });
    })
  }

  getProductById() {
    this.adminService.getProductById(this.id).subscribe(res=>{
      this.updateProductForm.patchValue(res);

  },error=>{
    this.message.error('Product not found', { nzDuration: 4000 });

  })
}

}

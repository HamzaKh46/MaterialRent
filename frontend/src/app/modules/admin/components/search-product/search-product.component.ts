import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AdminService } from '../../admin-services/admin.service';

@Component({
  selector: 'app-search-product',
  standalone: false,
  
  templateUrl: './search-product.component.html',
  styleUrls: ['./search-product.component.scss']
})
export class SearchProductComponent {

  isSpinning = false;

  searchProductForm!: FormGroup;
  listOfOptions: Array<{ value: string; label: string }> = [];
  listOfType = ["Lighting", "Stage", "Control", "Audio", "Accessory"];
listOfNames = [
  "Stage Light",
  "Control Panel",
  "Cable",
  "LED Panel",
  "Podium Element",
  "Speaker",
  "Microphone",
  "Extension Cord",
  "Spotlight",
  "Truss System"
];

  listOfPrice = ["< 1000", "1000 - 5000", "5000 - 10000", "10000 - 20000", "> 20000"];

  products:any = [];
  constructor(
    private fb: FormBuilder,
    private adminService: AdminService,
  ) {
    this.searchProductForm = this.fb.group({
      name: [null],
      type: [null],
      price: [null],
    });
  }

  searchProduct() {
    this.isSpinning = true; // Start spinner

    console.log(this.searchProductForm.value); // Log form values for debugging

    this.adminService.searchProduct(this.searchProductForm.value).subscribe({
        next: (res) => {
            this.products = []; // Clear existing products if needed
            res.productDtoList.forEach((element: any) => {
                this.products.push({
                    id: element.id,
                    name: element.name,
                    type: element.type,
                    price: element.price,
                    process: element.process // Replace with actual property if needed
                });
            });
            this.isSpinning = false; // Stop spinner after processing
        },
        error: (err) => {
            console.error('Error fetching products:', err);
            this.isSpinning = false; // Stop spinner even if there's an error
        }
    });
}

}

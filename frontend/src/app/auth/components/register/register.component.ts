import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: false,
  
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerForm!: FormGroup;
  constructor(
    private fb:FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      name: [null, [Validators.required]],

    })
  }

  submitForm(): void {
    this.authService.register(this.registerForm.value).subscribe(res=>{
      if(res.id != null) {
        this.message.success('Register success', {nzDuration: 4000});
        this.router.navigateByUrl('/');
      }else {
        this.message.error('Register failed', {nzDuration: 4000});
      }   
      
    })

  }

}

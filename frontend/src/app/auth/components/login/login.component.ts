import { Component } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { AuthService } from '../../services/auth/auth.service';
import { UserStorageService } from '../../services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  standalone: false,
  
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm! :FormGroup;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private message: NzMessageService,
    private router: Router,) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: [null,[Validators.required, Validators.email]],
      password: [null, [Validators.required]]
    });
  }

  submitForm(): void {
    this.authService.login(this.loginForm.value).subscribe(res=>{
      console.log(res);
      if(res.userId != null) {
        const user = {
          id: res.userId,
          role: res.userRole
        };
        UserStorageService.saveUser(user);
        UserStorageService.saveToken(res.jwt);
        if(UserStorageService.isAdminLoggedIn()) {
          this.router.navigateByUrl('/admin/dashboard');
        }else if(UserStorageService.isStudentLoggedIn()) {
          this.router.navigateByUrl('/student/products');
        }
      }else {
        this.message.error('Login failed', {nzDuration: 4000});
      }
    })
    
  }

}

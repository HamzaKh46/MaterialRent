import { Component } from '@angular/core';
import { UserStorageService } from './auth/services/storage/user-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
  isStudentLoggedIn:Boolean = UserStorageService.isStudentLoggedIn();
  isAdminLoggedIn:Boolean = UserStorageService.isAdminLoggedIn();

  constructor(private router: Router) {}

  ngOnInit() {
    this.router.events.subscribe(event => {
      if(event.constructor.name === 'NavigationEnd') {
        this.isStudentLoggedIn = UserStorageService.isStudentLoggedIn();
        this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
      }
    });
  }
  logout() {
    UserStorageService.signOut();
    this.router.navigateByUrl('/');

  }
} 

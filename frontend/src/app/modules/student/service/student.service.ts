import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserStorageService } from '../../../auth/services/storage/user-storage.service';
import { Observable } from 'rxjs';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

  constructor(
    private http:HttpClient
  ) { }

  getProducts(pageNumber:number): Observable<any> {
      return this.http.get(BASIC_URL + `api/student/products/${pageNumber}`, {headers: this.createAuthorizationHeader()});
  
    }

  bookProduct(bookingDto: any): Observable<any> {
    return this.http.post(BASIC_URL + `api/student/book`, bookingDto, {headers: this.createAuthorizationHeader()});

  }

  getMyBookings(pageNumber:number): Observable<any> {
    const userId= UserStorageService.getUserId();
    return this.http.get(BASIC_URL + `api/student/bookings/${userId}/${pageNumber}`, {headers: this.createAuthorizationHeader()});

  }

  searchProduct(searchProductDto:any): Observable<any> {
      return this.http.post(BASIC_URL + `api/student/product/search`, searchProductDto, {headers: this.createAuthorizationHeader()});
  
    }

  createAuthorizationHeader() {
      let authHeaders: HttpHeaders = new HttpHeaders();
      return authHeaders.set('Authorization', 'Bearer ' + UserStorageService.getToken());
    }
}

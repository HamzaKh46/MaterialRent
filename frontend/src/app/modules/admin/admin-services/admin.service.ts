import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from '../../../auth/services/storage/user-storage.service';

const BASIC_URL = 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }

  postProductDetails(productDto:any): Observable<any> {
    return this.http.post(BASIC_URL + 'api/admin/product', productDto, {headers: this.createAuthorizationHeader()});

  }

  getProducts(pageNumber:number): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/products/${pageNumber}`, {headers: this.createAuthorizationHeader()});

  }

  getProductById(id:number): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/product/${id}`, {headers: this.createAuthorizationHeader()});

  }

  updateProductDetails(id:number, productDto:any): Observable<any> {
    return this.http.put(BASIC_URL + `api/admin/product/${id}`, productDto, {headers: this.createAuthorizationHeader()});

  }

  deleteProduct(productId:number): Observable<any> {
    return this.http.delete(BASIC_URL + `api/admin/product/${productId}`, {headers: this.createAuthorizationHeader()});

  }

  getReservations(pageNumber:number): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/reservations/${pageNumber}`, {headers: this.createAuthorizationHeader()});

  }

  changeReservationStatus(reservationId:number, status:string): Observable<any> {
    return this.http.get(BASIC_URL + `api/admin/reservation/${reservationId}/${status}`, {headers: this.createAuthorizationHeader()});

  }

  searchProduct(searchProductDto:any): Observable<any> {
    return this.http.post(BASIC_URL + `api/admin/product/search`, searchProductDto, {headers: this.createAuthorizationHeader()});

  }

  createAuthorizationHeader() {
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set('Authorization', 'Bearer ' + UserStorageService.getToken());
  }
}

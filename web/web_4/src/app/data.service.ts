import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private baseUrl = 'http://localhost:8081/api/v1/auth';

  constructor(private httpClient: HttpClient) {}

  registerUser(data: any): Observable<any> {
    const url = `${this.baseUrl}/register`;

    return this.httpClient.post(url, data);
  }

  loginUser(data: any): Observable<any> {
    const url = `${this.baseUrl}/authenticate`;

    return this.httpClient.post(url, data);
  }

  logoutUser(data:any): Observable<any> {
    const url = `${this.baseUrl}/logout`;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
    });

    return this.httpClient.delete(url, { headers });
  }
}

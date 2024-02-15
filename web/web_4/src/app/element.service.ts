import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ElementService {
  private baseUrl = 'http://localhost:8081/api/v1/points';

  constructor(private httpClient: HttpClient) {}

  addElement(element: any): Observable<any> {
    const url = `${this.baseUrl}/add`;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
    });

    return this.httpClient.post(url, element, { headers });

  }
  getAllElements(element: any): Observable<any> {
    const url = `${this.baseUrl}/list`;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
    });

    return this.httpClient.get(url, { headers });
  }

  clearAllElements(element: any): Observable<any> {
    const url = `${this.baseUrl}/clear`;

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem("sessionId")}`
    });

    return this.httpClient.post(url, element, { headers });
  }
}

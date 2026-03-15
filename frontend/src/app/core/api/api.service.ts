import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly baseUrl = 'http://localhost:8080/api';

  constructor(private readonly http: HttpClient) {}

  analyzeMinutes(rawText: string): Observable<unknown> {
    return this.http.post(`${this.baseUrl}/ai/minutes/analyze`, { rawText });
  }

  getMinutes(): Observable<unknown> {
    return this.http.get(`${this.baseUrl}/ai/minutes`);
  }

  getPatterns(): Observable<unknown> {
    return this.http.get(`${this.baseUrl}/events/rag/patterns`);
  }

  generatePlan(eventName: string, currentMonth: number): Observable<unknown> {
    return this.http.post(`${this.baseUrl}/events/rag/plan`, { eventName, currentMonth });
  }

  getDriveItems(): Observable<unknown> {
    return this.http.get(`${this.baseUrl}/google/drive-items`);
  }

  getGmailEvents(): Observable<unknown> {
    return this.http.get(`${this.baseUrl}/google/gmail-events`);
  }
}

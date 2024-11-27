import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrlFestival = 'http://localhost:9090/api/';
  private apiUrlReview = 'http://localhost:9191/api/';

  constructor(private http: HttpClient) {}

  // DJ API calls

  getAllDjs(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlFestival + 'dj');
  }

  getAllDjsWithLongName(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlFestival + 'dj/long');
  }

  getAllDjById(id: number): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlFestival + 'dj/' + id);
  }

  createDj(djData: { name: string; genre: string; age: number; performances: any[] }): Observable<any> {
    return this.http.post<any>(this.apiUrlFestival + 'dj', djData);
  }

  updateDj(djData: {id: number; name: string; genre: string; age: number; performances: any[] }): Observable<any> {
    return this.http.put<any>(this.apiUrlFestival + 'dj/'+ djData.id, djData);
  }

  deleteDj(id: number): Observable<any[]> {
    return this.http.delete<any[]>(this.apiUrlFestival + 'dj/' + id);
  }

  // Performances API calls

  getAllPerformances(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlFestival + 'performance');
  }

  getAllPerformanceById(id: number): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlFestival + 'performance/' + id);
  }

  createPerformance(performanceData: {
    name: string;
    songs: string[];
    dayOfPerformance: string;
    startTime: string;
    endTime: string;
    externalDJId: number;
  }): Observable<any> {
    return this.http.post<any>(this.apiUrlFestival + 'performance', performanceData);
  }

  updatePerformance(performanceData: {
    id: number;
    name: string;
    songs: string[];
    dayOfPerformance: string;
    startTime: string;
    endTime: string;
  }): Observable<any> {
    return this.http.put<any>(this.apiUrlFestival + 'performance/' + performanceData.id, performanceData);
  }

  deletePerformance(id: number): Observable<any[]> {
    return this.http.delete<any[]>(this.apiUrlFestival + 'performance/' + id);
  }

  // Reviews API calls

  getAllReviews(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlReview + 'review');
  }

  getReviewById(id:number): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlReview + 'review/' + id);
  }

  getAllReviewsWithPerformanceId(id:number): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrlReview + 'review/performanceId/' + id);
  }

  createReview(reviewData: {
    name: string;
    review: string;
    performanceId: number;
  }): Observable<any> {
    return this.http.post<any>(this.apiUrlReview + 'review', reviewData);
  }

  updateReview(reviewData: {
    id: number;
    name: string;
    review: string;
    performanceId: number;
  }): Observable<any> {
    return this.http.put<any>(this.apiUrlReview + 'review/' + reviewData.id, reviewData);
  }

  deleteReview(id:number): Observable<any[]> {
    return this.http.delete<any[]>(this.apiUrlReview + 'review/performanceId/' + id);
  }

}

import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { QuestionI } from '../models/question-i';
import { AnswerI } from '../models/answer-i';
@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  push(arg0: string) {
    throw new Error('Method not implemented.');
  }

  private url: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getPage(page: number): Observable<QuestionI[]> {
    let direction = this.url + 'pagination/' + page;
    return this.http.get<QuestionI[]>(direction);
  }

  getAnswer(id: any): Observable<QuestionI> {
    let direction = this.url + 'get/' + id;
    return this.http.get<QuestionI>(direction);
  }

  getTotalPages(): Observable<number> {
    let direction = this.url + 'totalPages';
    return this.http.get<number>(direction);
  }

  getCountQuestions(): Observable<number> {
    let direction = this.url + 'countQuestions';
    return this.http.get<number>(direction);
  }

  public getAllQuestion(): Observable<QuestionI[]> {
    return this.http.get<QuestionI[]>(`${this.url}/getAllQuestions`);
  }

  public saveQuestion(newQuestion: QuestionI): Observable<String> {
    return this.http.post(`${this.url}/createQuestion`, newQuestion, {
      responseType: 'text',
    });
  }

  public getQuestion(questionId: string): Observable<QuestionI> {
    return this.http.get<QuestionI>(`${this.url}/getQuestion/${questionId}`);
  }

  public saveAnswer(newAnswer: AnswerI, userEmail: any): Observable<any> {
    newAnswer.email = userEmail
    console.log(newAnswer);
    return this.http.post<any>(`${this.url}/addAnswer`, newAnswer, {
      observe: 'body',
    });
  }

  public editQuestion(newQuestion: QuestionI): Observable<QuestionI> {
    return this.http.put<QuestionI>(`${this.url}/updateQuestion`, newQuestion);
  }

  public updateAnswer(newAnswer: AnswerI): Observable<AnswerI> {
    return this.http.put<AnswerI>(`${this.url}/updateAnswer`, newAnswer);
  }

  public deleteQuestionById(questionId: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/deleteQuestion/${questionId}`);
  }

  public deleteAnswerById(answerId: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/deleteAnswer/${answerId}`);
  }

  //////////////////////

  /* public getAllQuestion(): Observable<QuestionI[]> {
    return this.http.get<QuestionI[]>(`${this.url}/getAllQuestions`);
  }

  public getAllQuestionsByUserId(userId:string): Observable<QuestionI> {
    return this.http.get<QuestionI>(`${this.url}/getAllQuestions/${userId}`);
  }

  public createQuestion(newQuestion:QuestionI): Observable<QuestionI> {
    return this.http.post<QuestionI>(`${this.url}/createQuestion`, newQuestion);
  }

  public getQuestion(questionId:string): Observable<QuestionI> {
    return this.http.get<QuestionI>(`${this.url}/getQuestion/${questionId}`);
  }

  public addAnswer(newAnswer:AnswerI): Observable<AnswerI> {
    return this.http.post<AnswerI>(`${this.url}/addAnswer`, newAnswer);
  }

  public updateQuestion(newQuestion:QuestionI): Observable<QuestionI> {
    return this.http.put<QuestionI>(`${this.url}/updateQuestion`, newQuestion);
  }

  public updateAnswer(newAnswer:AnswerI): Observable<AnswerI> {
    return this.http.put<AnswerI>(`${this.url}/updateAnswer`, newAnswer);
  }

  public deleteQuestionById(questionId: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/deleteQuestion/${questionId}`);
  }

  public deleteAnswerById(answerId: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/deleteAnswer/${answerId}`);
  }*/
}

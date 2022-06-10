import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { QuestionI } from '../models/question-i';
import { AnswerI } from '../models/answer-i';

@Injectable({
  providedIn: 'root',
})
export class QuestionService {
  
  /*push(arg0: string) {
    throw new Error('Method not implemented.');
  }*/

  private url: string = 'http://localhost:8080';

  constructor(private http:HttpClient) {}

  public getAllQuestion(): Observable<QuestionI[]> {
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

  public deleteQuestionById(questionId: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/deleteQuestion/${questionId}`);
  }

  public deleteAnswerById(answerId: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/deleteAnswer/${answerId}`);
  }

                  // FALTA EL SERVICIO DE ACTUALIZA RESPUESTA //

                  

  /*getPage(page: number): Observable<QuestionI[]> {
    let direction = this.url + 'pagination/' + page;
    return this.http.get<QuestionI[]>(direction);
  }

  getAnswer(id: any): Observable<QuestionI> {
    let direction = this.url + 'get/' + id;
    return this.http.get<QuestionI>(direction);
  }

  getQuestion(id: string): Observable<QuestionI> {
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

  saveQuestion(question: QuestionI): Observable<any> {
    let direction = this.url + 'create';
    return this.http.post<any>(direction, question, {
      responseType: 'text' as 'json',
    });
  }

  saveAnswer(answer: AnswerI): Observable<any> {
    let direction = this.url + 'add';
    return this.http.post<any>(direction, answer);
  }

  editQuestion(question: QuestionI): Observable<any> {
    let direction = this.url + 'update';
    return this.http.post<any>(direction, question);
  }*/
}

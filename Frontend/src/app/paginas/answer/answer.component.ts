import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionService } from 'src/app/Service/question.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css'],
  providers: [MessageService],
})
export class AnswerComponent implements OnInit {

  
  public form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(10)]],
    rating: ['', []],
  });

  @Input() item: any;
  constructor(
    private modalService: NgbModal,
    private services: QuestionService,
    private toastr: ToastrService,
    private route: Router,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    public authService: ServiceService
  ) {}

  answer: AnswerI = {
    id: "",
    userId: '',
    questionId: '',
    answer: '',
    position: 0,
    created:"jajaja",
    updated:"jojojo"
  };

  ngOnInit(): void {}

  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  saveAnswer(): void {
    this.answer.userId = this.item.userId;
    this.answer.questionId = this.item.id;
    console.log(this.answer);
    this.services.saveAnswer(this.answer).subscribe({
      next: (v) => {
        console.log(v);
        if(v){
          this.modalService.dismissAll();
          this.messageService.add({
            severity: 'success',
            summary: 'Se ha agregado la respuesta',
            
           });
           setTimeout(() => {
           window.location.reload();
         }, 1000);
        }        
      },
      error: (e) => {  
      console.log(e);
      this.messageService.add({
        severity: 'error',
        summary: 'Rectifique los datos',
        detail: '(Campos Vacios)-Intente de Nuevo',
      })},
      complete: () => console.info('complete'),
    });
  
  }
}

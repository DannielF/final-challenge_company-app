import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { MessageService } from 'primeng/api';
import { answe } from 'src/app/models/answe';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';


@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css'],
  providers: [MessageService],
})
export class QuestionComponent implements OnInit {
  
  answers: AnswerI[] | undefined;
  question: QuestionI = {
    id:
      this.authService.userData.uid == undefined
        ? ''
        : this.authService.userData.uid,
    userId:
      this.authService.userData.uid == undefined
        ? ''
        : this.authService.userData.uid,
    question: '',
    type: '',
    category: '',
    answers:[]
  };

  constructor(
    private modalService: NgbModal,
    private authService: ServiceService,
    private services: QuestionService,
    private toastr: ToastrService,
    private route: Router,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  saveQuestion(question: QuestionI): void {
    console.log(question);
    if(question.type && question.category){    
     this.modalService.dismissAll();
     this.services.saveQuestion(question).subscribe({
       next: (v) => { 
        console.log(v)      
         if (v) {
           this.messageService.add({
             severity: 'success',
             summary: 'Se ha agregado la pregunta',
             
            });
            setTimeout(() => {
            window.location.reload();
          }, 2000);
        } else {
          
        }
      },
      error: (e) =>
      this.toastr.error(e.mesaje, 'Fail', {
        timeOut: 3000,
      }),
      complete: () => console.info('complete'),
    });
  }else{
   
    this.messageService.add({
      severity: 'error',
      summary: 'Rectifique los datos',
      detail: '(Campos Vacios)-Intente de Nuevo',
    });
  }
  }
}

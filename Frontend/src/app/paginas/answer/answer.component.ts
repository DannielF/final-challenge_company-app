import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionService } from 'src/app/Service/question.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';
import { AngularFireAuth } from '@angular/fire/compat/auth';

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.scss'],
  providers: [MessageService],
})
export class AnswerComponent implements OnInit {
  public form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(10)]],
    rating: ['', []],
  });

  userEmail: string | null | undefined;

  @Input() item: any;
  constructor(
    private modalService: NgbModal,
    private services: QuestionService,
    private toastr: ToastrService,
    private route: Router,
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    public authService: ServiceService,
    private afAuth: AngularFireAuth
  ) {}

  answer: AnswerI = {
    userId: '',
    questionId: '',
    answer: '',
    position: 0,
  };

  ngOnInit(): void {
    this.afAuth.currentUser.then((user) => {
      if(user?.email == undefined) {
        this.route.navigate(['preguntas'])
      } else {
        this.userEmail = user.email;
      }
    });
  }

  openVerticallyCentered(content: any) {
    this.modalService.open(content, { centered: true });
  }

  saveAnswer(): void {
    this.answer.userId = this.item.userId;
    this.answer.questionId = this.item.id;
    this.afAuth.currentUser.then((user) => {
        this.userEmail = user?.email;
      });
    this.services.saveAnswer(this.answer, this.userEmail).subscribe({
      next: (v) => {
        if (v) {
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
        });
      },
      complete: () => console.info('complete'),
    });
  }
}

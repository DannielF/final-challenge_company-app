import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';
import { ToastrService } from 'ngx-toastr';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';

@Component({
  selector: 'app-requestion',
  templateUrl: './requestion.component.html',
  styleUrls: ['./requestion.component.scss']
})
export class RequestionComponent implements OnInit {
  
  question:QuestionI | undefined;
  questions: QuestionI[] | undefined;
  answers: AnswerI[] | undefined;
  answersNew: AnswerI[]=[];
  page: number = 0;
  
  answer: AnswerI = {
    userId: '',
    questionId: '',
    answer: '',
    position: 0,
  };
  
  mean:number = 0;
  userIdQuestion:string = "";
  currentAnswer:number=0;
  id:string | undefined;

  constructor(
    
    private modalService: NgbModal,
    private route:ActivatedRoute,
    private questionService:QuestionService,
    private service: QuestionService,
    private authService: ServiceService,
    private toastr:ToastrService,
    private router:Router
    ) {
      
      this.userIdQuestion = this.route.snapshot.params['id'];
    }

  ngOnInit(): void {
    
    const id = this.route.snapshot.paramMap.get('id');
    this.getQuestions(`${id}`);
    this.get2();
    this.meanAnswer(`${id}`);
  }

  openVerticallyCentered(content: any) {

    this.modalService.open(content, { centered: true });
  }
  
  get2(){

    let id = this.route.snapshot.paramMap.get('id');
    this.service.getAnswer(id).subscribe((data) => {  
          this.answers = data.answers;
    });
  }

  getQuestions(id:string):void{

    this.questionService.getQuestion(id).subscribe(data=>{
      this.question=data;
      this.answers = data.answers;
      this.answers.sort((a, b) => (a.position > b.position ? -1 : 1));
    })
  }

  AddAnwsers(index:number) {

    let last=this.currentAnswer+index;
    for(let i = this.currentAnswer;i<last;i++){
    }
    this.currentAnswer+=10;
  }

  updateAnswer(answer:AnswerI){

    this.service.updateAnswer(answer).subscribe(
      response => {
        this.toastr.success('Respuesta Actualizada', 'OK', {
          timeOut: 3000, positionClass: 'toast-top-center'
        });
        this.reloadCurrentPage();
      },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
      }
    );
  }

  eliminar(id:any) {

    this.questionService.deleteAnswerById(id).subscribe(
      response => {
        this.toastr.success("Respuesta eliminado", 'OK',{
          timeOut: 3000,
          positionClass: 'toast-top-center'
        });
        this.reloadCurrentPage();
    },
      err => {
        this.toastr.error(err.error.mensaje, 'Fail', {
          timeOut: 3000,  positionClass: 'toast-top-center',
        });
      })
  }

  reloadCurrentPage() {

    window.location.reload();
   }

  meanAnswer(id:string) {

    this.questionService.getQuestion(id).subscribe((question) => {
      this.userIdQuestion = question.userId;
      let answers = question.answers;
      let sum = 0;
      for(let index=0; index<answers.length; index++) {
        let answer = answers[index];
        sum = sum + answer['position'];
      }
      let mean= (sum/answers.length).toFixed(0);
      this.mean = parseInt(mean);
      if (Number.isNaN(this.mean)) {
        this.mean = 0;
      } else {

      }
    });
  }

  onScroll() {

  }

}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AnswerI } from 'src/app/models/answer-i';
import { QuestionI } from 'src/app/models/question-i';
import { QuestionService } from 'src/app/Service/question.service';
import { ServiceService } from 'src/app/Service/service.service';

@Component({
  selector: 'app-requestion',
  templateUrl: './requestion.component.html',
  styleUrls: ['./requestion.component.scss']
})
export class RequestionComponent implements OnInit {
  
  question:QuestionI | undefined;
  answers: AnswerI[] | undefined;
  answersNew: AnswerI[]=[];
  currentAnswer:number=0;

  mean:number = 0;

  questions: QuestionI[] | undefined;
 
  page: number = 0;

  constructor(
    private route:ActivatedRoute,
    private questionService:QuestionService,
    private service: QuestionService,
    public authService: ServiceService

    ) {

    }

  id:string | undefined;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    this.getQuestions(`${id}`);
    this.get2();
    this.meanAnswer(`${id}`)
    console.log(id);
    
       
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

  meanAnswer(id:string) {
    this.questionService.getQuestion(id).subscribe((question) => {
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
      
      
    })
  }

  onScroll() {

  }

}

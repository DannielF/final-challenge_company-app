import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { ToastrService } from 'ngx-toastr';
import { FirebaseCodeErrorService } from 'src/app/Service/firebase-code-error.service';
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [MessageService],
})

export class LoginComponent implements OnInit {
  
  /*mostrar: Boolean = false;
  mostrar2: Boolean = false;
  val1: number = 3;
  displayModal: boolean = false;
  email: any = '';*/

  loginUsuario:FormGroup;
  loading:boolean = false;

  /*public form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(10)]],
    rating: ['', []],
  });
  public form2: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
  });*/

  constructor(
    
    /*private formBuilder: FormBuilder,
    private messageService: MessageService,
    private authService: ServiceService,
    private route: Router*/

    private fb:FormBuilder, 
    private afAuth:AngularFireAuth, 
    private toastrService:ToastrService, 
    private router:Router,
    private firebaseErrorService:FirebaseCodeErrorService,
  ) {

    this.loginUsuario = this.fb.group({
      email: ["",[ Validators.required, Validators.email]],
      password: ["", Validators.required],
    })
  }

  ngOnInit(): void {}

  /*ingresar() {
    this.mostrar = !this.mostrar;
    this.authService
      .login(this.form.value.email, this.form.value.password)
      .then((res) => {       
        if (res == undefined) {
          this.messageService.add({
            severity: 'error',
            summary: 'Rectifique los datos',
            detail: 'Clave o Usuario incorrecto, Intente de Nuevo',
          });
        } else {
          this.messageService.add({
            severity: 'success',
            summary: 'Bienvenido',
            detail: 'Disfruta de tu estadía',
          });
          this.route.navigate(['preguntas']);
        }

        this.mostrar = !this.mostrar;
      });
  }*/

  login() {
    const email = this.loginUsuario.value.email;
    const password = this.loginUsuario.value.password;

    this.loading = true;
    this.afAuth.signInWithEmailAndPassword(email, password).then((user) => {
      if(user.user?.emailVerified){
        this.router.navigate(["/dashboard"]);
      } else {
        this.router.navigate(["/registro"])
      }
      
    }).catch((error) => {
      this.loading = false;
      this.toastrService.error(this.firebaseErrorService.codeError(error.code), "Error");
    });    
  }

  loginGoogle() {
 
    this.afAuth.signInWithPopup(new GoogleAuthProvider()).then(() => {
      this.router.navigate(["preguntas"]);
    })
    
  }

   /*ingresarGoogle() {
    this.mostrar = !this.mostrar;       
    this.authService
      .loginGoogle(this.form.value.email, this.form.value.password)
      .then((res) => {
        if (res) {
          this.messageService.add({
            severity: 'success',
            summary: 'Bienvenido',
            detail: 'Disfruta de tu estadía',
          });
          setTimeout(() => {
            this.route.navigate(['preguntas']);
          }, 3000);

        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Rectifique los datos',
            detail: 'Clave o Usuario incorrecto, Intente de Nuevo',
          });
          
        }
        this.mostrar = !this.mostrar;
      });
  }
  getUserLogged() {
    this.authService.getUserLogged().subscribe((res) => {     
    });
  }
 
  preguntasHome() {
    this.route.navigate(['preguntas']);
  }

  //TODO: Utilidades
  showSuccess() {
    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Message Content',
    });
  }
  showInfo() {
    this.messageService.add({
      severity: 'info',
      summary: 'Info',
      detail: 'Message Content',
    });
  }
  showError() {}
  spinner() {
    this.mostrar = !this.mostrar;
  }

  showModalDialog() {
    this.displayModal = true;
  }

  recuperarEmail() {
    try {
      this.mostrar2 = !this.mostrar2;
      this.authService.resetPassword(this.form2.value.email).then((res) => {
        this.displayModal = false;
        this.messageService.add({
          severity: 'success',
          summary: '!Exitoso¡',
          detail: 'Revisa tu bandeja de entrada',
        });
      });
      this.mostrar2 = !this.mostrar2;
    } catch (error) {}
  }*/
}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService, Message } from 'primeng/api';
import { ServiceService } from 'src/app/Service/service.service';
import {AngularFireAuth} from '@angular/fire/compat/auth';
import { ToastrService } from 'ngx-toastr';
import { FirebaseCodeErrorService } from 'src/app/Service/firebase-code-error.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss'],
  providers: [MessageService],
})
export class RegistroComponent implements OnInit {
  
  /*mostrar: Boolean = false;
  val1: number = 3;*/

  registrarUsuario: FormGroup;
  loading:boolean = false;

  /*public form: FormGroup = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    rating: ['', []],
  }); */

  constructor(
    
    /*private formBuilder: FormBuilder,
    private messageService: MessageService,
    private authService: ServiceService,
    private route: Router*/

    private fb:FormBuilder, 
    private afAuth:AngularFireAuth, 
    private toastrService:ToastrService, 
    private router:Router, 
    private firebaseErrorService:FirebaseCodeErrorService
  ) {

    this.registrarUsuario = this.fb.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(6)]],
      repetirPassword: ["", Validators.required]
    });
  }

  ngOnInit(): void {}

  registrar() {
    const email = this.registrarUsuario.value.email;
    const password = this.registrarUsuario.value.password;
    const repetirPassword = this.registrarUsuario.value.repetirPassword;
    if (password != repetirPassword) {
      this.toastrService.error("Las contraseñas deben ser las mismas", "Error");
      return;
    }

    this.loading = true;
    this.afAuth.createUserWithEmailAndPassword(email, password)
    .then(() => {
      this.verificarCorreo();
    }).catch((error) => {
      this.loading = false;
      this.toastrService.error(this.firebaseErrorService.codeError(error.code), 'Error');
    });
  }

  verificarCorreo() {
    this.afAuth.currentUser
    .then(user => user?.sendEmailVerification())
    .then(() => {
      this.toastrService.info("Le enviamos un correo electrónico para su verificación", "Verificar Correo")
      this.router.navigate(["/login"]);
    })
  }

  /*ingresar() {
    this.mostrar = !this.mostrar;    
    this.authService
      .loginRegistre(this.form.value.email, this.form.value.password)
      .then((res) => {       
        if (res) {
          this.messageService.add({
            severity: 'success',
            summary: '!Exitoso¡',
            detail: 'Usuario Almacenado correctamente',
          });
          setTimeout(() => {
            this.route.navigate(['preguntas']);
          }, 2000);
        } else {
          this.messageService.add({
            severity: 'error',
            summary: 'Usuarios Registrado',
            detail: 'Por favor intente con otro correo',
          });
        }

        this.mostrar = !this.mostrar;
      });
  }
  ingresarGoogle() {
    this.mostrar = !this.mostrar;    
    this.authService
      .loginGoogle(this.form.value.email, this.form.value.password)
      .then((res) => {
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

  spinner() {
    this.mostrar = !this.mostrar;
  }*/
}

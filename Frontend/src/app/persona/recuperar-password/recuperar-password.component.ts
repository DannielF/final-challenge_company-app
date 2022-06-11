import { Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FirebaseCodeErrorService } from 'src/app/Service/firebase-code-error.service';

@Component({
  selector: 'app-recuperar-password',
  templateUrl: './recuperar-password.component.html',
  styleUrls: ['./recuperar-password.component.scss']
})
export class RecuperarPasswordComponent implements OnInit {

  recuperarUsuario: FormGroup;
  loading:boolean = false;

  constructor(

    private fb:FormBuilder, 
    private afAuth:AngularFireAuth, 
    private toastrService:ToastrService, 
    private router:Router,
    private firebaseErrorService:FirebaseCodeErrorService) { 

    this.recuperarUsuario = this.fb.group({
      correo: ["", [Validators.required, Validators.email]]
    })
  }

  ngOnInit(): void {
  }

  recuperar() {
    const email = this.recuperarUsuario.value.correo;

    this.loading = true;
    this.afAuth.sendPasswordResetEmail(email).then(() => {
      this.toastrService.info("Le enviamos un correo para restablecer su password", "Recuperar password")
      this.router.navigate(["/login"]);
    }).catch((error)=> {
      this.loading = false;
      this.toastrService.error(this.firebaseErrorService.codeError(error.code), "Error");
    });
    
  }
}

import { Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import { windowCount } from 'rxjs';
import { ServiceService } from '../Service/service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
 
  /*userLogged = this.authService.getUserLogged();
  disabled: boolean = false;*/
  
  dataUser:any;
  

  constructor(
    private authService: ServiceService, 
    private afAuth: AngularFireAuth,
    private router: Router) {}

  ngOnInit(): void {
    this.afAuth.currentUser.then((user) => {
      if(user && user.emailVerified) {
        console.log(user)
        this.dataUser = user;

      } else {
        //window.location.reload;
      }
    })
  }

  /*traerdatos() {
    this.userLogged.subscribe((value) => {    
      if (value?.email == undefined) {
        this.disabled = true;        
      } else {
        this.disabled = false;       
      }
    });
  }*/

  logOut() {
    this.afAuth.signOut();
    window.location.reload();
    
  }  
}

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UserRegistering as UserRegisteringService } from '../../Services/user-registering.service';

@Component({
  selector: 'app-login-component',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.scss',
})
export class LoginComponent {
  showLogin: boolean = true;
  email: string = '';
  password: string = '';
  constructor(private router: Router, private userService: UserRegisteringService) {}
  login(form: any) {
    if (form.valid) {
      this.userService.authenticateUser(this.email,this.password).subscribe({
        next:(token)=>{
          this.userService.storeToken(token);
          alert('Login successful!');
          form.reset();
          this.router.navigate(['/home']);
        },error:(err)=>{
          console.error('Login error:',err);
          alert('Login failed. Please check your credentials.');
        }
      });
    }else{
      console.error('Please fill all required fields correctly.');  
    }
  }
}
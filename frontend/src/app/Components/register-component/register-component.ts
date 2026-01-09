import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserRegistering as UserRegisteringService } from '../../Services/user-registering.service';

@Component({
  selector: 'app-register-component',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register-component.html',
  styleUrls: ['./register-component.scss'],
})
export class RegisterComponent {
  showLogin = false;
  name: string = '';
  email: string = '';
  password: string = '';

  constructor(private router: Router, private userService: UserRegisteringService) {}

  toggleLogin() {
    this.showLogin = !this.showLogin;
  }

  register(form: any) {
    if (form.valid) {
      if (this.password.length < 6) {
        alert('Password must be at least 6 characters');
        return;
      }
      
      const payload = {
        email: this.email,
        password: this.password,
        name: this.name
      };

      this.userService.registerUser(payload).subscribe({
        next: (response) => {
          alert('Registration successful! Please log in.');
          form.reset();
          this.router.navigate(['/login']);
        },
        error: (err) => {
          if (err.status === 405) {
            alert('User with this email already exists');
          } else {
            alert('Registration failed: ' + (err.error || err.message));
          }
          console.error('Registration error:', err);
        }
      });
    } else {
      console.error('Please fill all required fields correctly.');
    }
  }
}

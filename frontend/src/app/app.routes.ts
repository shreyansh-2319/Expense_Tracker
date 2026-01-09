import { Routes } from '@angular/router';
import { LoginComponent } from './Components/login-component/login-component';
import { RegisterComponent } from './Components/register-component/register-component';
import { ExpenseFormComponent } from './Components/expense-form-component/expense-form-component';
import { ExpenseListComponent } from './Components/expense-list-component/expense-list-component';
import { ExpenseEditComponent } from './Components/expense-edit-component/expense-edit-component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'expenses', component: ExpenseListComponent },
    { path: 'expenses/add', component: ExpenseFormComponent },
    { path: 'expenses/update/:id', component: ExpenseEditComponent }
];

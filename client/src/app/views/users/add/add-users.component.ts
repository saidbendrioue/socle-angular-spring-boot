import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UsersService } from '../../../services/users.service';
import { Router } from '@angular/router';
import { DataService } from '../../../services/data.service';

@Component({
  selector: 'app-users',
  templateUrl: 'add-users.component.html'
})
export class AddUsersComponent {

  editMode: boolean = false;
  submitted: boolean = false;
  userForm: FormGroup;


  constructor(private router: Router, private fb: FormBuilder,
    private _dataService: DataService, private _usersService: UsersService) {

  }

  ngOnInit() {
    this.userForm = this.fb.group({
      id: [''],
      lastName: ['', Validators.required],
      firstName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required]
    });
    this.initForm();
  }

  initForm() {
    let user = this._dataService.getData();

    if (user) {
      this.editMode = true;

      this.userForm.setValue({
        id: user.id,
        lastName: user.lastName,
        firstName: user.firstName,
        email: user.email,
        role: user.role
      });
    }
  }

  onFormSubmit() {
    this.submitted = true;

    if (this.userForm.valid) {
      this._usersService.storeUser(this.userForm.value).subscribe(() => {
        this.router.navigate(['/users/list']);
      });
    }
  }

  get lastName() {
    return this.userForm.get('lastName');
  }

  get firstName() {
    return this.userForm.get('firstName');
  }

  get email() {
    return this.userForm.get('email');
  }

  get role() {
    return this.userForm.get('role');
  }
}

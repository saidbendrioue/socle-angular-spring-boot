import { Component, ViewChild } from '@angular/core';
import { User } from '../../../model/user.model';

import { LazyLoadEvent } from 'primeng/components/common/lazyloadevent';
import { UsersService } from '../../../services/users.service';
import { Table } from 'primeng/table';
import { SweetAlertService } from 'angular-sweetalert-service/js';
import { DataService } from '../../../services/data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: 'users.component.html',
  styles: ['.badge { margin-right: 3px; }']
})
export class UsersComponent {

  users: User[] = [];
  totalElements: number = 0;
  cols: any[];

  @ViewChild(Table) table: Table;

  public constructor(private router: Router, private _usersService: UsersService, private _dataService: DataService, private _alert: SweetAlertService) {
  }

  ngOnInit() {
    this.cols = [
      { field: 'firstName', header: 'Prenom' },
      { field: 'lastName', header: 'Nom' },
      { field: 'email', header: 'Email' },
      { field: 'role', header: 'Type de collaborateur' },
    ];
    this.loadUsersLazy({});
  }
  loadUsersLazy(event: LazyLoadEvent) {
    if (!event.sortField) { event.sortField = 'lastName'; }

    this._usersService.loadUsers(event).subscribe((data: any) => {
      this.users = data.content;
      this.totalElements = data.totalElements;
    });
  }

  editUser(user: User) {
    this._dataService.setData(user);
    this.router.navigate(['/users/add']);
  }

  deleteUser(user: User) {
    this._alert.confirm({
      showCloseButton: true,
      confirmButtonText:
        '<i class="fa fa-trash"></i> Supprimer',
      showCancelButton: false,
      confirmButtonColor: '#f86c6b',
      title: `Voulez-vous supprimer l'utilisateur « ${user.firstName} ${user.lastName}» ?`
    })
      .then((res) => {
        if (res.value) {
          this._usersService.deleteUser(user.id).subscribe(() => {
            this._alert.info({ title: 'Succès !', text: `Le collaborateur « ${user.firstName} ${user.lastName} » a été bien supprimé`, showConfirmButton: false, timer: 1500 });
            this.table.reset();
          });
        }
      });
  }
}

import { AddUserPage } from './AddUserPage';

export class EditUserPage extends AddUserPage {
    constructor(userId: string) {
        super(`/loadUser.do?OperationType=edit&userID=${userId}`);
    }
}

import BasePage from '../../BasePage';

export default class ManageUsersPage extends BasePage {
    constructor() {
        super('/userList.do');
    }
}

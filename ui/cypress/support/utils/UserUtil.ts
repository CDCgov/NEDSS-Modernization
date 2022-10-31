import User from '../models/User';
import ManageUsersPage from '../pages/system-management/security-management/ManageUsersPage';
import { AddUserPage } from '../pages/system-management/security-management/AddUserPage';
import { EditUserPage } from '../pages/system-management/security-management/EditUserPage';

export default class UserUtil {
    private static defaultOptions = { log: Cypress.env('detailedLogs') };

    public static login(user: User): Cypress.Chainable {
        const loginAttempt = this.doLogin(user);
        return cy.document(this.defaultOptions).then((doc) => {
            const nav = doc.getElementsByClassName('nedssNavTable');
            if (nav.length === 0) {
                // try to login again if it failed.
                return this.doLogin(user);
            } else {
                return loginAttempt;
            }
        });
    }

    private static doLogin(user: User): Cypress.Chainable<Cypress.AUTWindow> {
        return cy.visit(`/nfc?UserName=${user.userId}`, this.defaultOptions);
    }

    public static logout(): void {
        cy.visit(`/logOut`, this.defaultOptions);
    }

    public static getUserState(user: User): Cypress.Chainable<'Active' | 'Inactive' | 'Null'> {
        const manageUsersPage = new ManageUsersPage();
        manageUsersPage.navigateTo();
        return cy
            .get('table[class=TableInner]', this.defaultOptions)
            .get('a', this.defaultOptions)
            .then((links) => {
                for (let link of links) {
                    if (link.text === user.userId) {
                        const active = link.parentElement?.nextSibling?.textContent === 'Active';
                        return active ? 'Active' : 'Inactive';
                    }
                }
                return 'Null';
            });
    }

    public static createOrActivateUser(user: User): void {
        const manageUsersPage = new ManageUsersPage();
        manageUsersPage.navigateTo();
        this.getUserState(user).then((userState) => {
            if (userState === 'Inactive') {
                const editUserPage = new EditUserPage(user.userId);
                editUserPage.navigateTo();
                editUserPage.setIsActive(true);
                editUserPage.clickSubmit();
            }
            if (userState === 'Null') {
                // create user
                const addUserPage = new AddUserPage();
                addUserPage.navigateTo();
                addUserPage.setUserId(user.userId);
                addUserPage.setFirstName(user.firstName);
                addUserPage.setLastName(user.lastName);
                user.roles.forEach((r) => addUserPage.addRole(r));
                addUserPage.clickSubmit();
            }
        });
    }

    public static deactivateUser(user: User): void {
        const editUserPage = new EditUserPage(user.userId);
        editUserPage.navigateTo();
        editUserPage.setIsActive(false);
        editUserPage.clickSubmit();
    }
}

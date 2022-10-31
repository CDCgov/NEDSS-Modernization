/// <reference types="cypress" />

import { Role } from '../../../models/User';
import BasePage from '../../BasePage';

enum Selector {
    USER_ID = 'input[id=userProfile\\.theUser_s\\.userID]',
    FIRST_NAME = 'input[id=userProfile\\.theUser_s\\.firstName]',
    LAST_NAME = 'input[id=userProfile\\.theUser_s\\.lastName]',
    STATUS_ACTIVE_RADIO = 'input[id=ACTIVE]',
    STATUS_INACTIVE_RADIO = 'input[id=INACTIVE]',
    EXTERNAL_CHECKBOX = 'input[name=userProfile\\.theUser_s\\.userType]',
    MASTER_SECURITY_ADMIN = 'input[name=userProfile\\.theUser_s\\.msa]',
    PROGRAM_AREA_ADMIN = 'input[name=userProfile\\.theUser_s\\.paa]',
    ROLE_JURISDICTION = 'input[name=userProfile\\.theRealizedRole_s\\[i\\]\\.jurisdictionCode_textbox]',
    ROLE_PROGRAM_AREA = 'input[name=userProfile\\.theRealizedRole_s\\[i\\]\\.programAreaCode_textbox]',
    ROLE_PERMISSION_SET = 'input[name=userProfile\\.theRealizedRole_s\\[i\\]\\.roleName_textbox]',
    ROLE_GUEST_CHECKBOX = 'input[name=userProfile\\.theRealizedRole_s\\[i\\]\\.guestString]',
    ROLE_ADD_ROLE_BTN = 'input[id=BatchEntryAddButtonRole]',
    SUBMIT_BUTTON = 'input[id=Submit]'
}

export class AddUserPage extends BasePage {
    constructor(relativeUrl = '/loadUser.do?OperationType=create') {
        super(relativeUrl);
    }

    setUserId(userId: string): void {
        this.setText(Selector.USER_ID, userId);
    }

    setFirstName(firstName: string): void {
        this.setText(Selector.FIRST_NAME, firstName);
    }

    setLastName(lastName: string): void {
        this.setText(Selector.LAST_NAME, lastName);
    }

    setIsActive(isActive: boolean): void {
        if (isActive) {
            this.click(Selector.STATUS_ACTIVE_RADIO);
        } else {
            this.click(Selector.STATUS_INACTIVE_RADIO);
        }
    }

    setIsExternal(isExternal: boolean): void {
        this.setChecked(Selector.EXTERNAL_CHECKBOX, isExternal);
    }

    setReportingFacility(facility: string): void {
        throw new Error('setReportingFacility NYI');
    }

    setProgramArea(programArea: string): void {
        throw new Error('setProgramArea NYI');
    }

    addRole(role: Role): void {
        this.setText(Selector.ROLE_JURISDICTION, role.jurisdiction);
        this.setText(Selector.ROLE_PROGRAM_AREA, role.programArea);
        this.setText(Selector.ROLE_PERMISSION_SET, role.permissionSet.toString());
        this.setChecked(Selector.ROLE_GUEST_CHECKBOX, role.isGuest);
        this.click(Selector.ROLE_ADD_ROLE_BTN);
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }
}

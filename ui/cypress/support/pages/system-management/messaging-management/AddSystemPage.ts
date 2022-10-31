import System from '../../../models/System';
import BasePage from '../../BasePage';

enum Selector {
    REPORT_TYPE = 'input[name=reportTypeField_textbox]',
    DISPLAY_NAME = 'input[name=selection\\.receivingSystemShortName]',
    APPLICATION_NAME = 'input[name=selection\\.receivingSystemNm]',
    APPLICATION_OID = 'input[name=selection\\.receivingSystemOid]',
    FACILITY_NAME = 'input[name=selection\\.receivingSystemOwner]',
    FACILITY_OID_IDENTIFIER = 'input[name=selection\\.receivingSystemOwnerOid]',
    FACILITY_DESCRIPTION = 'textarea[name=selection\\.receivingSystemDescTxt]',
    SENDING_SYSTEM = 'input[name=sendSys_textbox]',
    RECEIVING_SYSTEM = 'input[name=receivSys_textbox]',
    ALLOWS_TRANSFERS = 'input[name=allowTransfer_textbox]',
    USE_SYSTEM_DERIVED_JURISDICTION = 'input[name=jurDeriveIndCd_textbox]',
    ADMINISTRATIVE_COMMENTS = 'textarea[name=selection\\.adminComment]',
    SUBMIT_BUTTON = 'input[name=submitCrSub]'
}
export default class AddSystemPage extends BasePage {
    constructor() {
        super('/ReceivingSystem.do?method=createLoadRecFacility&exportReceivingFacilityUid#expAlg');
    }

    setReportType(reportType: 'Case Report' | 'Laboratory Report'): void {
        this.setText(Selector.REPORT_TYPE, reportType);
    }

    setDisplayName(displayName: string): void {
        this.setText(Selector.DISPLAY_NAME, displayName);
    }

    setApplicationName(applicationName: string): void {
        this.setText(Selector.APPLICATION_NAME, applicationName);
    }

    setApplicationOid(applicationOid: string): void {
        this.setText(Selector.APPLICATION_OID, applicationOid);
    }

    setFacilityName(facilityName: string): void {
        this.setText(Selector.FACILITY_NAME, facilityName);
    }

    setFacilityOid(facilityOid: string): void {
        this.setText(Selector.FACILITY_OID_IDENTIFIER, facilityOid);
    }

    setFacilityDescription(facilityDescription: string): void {
        this.setText(Selector.FACILITY_DESCRIPTION, facilityDescription);
    }

    setSendingSystem(sendingSystem: 'No' | 'Yes'): void {
        this.setText(Selector.SENDING_SYSTEM, sendingSystem);
    }

    setReceivingSystem(receivingSystem: 'No' | 'Yes'): void {
        this.setText(Selector.RECEIVING_SYSTEM, receivingSystem);
        this.blur(Selector.RECEIVING_SYSTEM);
    }

    setAllowsTransfers(allowsTransfers: 'No' | 'Yes'): void {
        this.setText(Selector.ALLOWS_TRANSFERS, allowsTransfers);
    }

    setUseSystemDerivedJurisdiction(useSystemDerivedJurisdiction: 'No' | 'Yes'): void {
        this.setText(Selector.USE_SYSTEM_DERIVED_JURISDICTION, useSystemDerivedJurisdiction);
    }

    setAdministrativeComments(administrativeComments: string): void {
        this.setText(Selector.ADMINISTRATIVE_COMMENTS, administrativeComments);
    }

    createSystem(system: System): void {
        this.setReportType(system.reportType);
        this.setDisplayName(system.displayName);
        this.setApplicationName(system.applicationName);
        this.setApplicationOid(system.applicationOid);
        this.setFacilityName(system.facilityName);
        this.setFacilityOid(system.facilityOid);
        if (system.facilityDescription) {
            this.setFacilityDescription(system.facilityDescription);
        }
        this.setSendingSystem(system.sendingSystem);
        // 'receiving system' and 'use system derived jurisdiction' are disabled unless reportType === 'Case Report'
        if (system.reportType === 'Case Report') {
            this.setReceivingSystem(system.receivingSystem ?? 'No');
            // allows transfers disabled unless receiving system is 'Yes'
            if (system.receivingSystem === 'Yes') {
                this.setAllowsTransfers(system.allowsTransfers ?? 'No');
            }
            this.setUseSystemDerivedJurisdiction(system.useSystemDerivedJurisdiction ?? 'No');
        }
        if (system.administrativeComments) {
            this.setAdministrativeComments(system.administrativeComments);
        }
        this.clickSubmitButton();
    }

    clickSubmitButton(): void {
        this.click(Selector.SUBMIT_BUTTON);
    }
}

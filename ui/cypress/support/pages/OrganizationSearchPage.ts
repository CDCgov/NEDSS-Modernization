import { AddressOperator, NameOperator } from '../models/enums/Operators';
import { OrganizationIdentificationType } from '../models/enums/OrganizationIdentificationType';
import { State } from '../models/enums/State';
import BasePage from './BasePage';
import OrganizationSearchResultsPage from './OrganizationSearchResultsPage';
enum Selector {
    NAME_OPERATOR = 'input[name=organizationSearch\\.nmTxtOperator_textbox]',
    NAME = 'input[id=organizationSearch\\.nmTxt]',
    STREET_OPERATOR = 'input[name=organizationSearch\\.streetAddr1Operator_textbox]',
    STREET = 'input[id=organizationSearch\\.streetAddr1]',
    CITY_OPERATOR = 'input[name=organizationSearch\\.cityDescTxtOperator_textbox]',
    CITY = 'input[id=organizationSearch\\.cityDescTxt]',
    STATE = 'input[name=organizationSearch\\.stateCd_textbox]',
    ZIP = 'input[id=organizationSearch\\.zipCd]',
    PHONE_1 = 'input[id=organizationSearch\\.phoneNbrTxt1]',
    PHONE_2 = 'input[id=organizationSearch\\.phoneNbrTxt2]',
    PHONE_3 = 'input[id=organizationSearch\\.phoneNbrTxt3]',
    ID_TYPE = 'input[name=organizationSearch\\.typeCd_textbox]',
    ID_VALUE = 'input[id=organizationSearch\\.rootExtensionTxt]',
    SUBMIT_BUTTON = 'input[name=Submit]'
}
export default class OrganizationSearchPage extends BasePage {
    constructor() {
        super('/MyTaskList1.do?ContextAction=GlobalOrganization');
    }

    setNameOperator(nameOperator: NameOperator): void {
        this.setText(Selector.NAME_OPERATOR, nameOperator);
    }

    setName(name: string): void {
        this.setText(Selector.NAME, name);
    }

    setStreetAddressOperator(streetAddressOperator: AddressOperator): void {
        this.setText(Selector.STREET_OPERATOR, streetAddressOperator);
    }

    setStreetAddress(streetAddress: string): void {
        this.setText(Selector.STREET, streetAddress);
    }

    setCityOperator(cityOperator: AddressOperator): void {
        this.setText(Selector.CITY_OPERATOR, cityOperator);
    }

    setCity(city: string): void {
        this.setText(Selector.CITY, city);
    }

    setState(state: State): void {
        this.setText(Selector.STATE, state);
    }

    setZip(zip: string): void {
        this.setText(Selector.ZIP, zip);
    }

    setTelephone(telephone: string): void {
        // MUST be 123-456-1234 format
        const sections = telephone.split('-');
        if (sections.length != 3) {
            throw new Error('Telephone number must be in 123-123-1234 format');
        }
        this.setText(Selector.PHONE_1, sections[0]);
        this.setText(Selector.PHONE_2, sections[2]);
        this.setText(Selector.PHONE_3, sections[3]);
    }

    setIdentificationType(idType: OrganizationIdentificationType): void {
        this.setText(Selector.ID_TYPE, idType);
    }

    clickSearch(): Cypress.Chainable<OrganizationSearchResultsPage> {
        return this.clickFirst(Selector.SUBMIT_BUTTON).then(() => {
            return new OrganizationSearchResultsPage();
        });
    }
}

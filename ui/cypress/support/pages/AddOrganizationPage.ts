import { OrganizationRole } from '../models/enums/OrganizationRole';
import { StandardIndustryClass } from '../models/enums/StandardIndustryClass';
import { Address, Identification, TelephoneInformation } from '../models/Organization';
import BasePage from './BasePage';

enum Selector {
    QUICK_CODE = 'input[name=quickCodeIdDT\\.rootExtensionTxt]',
    STANDARD_INDUSTRY_CLASS = 'input[name=organization\\.theOrganizationDT\\.standardIndustryClassCd_textbox]',
    ROLE_LIST = 'select[id=rolesList]',
    COMMENTS = 'textarea[id=organization\\.theOrganizationDT\\.description]',
    ORGANIZATION_NAME = 'input[id=name\\.nmTxt]',
    IDENTIFICATION_TYPE = 'input[name=organization\\.entityIdDT_s\\[i\\]\\.typeCd_textbox]',
    ASSIGNING_AUTHORITY = 'input[name=organization\\.entityIdDT_s\\[i\\]\\.assigningAuthorityCd_textbox]',
    ID_VALUE = 'input[id=organization\\.entityIdDT_s\\[i\\]\\.rootExtensionTxt]',
    ADD_IDENTIFICATION_BUTTON = 'input[id=BatchEntryAddButtonIdentification]',
    ADDRESS_USE = 'input[name=address\\[i\\]\\.useCd_textbox]',
    ADDRESS_TYPE = 'input[name=address\\[i\\]\\.cd_textbox]',
    STREET_ADDRESS_1 = 'input[id=address\\[i\\]\\.thePostalLocatorDT_s\\.streetAddr1]',
    STREET_ADDRESS_2 = 'input[id=address\\[i\\]\\.thePostalLocatorDT_s\\.streetAddr2]',
    CITY = 'input[id=address\\[i\\]\\.thePostalLocatorDT_s\\.cityDescTxt]',
    STATE = 'input[name=address\\[i\\]\\.thePostalLocatorDT_s\\.stateCd_textbox]',
    ZIP = 'input[name=address\\[i\\]\\.thePostalLocatorDT_s\\.zipCd]',
    COUNTY = 'input[name=address\\[i\\]\\.thePostalLocatorDT_s\\.cntyCd_textbox]',
    ADDRESS_COMMENTS = 'textarea[id=address\\[i\\]\\.locatorDescTxt]',
    ADD_ADDRESS_BUTTON = 'input[id=BatchEntryAddButtonAddress]',
    TELEPHONE_USE = 'input[name=telephone\\[i\\]\\.useCd_textbox]',
    TELEPHONE_TYPE = 'input[name=telephone\\[i\\]\\.cd_textbox]',
    TELEPHONE_COUNTRY_CODE = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.cntryCd]',
    TELEPHONE_1 = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.phoneNbrTxt1]',
    TELEPHONE_2 = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.phoneNbrTxt2]',
    TELEPHONE_3 = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.phoneNbrTxt3]',
    TELEPHONE_EXT = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.extensionTxt]',
    TELEPHONE_EMAIL = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.emailAddress]',
    TELEPHONE_URL = 'input[id=telephone\\[i\\]\\.theTeleLocatorDT_s\\.urlAddress]',
    TELEPHONE_COMMENTS = 'input[id=telephone\\[i\\]\\.locatorDescTxt]',
    ADD_TELEPHONE_BUTTON = 'input[id=BatchEntryAddButtonTelephone]',
    SUBMIT_BUTTON = 'input[id=Submit]'
}
export default class AddOrganizationPage extends BasePage {
    constructor() {
        super('/OrgSearchResults1.do?ContextAction=Add');
    }

    public navigateTo(): void {
        throw new Error(
            'Unable to navigate directly to add organization page. Please search for an organization first'
        );
    }

    setQuickCode(quickCode: string): void {
        this.setText(Selector.QUICK_CODE, quickCode);
    }

    setStandardIndustryClass(standardIndustryClass: StandardIndustryClass): void {
        this.setText(Selector.STANDARD_INDUSTRY_CLASS, standardIndustryClass);
    }

    setRoles(roles: OrganizationRole[]): void {
        this.select(Selector.ROLE_LIST, roles);
    }

    setComments(comments: string): void {
        this.setText(Selector.COMMENTS, comments);
    }

    setOrganizationName(organizationName: string): void {
        this.setText(Selector.ORGANIZATION_NAME, organizationName);
    }

    addIdentification(identification: Identification): void {
        this.setText(Selector.IDENTIFICATION_TYPE, identification.type);
        this.setText(Selector.ASSIGNING_AUTHORITY, identification.assigningAuthority);
        this.setText(Selector.ID_VALUE, identification.id);
        this.click(Selector.ADD_IDENTIFICATION_BUTTON);
    }

    addAddress(address: Address): void {
        this.setText(Selector.ADDRESS_USE, address.use);
        this.setText(Selector.ADDRESS_TYPE, address.type);
        this.setText(Selector.STREET_ADDRESS_1, address.streetAddress1);
        this.setText(Selector.STREET_ADDRESS_2, address.streetAddress2);
        this.setText(Selector.CITY, address.city);
        this.setText(Selector.STATE, address.state);
        this.setText(Selector.ZIP, address.zip);
        this.setText(Selector.COUNTY, address.county);
        this.setText(Selector.ADDRESS_COMMENTS, address.comments);
        this.click(Selector.ADD_ADDRESS_BUTTON);
    }

    addTelephoneInformation(telephoneInfo: TelephoneInformation): void {
        this.setText(Selector.TELEPHONE_USE, telephoneInfo.use);
        this.setText(Selector.TELEPHONE_TYPE, telephoneInfo.type);
        this.setText(Selector.TELEPHONE_COUNTRY_CODE, telephoneInfo.countryCode);
        if (telephoneInfo.telephone && telephoneInfo.telephone.trim().length > 0) {
            // telephone must be in 123-456-1234 format
            const splitPhoneNumber = telephoneInfo.telephone.split('-');
            if (splitPhoneNumber.length != 3) {
                throw new Error('Telephone must be in format 123-456-1234');
            }
            this.setText(Selector.TELEPHONE_1, splitPhoneNumber[0]);
            this.setText(Selector.TELEPHONE_2, splitPhoneNumber[1]);
            this.setText(Selector.TELEPHONE_3, splitPhoneNumber[2]);
        }
        this.setText(Selector.TELEPHONE_EXT, telephoneInfo.telephoneExtension);
        this.setText(Selector.TELEPHONE_EMAIL, telephoneInfo.email);
        this.setText(Selector.TELEPHONE_URL, telephoneInfo.url);
        this.setText(Selector.TELEPHONE_COMMENTS, telephoneInfo.comments);
        this.click(Selector.ADD_TELEPHONE_BUTTON);
    }

    clickSubmit(): void {
        this.clickFirst(Selector.SUBMIT_BUTTON);
    }
}

export type Pass = {
    name: string;
    description?: string;
    active: boolean;
    blockingCriteria: BlockingAttribute[];
};

export enum BlockingAttribute {
    FIRST_NAME = 'FIRST_NAME',
    LAST_NAME = 'LAST_NAME',
    DATE_OF_BIRTH = 'DATE_OF_BIRTH',
    SEX = 'SEX',
    STREET_ADDRESS = 'STREET_ADDRESS',
    ZIP = 'ZIP',
    EMAIL = 'EMAIL',
    PHONE = 'PHONE',
    IDENTIFIER = 'IDENTIFIER'
}

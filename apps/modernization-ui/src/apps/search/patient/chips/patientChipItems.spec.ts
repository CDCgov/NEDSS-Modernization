import { PersonFilter } from 'generated/graphql/schema';
import { SearchCriteria } from 'providers/SearchCriteriaContext';
import { patientChipItems } from './patientChipItems';

describe('patientChipItems', () => {
    const mockSearchCriteria: SearchCriteria = {
        programAreas: [],
        conditions: [],
        jurisdictions: [],
        userResults: [],
        outbreaks: [],
        ethnicities: [{ id: { code: '1' }, codeDescTxt: 'Ethnicity1' }],
        races: [{ id: { code: '2' }, codeDescTxt: 'Race1' }],
        identificationTypes: [{ id: { code: 'ID1' }, codeDescTxt: 'IdentificationType1' }],
        states: [{ value: 'NY', name: 'New York', abbreviation: 'NY' }]
    };

    it('should generate chip items correctly from person filters', () => {
        const mockPersonFilter: PersonFilter = {
            firstName: 'John',
            lastName: 'Doe',
            ethnicity: '1',
            race: '2',
            state: 'NY',
            identification: {
                identificationType: 'ID1',
                identificationNumber: '123456'
            },
            recordStatus: []
        };

        const expectedChipItems = [
            { name: 'FIRST', value: 'John', source: 'firstName' },
            { name: 'LAST', value: 'Doe', source: 'lastName' },
            { name: 'ETHNICITY', value: 'Ethnicity1', source: 'ethnicity' },
            { name: 'RACE', value: 'Race1', source: 'race' },
            { name: 'STATE', value: 'New York', source: 'state' },
            { name: 'ID TYPE', value: 'IdentificationType1', source: 'identification.identificationType' },
            { name: 'ID NUMBER', value: '123456', source: 'identification.identificationNumber' }
        ];

        const chipItems = patientChipItems(mockPersonFilter, mockSearchCriteria);
        expect(chipItems).toEqual(expectedChipItems);
    });
});
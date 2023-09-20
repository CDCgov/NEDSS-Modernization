import { fireEvent, render } from '@testing-library/react';
import { Gender, PersonFilter } from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { PatientChips } from './PatientChips';

describe('PatientChips component tests', () => {
    it('should render last name chip', () => {
        const filter: PersonFilter = { lastName: 'lastName', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('LAST-chip');
        expect(chip.children[0]).toHaveTextContent('LAST');
        expect(chip.children[2]).toHaveTextContent('lastName');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.lastName).not.toBeDefined();
    });

    it('should render first name chip', () => {
        const filter: PersonFilter = { firstName: 'firstName', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('FIRST-chip');
        expect(chip.children[0]).toHaveTextContent('FIRST');
        expect(chip.children[2]).toHaveTextContent('firstName');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.firstName).not.toBeDefined();
    });

    it('should render DOB chip', () => {
        const filter: PersonFilter = { dateOfBirth: '01/01/2020', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('DOB-chip');
        expect(chip.children[0]).toHaveTextContent('DOB');
        expect(chip.children[2]).toHaveTextContent('01/01/2020');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.dateOfBirth).not.toBeDefined();
    });

    it('should render SEX chip', () => {
        const filter: PersonFilter = { gender: Gender.M, recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('SEX-chip');
        expect(chip.children[0]).toHaveTextContent('SEX');
        expect(chip.children[2]).toHaveTextContent('M');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.gender).not.toBeDefined();
    });

    it('should render ID chip', () => {
        const filter: PersonFilter = { id: '1234A', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('ID-chip');
        expect(chip.children[0]).toHaveTextContent('ID');
        expect(chip.children[2]).toHaveTextContent('1234A');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.id).not.toBeDefined();
    });

    it('should render ADDRESS chip', () => {
        const filter: PersonFilter = { address: 'some Address', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('ADDRESS-chip');
        expect(chip.children[0]).toHaveTextContent('ADDRESS');
        expect(chip.children[2]).toHaveTextContent('some Address');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.address).not.toBeDefined();
    });

    it('should render CITY chip', () => {
        const filter: PersonFilter = { city: 'some city', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('CITY-chip');
        expect(chip.children[0]).toHaveTextContent('CITY');
        expect(chip.children[2]).toHaveTextContent('some city');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.city).not.toBeDefined();
    });

    it('should render STATE chip', () => {
        const filter: PersonFilter = { state: 'some state', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [{ value: 'some state', name: 'state name', abbreviation: 'st' }],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <PatientChips
                    filter={filter}
                    handlePersonFilterChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chip = getByTestId('STATE-chip');
        expect(chip.children[0]).toHaveTextContent('STATE');
        expect(chip.children[2]).toHaveTextContent('state name');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.state).not.toBeDefined();
    });

    it('should render ZIP chip', () => {
        const filter: PersonFilter = { zip: '12345', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('ZIP-chip');
        expect(chip.children[0]).toHaveTextContent('ZIP');
        expect(chip.children[2]).toHaveTextContent('12345');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.zip).not.toBeDefined();
    });

    it('should render PHONENUMBER chip', () => {
        const filter: PersonFilter = { phoneNumber: '123-456-7890', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('PHONENUMBER-chip');
        expect(chip.children[0]).toHaveTextContent('PHONENUMBER');
        expect(chip.children[2]).toHaveTextContent('123-456-7890');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.phoneNumber).not.toBeDefined();
    });

    it('should render EMAIL chip', () => {
        const filter: PersonFilter = { email: 'email@email.com', recordStatus: [] };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('EMAIL-chip');
        expect(chip.children[0]).toHaveTextContent('EMAIL');
        expect(chip.children[2]).toHaveTextContent('email@email.com');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.email).not.toBeDefined();
    });

    it('should render ID TYPE chip', () => {
        const filter: PersonFilter = {
            identification: { identificationType: 'type', identificationNumber: '1234' },
            recordStatus: []
        };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [],
                        races: [],
                        identificationTypes: [{ id: { code: 'type' }, codeDescTxt: 'some id text' }]
                    }
                }}>
                <PatientChips
                    filter={filter}
                    handlePersonFilterChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chip = getByTestId('ID TYPE-chip');
        expect(chip.children[0]).toHaveTextContent('ID TYPE');
        expect(chip.children[2]).toHaveTextContent('some id text');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.identification).not.toBeDefined();
    });

    it('should render ID NUMBER chip', () => {
        const filter: PersonFilter = {
            identification: { identificationType: 'type', identificationNumber: '1234' },
            recordStatus: []
        };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <PatientChips
                filter={filter}
                handlePersonFilterChange={(e) => {
                    updatedFilter = e;
                }}
            />
        );
        const chip = getByTestId('ID NUMBER-chip');
        expect(chip.children[0]).toHaveTextContent('ID NUMBER');
        expect(chip.children[2]).toHaveTextContent('1234');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.identification).not.toBeDefined();
    });

    it('should render ETHNICITY chip', () => {
        const filter: PersonFilter = {
            ethnicity: 'ethnicityCode',
            recordStatus: []
        };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [{ id: { code: 'ethnicityCode' }, codeDescTxt: 'ethnicity display' }],
                        races: [],
                        identificationTypes: []
                    }
                }}>
                <PatientChips
                    filter={filter}
                    handlePersonFilterChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chip = getByTestId('ETHNICITY-chip');
        expect(chip.children[0]).toHaveTextContent('ETHNICITY');
        expect(chip.children[2]).toHaveTextContent('ethnicity display');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.ethnicity).not.toBeDefined();
    });

    it('should render RACE chip', () => {
        const filter: PersonFilter = {
            race: 'raceCode',
            recordStatus: []
        };
        let updatedFilter: PersonFilter = { recordStatus: [] };
        const { getByTestId } = render(
            <SearchCriteriaContext.Provider
                value={{
                    searchCriteria: {
                        states: [],
                        programAreas: [],
                        conditions: [],
                        jurisdictions: [],
                        userResults: [],
                        outbreaks: [],
                        ethnicities: [],
                        races: [{ id: { code: 'raceCode' }, codeDescTxt: 'race display' }],
                        identificationTypes: []
                    }
                }}>
                <PatientChips
                    filter={filter}
                    handlePersonFilterChange={(e) => {
                        updatedFilter = e;
                    }}
                />
            </SearchCriteriaContext.Provider>
        );
        const chip = getByTestId('RACE-chip');
        expect(chip.children[0]).toHaveTextContent('RACE');
        expect(chip.children[2]).toHaveTextContent('race display');
        fireEvent.click(chip.children[3]);
        expect(updatedFilter.race).not.toBeDefined();
    });
});

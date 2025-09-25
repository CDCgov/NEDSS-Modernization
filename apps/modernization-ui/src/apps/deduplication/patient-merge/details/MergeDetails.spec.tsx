import { render, screen } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { MemoryRouter, Route, Routes } from 'react-router';
import { MergeDetails } from './MergeDetails';
import userEvent from '@testing-library/user-event';

const mockResponse: MergeCandidate[] = [
    {
        personLocalId: '89003',
        personUid: '10052298',
        addTime: '2022-05-19 18:34:42.363', // oldest record
        adminComments: {
            date: '',
            comment: 'abc'
        },
        ethnicity: {
            asOf: '2025-05-30T00:00:00',
            ethnicity: 'Hispanic or Latino',
            reasonUnknown: undefined,
            spanishOrigin: 'Central American | Cuban'
        },
        sexAndBirth: {
            asOf: '2022-06-07T00:00:00',
            dateOfBirth: '1952-07-20T00:00:00',
            currentSex: 'Unknown',
            sexUnknown: 'Refused',
            transgender: undefined,
            additionalGender: undefined,
            birthGender: undefined,
            multipleBirth: undefined,
            birthOrder: undefined,
            birthCity: undefined,
            birthState: undefined,
            birthCounty: undefined,
            birthCountry: undefined
        },
        mortality: {
            asOf: '2025-06-09T00:00:00',
            deceased: 'No',
            dateOfDeath: undefined,
            deathCity: undefined,
            deathState: undefined,
            deathCounty: undefined,
            deathCountry: undefined
        },
        general: {
            asOf: '2022-06-07T00:00:00',
            maritalStatus: 'Single, never married',
            mothersMaidenName: undefined,
            numberOfAdultsInResidence: undefined,
            numberOfChildrenInResidence: undefined,
            primaryOccupation: undefined,
            educationLevel: undefined,
            primaryLanguage: undefined,
            speaksEnglish: undefined,
            stateHivCaseId: undefined
        },
        investigations: [],
        addresses: [
            {
                id: '10052299',
                asOf: '2022-06-07T14:24:44.970',
                type: 'House',
                use: 'Home',
                address: '9 TRUMPET LN',
                address2: undefined,
                city: 'GORDONSVILLE',
                state: 'Tennessee',
                zipcode: '38563-0000',
                county: undefined,
                censusTract: undefined,
                country: 'United States',
                comments: undefined
            }
        ],
        phoneEmails: [
            {
                id: '10052300',
                asOf: '2022-06-07T14:24:44.970',
                type: 'Phone',
                use: 'Home',
                countryCode: '1',
                phoneNumber: '6154899999',
                extension: undefined,
                email: undefined,
                url: undefined,
                comments: undefined
            },
            {
                id: '10052301',
                asOf: '2022-06-07T14:24:44.970',
                type: 'Cellular Phone',
                use: 'Mobile Contact',
                countryCode: '1',
                phoneNumber: '6154899999',
                extension: undefined,
                email: undefined,
                url: undefined,
                comments: undefined
            }
        ],
        names: [
            {
                personUid: '10052298',
                sequence: '1',
                asOf: '2022-06-07T00:00:00',
                type: 'Legal',
                prefix: undefined,
                first: 'RONALD',
                middle: undefined,
                secondMiddle: undefined,
                last: 'WESLEY',
                secondLast: undefined,
                suffix: undefined,
                degree: undefined
            }
        ],
        identifications: [
            {
                personUid: '10052298',
                sequence: '1',
                asOf: '2018-04-18T00:00:00',
                type: 'WIC identifier',
                assigningAuthority: 'NH',
                value: 'WIC123'
            },
            {
                personUid: '10052298',
                sequence: '2',
                asOf: '2025-05-28T00:00:00',
                type: 'Account number',
                assigningAuthority: '',
                value: '23123132'
            }
        ],
        races: [
            {
                personUid: '10052298',
                raceCode: '2028-9',
                asOf: '2025-05-28T00:00:00',
                race: 'Asian',
                detailedRaces: 'Asian Indian | Bangladeshi | Bhutanese | Burmese'
            },
            {
                personUid: '10052298',
                raceCode: '2106-3',
                asOf: '2022-06-07T00:00:00',
                race: 'White',
                detailedRaces: 'European | Middle Eastern or North African'
            }
        ]
    },
    {
        personLocalId: '91000',
        personUid: '10055283',
        addTime: '2025-05-27 14:56:50.523',
        adminComments: {
            date: '2025-05-27T00:00',
            comment: 'Some admin comment goes here'
        },
        ethnicity: {
            asOf: undefined,
            ethnicity: undefined,
            reasonUnknown: undefined,
            spanishOrigin: undefined
        },
        sexAndBirth: {
            asOf: '2025-06-05T00:00:00',
            dateOfBirth: '2025-05-04T00:00:00',
            currentSex: 'Male',
            sexUnknown: undefined,
            transgender: 'Did not ask',
            additionalGender: 'Add Gender',
            birthGender: 'Male',
            multipleBirth: 'No',
            birthOrder: '1',
            birthCity: 'Birth City',
            birthState: 'Tennessee',
            birthCounty: 'Monroe County',
            birthCountry: 'United States'
        },
        mortality: {
            asOf: '2025-06-05T00:00:00',
            deceased: 'Yes',
            dateOfDeath: '2025-05-11T00:00:00',
            deathCity: 'Death city',
            deathState: 'Texas',
            deathCounty: 'Anderson County',
            deathCountry: 'Afghanistan'
        },
        general: {
            asOf: '2025-06-05T00:00:00',
            maritalStatus: 'Anundefineded',
            mothersMaidenName: 'MotherMaiden',
            numberOfAdultsInResidence: '2',
            numberOfChildrenInResidence: '0',
            primaryOccupation: 'Mining',
            educationLevel: '10th grade',
            primaryLanguage: 'Eastern Frisian',
            speaksEnglish: 'Yes',
            stateHivCaseId: undefined
        },
        investigations: [
            {
                id: 'CAS10001000GA01',
                startDate: '2025-06-05T00:00:00',
                condition: '2019 Novel Coronavirus'
            },
            {
                id: 'CAS10001001GA01',
                startDate: undefined,
                condition: 'Cholera'
            }
        ],
        addresses: [
            {
                id: '10055291',
                asOf: '2025-05-27T00:00:00',
                type: 'Dormitory',
                use: 'Primary Business',
                address: '1112 Another address',
                address2: undefined,
                city: 'Atlanta',
                state: 'Georgia',
                zipcode: '12345',
                county: undefined,
                censusTract: undefined,
                country: undefined,
                comments: undefined
            },
            {
                id: '10055292',
                asOf: '2025-05-13T00:00:00',
                type: 'House',
                use: 'Home',
                address: '111 Main st',
                address2: 'Block 2',
                city: 'City ',
                state: 'Georgia',
                zipcode: '11111',
                county: 'Atkinson County',
                censusTract: '0111',
                country: 'United States',
                comments: 'Address comment 1'
            },
            {
                id: '10056288',
                asOf: '2025-06-05T00:00:00',
                type: 'House',
                use: 'Home',
                address: '111 Main st',
                address2: 'Block 2',
                city: 'City ',
                state: 'Georgia',
                zipcode: '11111',
                county: 'Atkinson County',
                censusTract: undefined,
                country: 'United States',
                comments: undefined
            },
            {
                id: '10056294',
                asOf: '2025-06-05T00:00:00',
                type: 'House',
                use: 'Home',
                address: '111 Main st',
                address2: 'Block 2',
                city: 'City',
                state: 'Georgia',
                zipcode: '11111',
                county: 'Atkinson County',
                censusTract: undefined,
                country: 'United States',
                comments: undefined
            }
        ],
        phoneEmails: [
            {
                id: '10055285',
                asOf: '2025-05-27T00:00:00',
                type: 'Answering service',
                use: 'Alternate Work Place',
                countryCode: '1',
                phoneNumber: '1111111111',
                extension: '1',
                email: 'oneEmail@email.com',
                url: '1Url.com',
                comments: '1 phone comment'
            },
            {
                id: '10055293',
                asOf: '2025-05-28T00:00:00',
                type: 'Cellular Phone',
                use: 'Temporary',
                countryCode: undefined,
                phoneNumber: '123',
                extension: undefined,
                email: undefined,
                url: undefined,
                comments: undefined
            }
        ],
        names: [
            {
                personUid: '10055283',
                sequence: '1',
                asOf: '2025-05-27T00:00:00',
                type: 'Legal',
                prefix: 'Bishop',
                first: 'John',
                middle: 'R',
                secondMiddle: '2M',
                last: 'Smith',
                secondLast: '2Last',
                suffix: 'Esquire',
                degree: 'PHD'
            }
        ],
        identifications: [
            {
                personUid: '10055283',
                sequence: '1',
                asOf: '2025-05-28T00:00:00',
                type: 'Account number',
                assigningAuthority: 'AZ',
                value: '444111'
            },
            {
                personUid: '10055283',
                sequence: '2',
                asOf: '2025-05-05T00:00:00',
                type: "Driver's license number",
                assigningAuthority: 'TN',
                value: '001111'
            }
        ],
        races: [
            {
                personUid: '10055283',
                raceCode: '2106-3',
                asOf: '2025-05-27T00:00:00',
                race: 'White',
                detailedRaces: 'European'
            },
            {
                personUid: '10055283',
                raceCode: '2106-3',
                asOf: '2025-06-05T00:00:00',
                race: 'White',
                detailedRaces: undefined
            }
        ]
    },
    {
        personUid: '000123',
        personLocalId: '3',
        addTime: '2025-05-27 14:56:50.523',
        adminComments: {
            date: '2025-05-27T00:00',
            comment: 'Some admin comment goes here'
        },
        ethnicity: {
            asOf: undefined,
            ethnicity: undefined,
            reasonUnknown: undefined,
            spanishOrigin: undefined
        },
        sexAndBirth: {
            asOf: '2025-06-05T00:00:00',
            dateOfBirth: '2025-05-04T00:00:00',
            currentSex: 'Male',
            sexUnknown: undefined,
            transgender: 'Did not ask',
            additionalGender: 'Add Gender',
            birthGender: 'Male',
            multipleBirth: 'No',
            birthOrder: '1',
            birthCity: 'Birth City',
            birthState: 'Tennessee',
            birthCounty: 'Monroe County',
            birthCountry: 'United States'
        },
        mortality: {
            asOf: '2025-06-05T00:00:00',
            deceased: 'Yes',
            dateOfDeath: '2025-05-11T00:00:00',
            deathCity: 'Death city',
            deathState: 'Texas',
            deathCounty: 'Anderson County',
            deathCountry: 'Afghanistan'
        },
        general: {
            asOf: '2025-06-05T00:00:00',
            maritalStatus: 'Annulled',
            mothersMaidenName: 'MotherMaiden',
            numberOfAdultsInResidence: '2',
            numberOfChildrenInResidence: '0',
            primaryOccupation: 'Mining',
            educationLevel: '10th grade',
            primaryLanguage: 'Eastern Frisian',
            speaksEnglish: 'Yes',
            stateHivCaseId: undefined
        },
        investigations: [],
        addresses: [],
        phoneEmails: [],
        names: [
            {
                personUid: '10055283',
                sequence: '1',
                asOf: '2025-05-27T00:00:00',
                type: 'Alias',
                prefix: 'Bishop',
                first: 'Not',
                middle: 'R',
                secondMiddle: '2M',
                last: 'Match',
                secondLast: '2Last',
                suffix: 'Esquire',
                degree: 'PHD'
            },
            {
                personUid: '10055283',
                sequence: '1',
                asOf: '2025-05-27T00:00:00',
                type: 'Legal',
                prefix: 'Bishop',
                first: 'John',
                middle: 'R',
                secondMiddle: '2M',
                last: 'Doe',
                secondLast: '2Last',
                suffix: 'Esquire',
                degree: 'PHD'
            }
        ],
        identifications: [],
        races: []
    }
];

const mockFetch = vi.fn();
let mockLoading = false;
vi.mock('apps/deduplication/api/useMergeDetails', () => ({
    useMergeDetails: () => {
        return { fetchPatientMergeDetails: mockFetch, loading: mockLoading, response: mockResponse };
    }
}));

const mockRemovePatient = vi.fn();
vi.mock('apps/deduplication/api/useRemoveMerge', () => ({
    useRemoveMerge: () => {
        return { removePatient: mockRemovePatient };
    }
}));

const Fixture = () => {
    return (
        <AlertProvider>
            <MemoryRouter initialEntries={['/deduplication/merge/1234']}>
                <Routes>
                    <Route path="/deduplication/merge/:matchId" element={<MergeDetails />} />
                </Routes>
            </MemoryRouter>
        </AlertProvider>
    );
};
describe('MergeDetails', () => {
    beforeEach(() => {
        mockLoading = false;
    });

    it('should fetch the patient merge details specified in the path', () => {
        render(<Fixture />);
        expect(mockFetch).toHaveBeenCalledWith('1234');
    });

    it('should render a loading indicator', () => {
        mockLoading = true;
        const { getByText } = render(<Fixture />);
        expect(getByText('Loading')).toBeInTheDocument();
    });

    it('should default to review display', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Patient matches requiring review')).toBeInTheDocument();
    });

    it('should apply default selection of the oldest patient', () => {
        const { getAllByRole } = render(<Fixture />);

        // all checked radio buttons should have the oldest record id for value
        const allChecked = getAllByRole('radio', { checked: true });
        allChecked.forEach((c) => expect(c).toHaveAttribute('value', '10052298'));

        // unchecked radio buttons should have the newer record id for value
        const allNotChecked = getAllByRole('radio', { checked: false });
        allNotChecked.forEach((c) => expect(c).not.toHaveAttribute('value', '10052298'));

        // All repeating block values (old and new) should be selected
        const checkboxes = getAllByRole('checkbox', { hidden: true });
        checkboxes.forEach((c) => expect(c).toBeChecked());
    });

    it('should confirm removal from group', async () => {
        const user = userEvent.setup();
        const { getAllByText, getByText } = render(<Fixture />);

        const removeButtons = getAllByText('Remove');
        expect(removeButtons).toHaveLength(2);

        // click remove
        await user.click(removeButtons[1]);

        // modal should pop up
        expect(getByText('Remove from group')).toBeInTheDocument();

        // click confirm
        await user.click(getAllByText('Remove')[2]);

        // api called
        expect(mockRemovePatient).toHaveBeenCalledWith('1234', '000123', expect.any(Function), expect.any(Function));
    });

    it('should confirm merge', async () => {
        const user = userEvent.setup();
        const { getByText } = render(<Fixture />);

        const mergeButton = getByText('Merge all');

        // click remove
        await user.click(mergeButton);

        // modal should pop up
        // Header
        expect(getByText('Merge patients')).toBeInTheDocument();

        // Content
        expect(
            screen.getAllByText((_, element) => {
                return (
                    element?.textContent ===
                    'You have indicated that you would like to merge the following patients: 89003, 91000, 3. Patient ID: 89003 will be the surviving record.'
                );
            })[0]
        ).toBeInTheDocument();
    });
});

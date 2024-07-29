import { render } from '@testing-library/react';
import { GeneralFields } from './GeneralFields';
import { useForm } from 'react-hook-form';
import { LabReportFilterEntry } from './labReportFormTypes';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { EntryMethod, EventStatus, UserType } from 'generated/graphql/schema';
import { AutocompleteSingleProps } from 'design-system/autocomplete';

jest.mock('options/autocompete/UserAutocomplete', () => ({
    UserAutocomplete: ({ id, label, onChange }: AutocompleteSingleProps) => (
        <input
            data-testid={id}
            placeholder={label}
            onChange={(e) => onChange?.({ name: e.target.value, value: e.target.value, label: e.target.value })}
        />
    )
}));

jest.mock('options/autocompete/ProviderAutocomplete', () => ({
    ProviderAutocomplete: ({ id, label, onChange }: AutocompleteSingleProps) => (
        <input
            data-testid={id}
            placeholder={label}
            onChange={(e) => onChange?.({ name: e.target.value, value: e.target.value, label: e.target.value })}
        />
    )
}));

jest.mock('options/autocompete/FacilityAutocomplete', () => ({
    FacilityAutocomplete: ({ id, label, onChange }: AutocompleteSingleProps) => (
        <input
            data-testid={id}
            placeholder={label}
            onChange={(e) => onChange?.({ name: e.target.value, value: e.target.value, label: e.target.value })}
        />
    )
}));

const mockSearchCriteria = {
    programAreas: [{ id: 'STD', progAreaDescTxt: 'STD' }],
    conditions: [{ id: 'COND1', conditionDescTxt: 'Condition 1' }],
    jurisdictions: [{ id: '1', codeDescTxt: 'Jurisdiction 1', typeCd: 'jur1' }],
    userResults: [{ nedssEntryId: 'user1', userId: 'userId1', userFirstNm: 'John', userLastNm: 'Doe' }],
    outbreaks: [{ id: { code: 'OB1', codeSetNm: 'Outbreak 1' }, codeShortDescTxt: 'OB1' }],
    ethnicities: [{ id: { code: 'ETH1' }, codeDescTxt: 'Ethnicity 1' }],
    races: [{ id: { code: 'RACE1' }, codeDescTxt: 'Race 1' }],
    identificationTypes: [{ id: { code: 'ID1' }, codeDescTxt: 'ID Type 1' }],
    states: [{ value: 'ST1', name: 'State 1', abbreviation: 'S1' }]
};

const GeneralFieldsWithForm = () => {
    const form = useForm<LabReportFilterEntry>({
        defaultValues: {}
    });
    return (
        <SearchCriteriaContext.Provider value={{ searchCriteria: mockSearchCriteria }}>
            <GeneralFields form={form} />
        </SearchCriteriaContext.Provider>
    );
};

describe('GeneralFields component', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });
    it('renders program area multi-select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Program area')).toBeInTheDocument();
    });

    it('renders jurisdiction multi-select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Jurisdiction')).toBeInTheDocument();
    });

    it('renders pregnancy status select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Pregnancy status')).toBeInTheDocument();
    });

    it('renders event id type select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Event id type')).toBeInTheDocument();
    });

    it('renders event date type select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Event date type')).toBeInTheDocument();
    });

    it('renders entry method checkboxes', async () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Entry method')).toBeInTheDocument();
        Object.values(EntryMethod).forEach((method) => {
            expect(getByRole('checkbox', { name: new RegExp(method, 'i') })).toBeInTheDocument();
        });
    });

    it('renders entered by checkboxes', async () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Entered by')).toBeInTheDocument();
        Object.values(UserType).forEach((type) => {
            expect(getByRole('checkbox', { name: new RegExp(type, 'i') })).toBeInTheDocument();
        });
    });

    it('renders event status checkboxes', async () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Event status')).toBeInTheDocument();
        Object.values(EventStatus).forEach((status) => {
            expect(getByRole('checkbox', { name: new RegExp(status, 'i') })).toBeInTheDocument();
        });
    });

    it('renders user autocomplete fields', async () => {
        const { getByPlaceholderText } = render(<GeneralFieldsWithForm />);
        expect(getByPlaceholderText('Event created by user')).toBeInTheDocument();
        expect(getByPlaceholderText('Event updated by user')).toBeInTheDocument();
    });

    it('renders provider/facility type select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Event provider/facility type')).toBeInTheDocument();
    });
});

import { render } from '@testing-library/react';
import { FacilityOptionsService, ProviderOptionsService, UserOptionsService } from 'generated';
import { GeneralFields } from './GeneralFields';
import { LabratorySearchCriteriaFormWrapper } from './LabratorySearchCriteriaFormWrapper';

jest.mock('options/jurisdictions', () => ({
    useJurisdictionOptions: () => ({ all: [], resolve: () => {} })
}));

jest.mock('options/program-areas', () => ({
    useProgramAreaOptions: () => ({ all: [], resolve: () => {} })
}));

const GeneralFieldsWithForm = () => {
    return (
        <LabratorySearchCriteriaFormWrapper>
            <GeneralFields />
        </LabratorySearchCriteriaFormWrapper>
    );
};

describe('GeneralFields component ', () => {
    beforeEach(() => {
        const options = jest.fn();

        options.mockImplementation(() => Promise.resolve([]));

        FacilityOptionsService.facilityAutocomplete = options;
        ProviderOptionsService.providerAutocomplete = options;
        UserOptionsService.userAutocomplete = options;
    });

    it('renders program area multi-select', () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Program area')).toBeInTheDocument();
    });

    it('renders jurisdiction multi-select', () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Jurisdiction')).toBeInTheDocument();
    });

    it('renders pregnancy status select', () => {
        const { getByLabelText } = render(<GeneralFieldsWithForm />);
        expect(getByLabelText('Pregnancy status')).toBeInTheDocument();
    });

    it('renders event id type select', async () => {
        const { getByText } = render(<GeneralFieldsWithForm />);
        expect(getByText('Event ID type')).toBeInTheDocument();
    });

    it('renders event date type select', () => {
        const { getByLabelText } = render(<GeneralFieldsWithForm />);
        expect(getByLabelText('Event date type')).toBeInTheDocument();
    });

    it('renders entry method checkboxes', () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Entry method')).toBeInTheDocument();

        expect(getByRole('checkbox', { name: 'Entry method, Manual' })).toBeInTheDocument();
        expect(getByRole('checkbox', { name: 'Entry method, Electronic' })).toBeInTheDocument();
    });

    it('renders entered by checkboxes', () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Entered by')).toBeInTheDocument();

        expect(getByRole('checkbox', { name: 'Entered by, External' })).toBeInTheDocument();
        expect(getByRole('checkbox', { name: 'Entered by, Internal' })).toBeInTheDocument();
    });

    it('renders event status checkboxes', () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Event status')).toBeInTheDocument();

        expect(getByRole('checkbox', { name: 'Event status, New' })).toBeInTheDocument();
        expect(getByRole('checkbox', { name: 'Event status, Update' })).toBeInTheDocument();
    });

    it('renders processing status checkboxes', () => {
        const { getByText, getByRole } = render(<GeneralFieldsWithForm />);
        expect(getByText('Processing status')).toBeInTheDocument();

        expect(getByRole('checkbox', { name: 'Processing status, Unprocessed' })).toBeInTheDocument();
        expect(getByRole('checkbox', { name: 'Processing status, Processed' })).toBeInTheDocument();
    });

    it('renders user autocomplete fields', () => {
        const { getByLabelText } = render(<GeneralFieldsWithForm />);
        expect(getByLabelText('Event created by user')).toBeInTheDocument();
        expect(getByLabelText('Event updated by user')).toBeInTheDocument();
    });

    it('renders provider/facility fields', () => {
        const { getByLabelText } = render(<GeneralFieldsWithForm />);
        expect(getByLabelText('Event ordering facility')).toBeInTheDocument();
        expect(getByLabelText('Event reporting facility')).toBeInTheDocument();
        expect(getByLabelText('Event ordering provider')).toBeInTheDocument();
    });
});

import { FormProvider, useForm } from 'react-hook-form';
import { FacilityOptionsService, ProviderOptionsService, UserOptionsService } from 'generated';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import GeneralSearchFields from './GeneralSearchFields';
import { InvestigationFilterEntry } from './InvestigationFormTypes';

vi.mock('options/jurisdictions', () => ({
    useJurisdictionOptions: () => ({ all: [] })
}));

vi.mock('options/program-areas', () => ({
    useProgramAreaOptions: () => ({ all: [] })
}));

vi.mock('options/condition', () => ({
    useConditionOptions: () => ({ options: [] })
}));

const InvestigationFormWithFields = () => {
    const form = useForm<InvestigationFilterEntry>({
        mode: 'onBlur'
    });

    return (
        <FormProvider {...form}>
            <GeneralSearchFields />;
        </FormProvider>
    );
};

describe('GeneralSearchFields', () => {
    beforeEach(() => {
        const options = jest.fn();

        options.mockImplementation(() => Promise.resolve([]));

        FacilityOptionsService.facilityAutocomplete = options;
        ProviderOptionsService.providerAutocomplete = options;
        UserOptionsService.userAutocomplete = options;
    });

    describe('Pregnancy Status', () => {
        it('should exist', () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Pregnancy status' });
            expect(select).toBeInTheDocument();
        });

        it('should update the selection', async () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Pregnancy status' });

            const user = userEvent.setup();
            await user.selectOptions(select, 'No');

            expect(getByRole('option', { name: 'No', selected: true })).toBeInTheDocument();
        });
    });

    describe('Event ID Type', () => {
        it('should exist', () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Event ID type' });
            expect(select).toBeInTheDocument();
        });

        it('should display the Event ID input when an Event ID type is selected', async () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Event ID type' });

            const user = userEvent.setup();
            await user.selectOptions(select, 'Investigation ID');

            expect(getByRole('textbox', { name: 'Event ID' })).toBeInTheDocument();
        });
    });

    describe('Event Date Type', () => {
        it('should exist', () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Event date type' });
            expect(select).toBeInTheDocument();
        });

        it('should update the selection', async () => {
            const { getByRole } = render(<InvestigationFormWithFields />);

            const select = getByRole('combobox', { name: 'Event date type' });

            const user = userEvent.setup();
            await user.selectOptions(select, 'Date of report');

            expect(getByRole('option', { name: 'Date of report', selected: true })).toBeInTheDocument();
        });
    });
});

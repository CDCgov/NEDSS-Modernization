import { render, waitFor } from '@testing-library/react';
import {
    InvestigationEventDateType,
    InvestigationEventIdType,
    InvestigationFilter,
    PregnancyStatus,
    ReportingEntityType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { useForm } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';
import { InvestigationGeneralFields } from './InvestigationGeneralFields';

const InvestigationGeneralFieldsWithForm = () => {
    const investigationForm = useForm<InvestigationFilter>({ defaultValues: {} });
    return <InvestigationGeneralFields form={investigationForm} />;
};

const InvestigationGeneralFieldsWithDefaultsSet = () => {
    const investigationForm = useForm<InvestigationFilter>({
        defaultValues: {
            conditions: ['1'],
            programAreas: ['STD'],
            jurisdictions: ['1'],
            pregnancyStatus: PregnancyStatus.Yes,
            eventId: { investigationEventType: InvestigationEventIdType.AbcsCaseId, id: 'eventId' },
            eventDate: { type: InvestigationEventDateType.DateOfReport, from: '12/01/2020', to: '12/20/2020' },
            createdBy: 'userNedssEntry',
            lastUpdatedBy: 'userNedssEntry',
            providerFacilitySearch: { id: 'providerId', entityType: ReportingEntityType.Provider }
        }
    });
    return (
        <SearchCriteriaContext.Provider
            value={{
                searchCriteria: {
                    programAreas: [{ id: 'STD', progAreaDescTxt: 'STD' }],
                    conditions: [{ id: '1', conditionDescTxt: 'a condition' }],
                    jurisdictions: [{ id: '1', typeCd: '', codeDescTxt: 'a jurisdiction' }],
                    userResults: [
                        {
                            nedssEntryId: 'userNedssEntry',
                            userId: 'userId',
                            userFirstNm: 'firstName',
                            userLastNm: 'lastName'
                        }
                    ],
                    outbreaks: [],
                    ethnicities: [],
                    races: [],
                    identificationTypes: [],
                    states: []
                }
            }}>
            <InvestigationGeneralFields form={investigationForm} />
        </SearchCriteriaContext.Provider>
    );
};

describe('InvestigationGeneralFields component', () => {
    it('should contain default selections', async () => {
        const { container, getByText, getByTestId } = render(<InvestigationGeneralFieldsWithForm />);
        const multiSelectInputs = container.getElementsByClassName('multi-select-input');
        // Condition
        expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Condition');
        expect(multiSelectInputs[0].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Program Area
        expect(multiSelectInputs[1].getElementsByClassName('usa-label')[0]).toHaveTextContent('Program area');
        expect(multiSelectInputs[1].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Jurisdiction
        expect(multiSelectInputs[2].getElementsByClassName('usa-label')[0]).toHaveTextContent('Jurisdiction');
        expect(multiSelectInputs[2].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Pregnancy test
        getByText('Pregnancy test');
        const pregnancySelect = getByTestId('pregnancyStatus');
        expect(pregnancySelect).toHaveAttribute('placeholder', '-Select-');
        expect(pregnancySelect).toHaveValue('');

        // Event id type
        getByText('Event id type');
        const eventTypeSelect = getByTestId('eventId.investigationEventType');
        expect(eventTypeSelect).toHaveAttribute('placeholder', '-Select-');
        expect(eventTypeSelect).toHaveValue('');

        // Event id
        await waitFor(() =>
            expect(() => getByTestId('eventId.eventId.id')).toThrow(
                'Unable to find an element by: [data-testid="eventId.eventId.id"]'
            )
        );

        // Event date type
        getByText('Event date type');
        const eventDateSelect = getByTestId('eventDate.type');
        expect(eventDateSelect).toHaveAttribute('placeholder', '-Select-');
        expect(eventDateSelect).toHaveValue('');

        const dateInputs = container.getElementsByClassName('usa-date-picker__external-input');
        // from
        expect(dateInputs[0]).not.toBeDefined();

        // to
        expect(dateInputs[1]).not.toBeDefined();

        // Event provider/facility type
        getByText('Event provider/facility type');
        const providerSelect = getByTestId('providerFacilitySearch.entityType');
        expect(providerSelect).toHaveAttribute('placeholder', '-Select-');
        expect(providerSelect).toHaveValue('');
    });

    it('should show form values', () => {
        const { container, getByText, getByTestId } = render(<InvestigationGeneralFieldsWithDefaultsSet />);

        const multiSelectInputs = container.getElementsByClassName('multi-select-input');
        // Condition
        expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Condition');
        getByText('a condition');

        // Program Area
        expect(multiSelectInputs[1].getElementsByClassName('usa-label')[0]).toHaveTextContent('Program area');
        getByText('STD');

        // Jurisdiction
        expect(multiSelectInputs[2].getElementsByClassName('usa-label')[0]).toHaveTextContent('Jurisdiction');
        getByText('a jurisdiction');

        // Pregnancy test
        const pregnancySelect = getByTestId('pregnancyStatus');
        expect(pregnancySelect).toHaveAttribute('placeholder', '-Select-');
        expect(pregnancySelect).toHaveValue('YES');

        // Event id type
        const eventTypeSelect = getByTestId('eventId.investigationEventType');
        expect(eventTypeSelect).toHaveAttribute('placeholder', '-Select-');
        expect(eventTypeSelect).toHaveValue(InvestigationEventIdType.AbcsCaseId);
        getByText(formatInterfaceString(InvestigationEventIdType.AbcsCaseId));

        // Event id
        const eventIdInput = getByTestId('eventId.id');
        expect(eventIdInput).toHaveValue('eventId');

        // Event date type
        const eventDateSelect = getByTestId('eventDate.type');
        expect(eventDateSelect).toHaveAttribute('placeholder', '-Select-');
        expect(eventDateSelect).toHaveValue(InvestigationEventDateType.DateOfReport);

        const dateInputs = container.getElementsByClassName('usa-date-picker__external-input');
        // from
        expect(dateInputs[0]).toHaveValue('12/01/2020');

        // to
        expect(dateInputs[1]).toHaveValue('12/20/2020');

        // Event provider/facility type
        const providerSelect = getByTestId('providerFacilitySearch.entityType');
        expect(providerSelect).toHaveValue(ReportingEntityType.Provider);

        // Provider/facility id
        const providerId = getByTestId('providerFacilitySearch.id');
        expect(providerId).toHaveValue('providerId');
    });
});

import { render, waitFor } from '@testing-library/react';
import {
    EntryMethod,
    EventStatus,
    InvestigationEventDateType,
    InvestigationEventIdType,
    LabReportFilter,
    LaboratoryEventIdType,
    LaboratoryReportEventDateType,
    LaboratoryReportStatus,
    PregnancyStatus,
    ProviderType,
    ReportingEntityType,
    UserType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { useForm } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';
import { LabReportGeneralFields } from './LabReportGeneralFields';

const LabReportGeneralFieldsWithForm = () => {
    const labForm = useForm<LabReportFilter>({ defaultValues: {} });
    return <LabReportGeneralFields form={labForm} />;
};

const LabReportGeneralFieldsWithDefaultsSet = () => {
    const labForm = useForm<LabReportFilter>({
        defaultValues: {
            programAreas: ['STD'],
            jurisdictions: ['1'],
            pregnancyStatus: PregnancyStatus.Yes,
            eventId: { labEventType: LaboratoryEventIdType.AccessionNumber, labEventId: 'eventId' },
            eventDate: { type: LaboratoryReportEventDateType.DateOfReport, from: '12/01/2020', to: '12/20/2020' },
            createdBy: 'userNedssEntry',
            lastUpdatedBy: 'userNedssEntry',
            providerSearch: { providerType: ProviderType.OrderingFacility, providerId: 'providerId' },
            entryMethods: [EntryMethod.Electronic],
            enteredBy: [UserType.External, UserType.Internal],
            eventStatus: [EventStatus.New],
            processingStatus: [LaboratoryReportStatus.Unprocessed]
        }
    });
    return (
        <SearchCriteriaContext.Provider
            value={{
                searchCriteria: {
                    programAreas: [{ id: 'STD', progAreaDescTxt: 'STD' }],
                    conditions: [],
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
            <LabReportGeneralFields form={labForm} />
        </SearchCriteriaContext.Provider>
    );
};

describe('InvestigationGeneralFields component', () => {
    it('should contain default selections', async () => {
        const { container, getByText, getByTestId } = render(<LabReportGeneralFieldsWithForm />);
        const multiSelectInputs = container.getElementsByClassName('multi-select-input');

        // Program Area
        expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Program area');
        expect(multiSelectInputs[0].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Jurisdiction
        expect(multiSelectInputs[1].getElementsByClassName('usa-label')[0]).toHaveTextContent('Jurisdiction');
        expect(multiSelectInputs[1].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Pregnancy test
        getByText('Pregnancy test');
        const pregnancySelect = getByTestId('pregnancyStatus');
        expect(pregnancySelect).toHaveAttribute('placeholder', '-Select-');
        expect(pregnancySelect).toHaveValue('');

        // Event id type
        getByText('Event id type');
        const eventTypeSelect = getByTestId('eventId.labEventType');
        expect(eventTypeSelect).toHaveAttribute('placeholder', '-Select-');
        expect(eventTypeSelect).toHaveValue('');

        // Event id
        await waitFor(() =>
            expect(() => getByTestId('eventId.labEventId')).toThrow(
                'Unable to find an element by: [data-testid="eventId.labEventId"]'
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

        // Entry methods
        const entryMethods = getByText('Entry method');
        expect(entryMethods.getElementsByTagName('input')[0]).not.toBeChecked();
        expect(entryMethods.getElementsByTagName('input')[1]).not.toBeChecked();

        // Entered by
        const enteredBy = getByText('Entered by');
        expect(enteredBy.getElementsByTagName('input')[0]).not.toBeChecked();
        expect(enteredBy.getElementsByTagName('input')[1]).not.toBeChecked();

        // Event status
        const eventStatus = getByText('Event status');
        expect(eventStatus.getElementsByTagName('input')[0]).not.toBeChecked();
        expect(eventStatus.getElementsByTagName('input')[1]).not.toBeChecked();

        // Processing status
        const processingStatus = getByText('Processing status');
        expect(processingStatus.getElementsByTagName('input')[0]).not.toBeChecked();
        expect(processingStatus.getElementsByTagName('input')[1]).not.toBeChecked();

        // Event created by user
        getByText('Event created by user');
        const userCreateSelect = getByTestId('createdBy');
        expect(userCreateSelect).toHaveAttribute('placeholder', '-Select-');

        // Event updated by user
        getByText('Event updated by user');
        const userUpdateSelect = getByTestId('lastUpdatedBy');
        expect(userUpdateSelect).toHaveAttribute('placeholder', '-Select-');
        expect(userUpdateSelect).toHaveValue('');

        // Event provider/facility type
        getByText('Event provider/facility type');
        const providerSelect = getByTestId('providerSearch.providerType');
        expect(providerSelect).toHaveAttribute('placeholder', '-Select-');
        expect(providerSelect).toHaveValue('');
    });

    it('should show form values', () => {
        const { container, getByText, getByTestId } = render(<LabReportGeneralFieldsWithDefaultsSet />);

        const multiSelectInputs = container.getElementsByClassName('multi-select-input');

        // Program Area
        expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Program area');
        getByText('STD');

        // Jurisdiction
        expect(multiSelectInputs[1].getElementsByClassName('usa-label')[0]).toHaveTextContent('Jurisdiction');
        getByText('a jurisdiction');

        // Pregnancy test
        const pregnancySelect = getByTestId('pregnancyStatus');
        expect(pregnancySelect).toHaveAttribute('placeholder', '-Select-');
        expect(pregnancySelect).toHaveValue('YES');

        // Event id type
        const eventTypeSelect = getByTestId('eventId.labEventType');
        expect(eventTypeSelect).toHaveAttribute('placeholder', '-Select-');
        expect(eventTypeSelect).toHaveValue(LaboratoryEventIdType.AccessionNumber);
        getByText(formatInterfaceString(LaboratoryEventIdType.AccessionNumber));

        // Event id
        const eventIdInput = getByTestId('eventId.labEventId');
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

        // Entry methods
        const entryMethods = getByText('Entry method');
        expect(entryMethods.getElementsByTagName('input')[0]).toBeChecked();
        expect(entryMethods.getElementsByTagName('input')[1]).not.toBeChecked();

        // Entered by
        const enteredBy = getByText('Entered by');
        expect(enteredBy.getElementsByTagName('input')[0]).toBeChecked();
        expect(enteredBy.getElementsByTagName('input')[1]).toBeChecked();

        // Event status
        const eventStatus = getByText('Event status');
        expect(eventStatus.getElementsByTagName('input')[0]).toBeChecked();
        expect(eventStatus.getElementsByTagName('input')[1]).not.toBeChecked();

        // Processing status
        const processingStatus = getByText('Processing status');
        expect(processingStatus.getElementsByTagName('input')[0]).not.toBeChecked();
        expect(processingStatus.getElementsByTagName('input')[1]).toBeChecked();

        // Event created by user
        const userCreateSelect = getByTestId('createdBy');
        expect(userCreateSelect).toHaveValue('userNedssEntry');

        // Event updated by user
        const userUpdateSelect = getByTestId('lastUpdatedBy');
        expect(userUpdateSelect).toHaveValue('userNedssEntry');

        // Event provider/facility type
        const providerSelect = getByTestId('providerSearch.providerType');
        expect(providerSelect).toHaveValue(ProviderType.OrderingFacility);

        // Provider/facility id
        const providerId = getByTestId('providerSearch.providerId');
        expect(providerId).toHaveValue('providerId');
    });
});

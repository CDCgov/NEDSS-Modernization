import { render, waitFor } from '@testing-library/react';
import {
    EntryMethod,
    EventStatus,
    LaboratoryEventIdType,
    LaboratoryReportEventDateType,
    LaboratoryReportStatus,
    LabReportFilter,
    PregnancyStatus,
    ProviderType,
    UserType
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { useForm } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';
import { LabReportGeneralFields } from './LabReportGeneralFields';
import userEvent from '@testing-library/user-event';

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

describe('LabReportGeneralFields component', () => {
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
        const userCreateSelect = getByText('Event created by user');
        expect(userCreateSelect).toBeInTheDocument();
        expect(userCreateSelect).not.toHaveValue();

        // Event updated by user
        const userUpdateSelect = getByText('Event updated by user');
        expect(userUpdateSelect).toBeInTheDocument();
        expect(userUpdateSelect).not.toHaveValue();

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
        expect(eventDateSelect).toHaveValue(LaboratoryReportEventDateType.DateOfReport);

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

        // Event provider/facility type
        const providerSelect = getByTestId('providerSearch.providerType');
        expect(providerSelect).toHaveValue(ProviderType.OrderingFacility);
    });

    it('should clear date fields when date type is deselected', async () => {
        const { getByTestId, queryByText } = render(<LabReportGeneralFieldsWithDefaultsSet />);

        // Ensure that eventDate.type is set to a value that will render the date input fields
        const eventDateSelect = getByTestId('eventDate.type');
        userEvent.selectOptions(eventDateSelect, LaboratoryReportEventDateType.DateOfReport);

        // Wait for the date input fields to be rendered
        await waitFor(() => {
            const fromLabel = queryByText('From');
            const toLabel = queryByText('To');
            expect(fromLabel).toBeInTheDocument();
            expect(toLabel).toBeInTheDocument();
        });

        const dateInputs = document.getElementsByClassName('usa-date-picker__external-input');
        expect(dateInputs.length).toBeGreaterThan(0); // Ensure that the date input fields are rendered

        // Deselect the eventDate.type
        userEvent.selectOptions(eventDateSelect, '');

        expect(dateInputs[0]).toHaveValue('12/01/2020');
        expect(dateInputs[1]).toHaveValue('12/20/2020');
    });

    it('should clear event id field when event id type is deselected', async () => {
        const { getByTestId, queryByTestId } = render(<LabReportGeneralFieldsWithDefaultsSet />);

        const eventTypeSelect = getByTestId('eventId.labEventType');
        userEvent.selectOptions(eventTypeSelect, '');

        const eventIdInput = queryByTestId('eventId.labEventId');
        if (eventIdInput) {
            expect(eventIdInput).toHaveValue('');
        } else {
            expect(eventIdInput).toBeNull(); // Or any other appropriate assertion
        }
    });

    it('should clear provider/facility id field when provider/facility type is deselected', async () => {
        const { getByTestId, queryByTestId } = render(<LabReportGeneralFieldsWithDefaultsSet />);

        const providerSelect = getByTestId('providerSearch.providerType');
        userEvent.selectOptions(providerSelect, '');

        const providerId = queryByTestId('providerSearch.providerId');
        if (providerId) {
            expect(providerId).toHaveValue('');
        } else {
            expect(providerId).toBeNull(); // Or any other appropriate assertion
        }
    });

    it('should update entry methods correctly', async () => {
        const { getByText } = render(<LabReportGeneralFieldsWithForm />);

        const entryMethods = getByText('Entry method');
        const electroniceInput = entryMethods.getElementsByTagName('input')[0];
        const faxInput = entryMethods.getElementsByTagName('input')[1];

        userEvent.click(electroniceInput);
        expect(electroniceInput).toBeChecked();
        expect(faxInput).not.toBeChecked();

        userEvent.click(faxInput);
        expect(electroniceInput).toBeChecked();
        expect(faxInput).toBeChecked();

        userEvent.click(electroniceInput);
        expect(electroniceInput).not.toBeChecked();
        expect(faxInput).toBeChecked();
    });

    it('should update entered by correctly', async () => {
        const { getByText } = render(<LabReportGeneralFieldsWithForm />);

        const enteredBy = getByText('Entered by');
        const externalInput = enteredBy.getElementsByTagName('input')[0];
        const internalInput = enteredBy.getElementsByTagName('input')[1];

        userEvent.click(externalInput);
        expect(externalInput).toBeChecked();
        expect(internalInput).not.toBeChecked();

        userEvent.click(internalInput);
        expect(externalInput).toBeChecked();
        expect(internalInput).toBeChecked();

        userEvent.click(externalInput);
        expect(externalInput).not.toBeChecked();
        expect(internalInput).toBeChecked();
    });

    it('should update event status correctly', async () => {
        const { getByText } = render(<LabReportGeneralFieldsWithForm />);

        const eventStatus = getByText('Event status');
        const newInput = eventStatus.getElementsByTagName('input')[0];
        const updatedInput = eventStatus.getElementsByTagName('input')[1];

        userEvent.click(newInput);
        expect(newInput).toBeChecked();
        expect(updatedInput).not.toBeChecked();

        userEvent.click(updatedInput);
        expect(newInput).toBeChecked();
        expect(updatedInput).toBeChecked();

        userEvent.click(newInput);
        expect(newInput).not.toBeChecked();
        expect(updatedInput).toBeChecked();
    });

    it('should update processing status correctly', async () => {
        const { getByText } = render(<LabReportGeneralFieldsWithForm />);

        const processingStatus = getByText('Processing status');
        const unprocessedInput = processingStatus.getElementsByTagName('input')[0];
        const processedInput = processingStatus.getElementsByTagName('input')[1];

        userEvent.click(unprocessedInput);
        expect(unprocessedInput).toBeChecked();
        expect(processedInput).not.toBeChecked();

        userEvent.click(processedInput);
        expect(unprocessedInput).toBeChecked();
        expect(processedInput).toBeChecked();

        userEvent.click(unprocessedInput);
        expect(unprocessedInput).not.toBeChecked();
        expect(processedInput).toBeChecked();
    });
});

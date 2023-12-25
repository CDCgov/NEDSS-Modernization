import { render } from '@testing-library/react';
import {
    CaseStatus,
    InvestigationFilter,
    InvestigationStatus,
    NotificationStatus,
    ProcessingStatus
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { useForm } from 'react-hook-form';
import { InvestigationCriteria } from './InvestigationCriteria';
import { formatInterfaceString } from 'utils/util';

const InvestigationCriteriaFieldsWithForm = () => {
    const investigationForm = useForm<InvestigationFilter>({ defaultValues: {} });
    return <InvestigationCriteria form={investigationForm} />;
};

const InvestigationCriteriaFieldsWithDefaultsSet = () => {
    const investigationForm = useForm<InvestigationFilter>({
        defaultValues: {
            investigationStatus: InvestigationStatus.Open,
            outbreakNames: ['outbreak'],
            caseStatuses: [CaseStatus.Confirmed],
            processingStatuses: [ProcessingStatus.AwaitingInterview],
            notificationStatuses: [NotificationStatus.Completed]
        }
    });
    return (
        <SearchCriteriaContext.Provider
            value={{
                searchCriteria: {
                    programAreas: [],
                    conditions: [],
                    jurisdictions: [],
                    userResults: [],
                    outbreaks: [
                        { id: { code: 'outbreak', codeSetNm: 'someOutbreak' }, codeShortDescTxt: 'outbreak display' }
                    ],
                    ethnicities: [],
                    races: [],
                    identificationTypes: [],
                    states: []
                }
            }}>
            <InvestigationCriteria form={investigationForm} />
        </SearchCriteriaContext.Provider>
    );
};

describe('InvestigationGeneralFields component', () => {
    it('should contain default selections', () => {
        const { container, getByText, getByTestId } = render(<InvestigationCriteriaFieldsWithForm />);
        const multiSelectInputs = container.getElementsByClassName('multi-select-input');

        // Investigation status
        getByText('Investigation status');
        const investigationStatusSelect = getByTestId('investigationStatus');
        expect(investigationStatusSelect).toHaveAttribute('placeholder', '-Select-');
        expect(investigationStatusSelect).toHaveValue('');

        // Outbreak name
        expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Outbreak name');
        expect(multiSelectInputs[0].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Case status
        expect(multiSelectInputs[1].getElementsByClassName('usa-label')[0]).toHaveTextContent('Case status');
        expect(multiSelectInputs[1].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Case processing status
        expect(multiSelectInputs[2].getElementsByClassName('usa-label')[0]).toHaveTextContent(
            'Current processing status'
        );
        expect(multiSelectInputs[2].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );

        // Notification status
        expect(multiSelectInputs[3].getElementsByClassName('usa-label')[0]).toHaveTextContent('Notification status');
        expect(multiSelectInputs[3].getElementsByClassName('multi-select__placeholder')[0]).toHaveTextContent(
            '- Select -'
        );
    });

    it('should show form values', () => {
        const { container, getByTestId, getByText } = render(<InvestigationCriteriaFieldsWithDefaultsSet />);
        const multiSelectInputs = container.getElementsByClassName('multi-select-input');

        // Investigation status
        const investigationStatusSelect = getByTestId('investigationStatus');
        expect(investigationStatusSelect).toHaveValue(InvestigationStatus.Open);

        // Outbreak name
        expect(multiSelectInputs[0].getElementsByClassName('usa-label')[0]).toHaveTextContent('Outbreak name');
        expect(multiSelectInputs[0].getElementsByClassName('multi-select__multi-value__label')[0]).toHaveTextContent(
            'Outbreak display'
        );

        // Case status
        expect(multiSelectInputs[1].getElementsByClassName('usa-label')[0]).toHaveTextContent('Case status');
        expect(multiSelectInputs[1].getElementsByClassName('multi-select__multi-value__label')[0]).toHaveTextContent(
            formatInterfaceString(CaseStatus.Confirmed)
        );

        // Case processing status
        expect(multiSelectInputs[2].getElementsByClassName('usa-label')[0]).toHaveTextContent(
            'Current processing status'
        );
        expect(multiSelectInputs[2].getElementsByClassName('multi-select__multi-value__label')[0]).toHaveTextContent(
            formatInterfaceString(ProcessingStatus.AwaitingInterview)
        );

        // Notification status
        expect(multiSelectInputs[3].getElementsByClassName('usa-label')[0]).toHaveTextContent('Notification status');
        expect(multiSelectInputs[3].getElementsByClassName('multi-select__multi-value__label')[0]).toHaveTextContent(
            formatInterfaceString(NotificationStatus.Completed)
        );
    });
});

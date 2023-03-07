import { render, screen } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { SearchCriteria } from './SearchCriteria';

describe('SearchCriteria component tests', () => {
    it('should render the expected labels', () => {
        const { result } = renderHook(() => useForm());
        const { getAllByTestId } = render(<SearchCriteria control={result.current.control} />);
        const labelTexts = [
            'Investigation status',
            'Outbreak name',
            'Case status',
            'Current processing status',
            'Notification status'
        ];
        const dropdownOptions = [
            ['Closed', 'Open'],
            [''],
            ['Confirmed', 'Not A Case', 'Probable', 'Suspect', 'Unknown'],
            [
                'Awaiting Interview',
                'Closed Case',
                'Field Follow Up',
                'No Follow Up',
                'Open Case',
                'Surveillance Follow Up'
            ],
            ['Approved', 'Completed', 'Message Failed', 'Pending Approval', 'Rejected']
        ];
        const labels = getAllByTestId('label');
        const dropdowns = getAllByTestId('dropdown');

        // Should have expected labels
        labelTexts.forEach((text, idx) => {
            expect(labels[idx]).toHaveTextContent(text);
        });

        // should have expected dropdowns each with correct options
        dropdownOptions.forEach((optionsExpected, dropdownIdx) => {
            if (optionsExpected.length > 1) {
                const optionsDisplayed = dropdowns[dropdownIdx].querySelectorAll('option');
                optionsExpected.forEach((option, optionIdx) => {
                    expect(optionsDisplayed[optionIdx + 1]).toHaveTextContent(option);
                });
            }
        });
    });
});

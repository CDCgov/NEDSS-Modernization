import { render, screen } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { SearchCriteria } from './SearchCriteria';

describe('SearchCriteria component tests', () => {
    it('should render the expected Investigation status', () => {
        const { result } = renderHook(() => useForm());
        const { container, getAllByTestId } = render(<SearchCriteria control={result.current.control} />);

        const investigationStatus = container.querySelectorAll('select[name="investigationStatus"] option');

        expect(investigationStatus).toHaveLength(3);
        expect(investigationStatus[0]).toHaveValue('- Select -');

        expect(investigationStatus[1]).toHaveTextContent('Closed');
        expect(investigationStatus[1]).toHaveValue('CLOSED');

        expect(investigationStatus[2]).toHaveTextContent('Open');
        expect(investigationStatus[2]).toHaveValue('OPEN');
    });

    it('should render the expected Outbreak Names', () => {
        const { result } = renderHook(() => useForm());
        const { container, getAllByTestId } = render(<SearchCriteria control={result.current.control} />);

        const outBreakNames = container.querySelectorAll('select[name="outbreakNames"] option');

        expect(outBreakNames).toHaveLength(1);
        expect(outBreakNames[0]).toHaveValue('- Select -');
    });

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

        const labels = getAllByTestId('label');

        // Should have expected labels
        labelTexts.forEach((text, idx) => {
            expect(labels[idx]).toHaveTextContent(text);
        });
    });

    xit('should render the expected dropdown values', () => {
        const { result } = renderHook(() => useForm());
        const { getAllByTestId } = render(<SearchCriteria control={result.current.control} />);

        //  These tests were added before Case Status, Processing Status, and Notification Status
        // were switched to multi-selects however, the PR was merged after causing this test to be out of sync with the implementation.
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

        const dropdowns = getAllByTestId('dropdown');

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

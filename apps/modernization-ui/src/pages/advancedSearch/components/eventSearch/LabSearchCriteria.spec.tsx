import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { LabSearchCriteria } from './LabSearchCriteria';

describe('LabSearchCriteria component tests', () => {
    it('should render lab results based on the search criteria', () => {
        const { result } = renderHook(() => useForm());
        const { getAllByTestId } = render(<LabSearchCriteria control={result.current.control} />);
        const labelTexts = ['Resulted test', 'Coded result/organism'];
        const labels = getAllByTestId('label');
        labelTexts.forEach((text, idx) => {
            expect(labels[idx]).toHaveTextContent(text);
        });
    });
});

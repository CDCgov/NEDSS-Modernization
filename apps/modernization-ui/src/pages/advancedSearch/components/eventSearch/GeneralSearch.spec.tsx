import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { GeneralSearch } from './GeneralSearch';

describe('GeneralSearch component tests', () => {
    it('should render context based on the search criteria', () => {
        const { result } = renderHook(() => useForm());
        const { getAllByTestId } = render(<GeneralSearch control={result.current.control} />);
        const labelTexts = [
            'Condition',
            'Program area',
            'Jurisdiction',
            'Pregnancy test',
            'Event id type',
            'Event id',
            'Event date type',
            'From',
            'To',
            'Event created by user',
            'Event updated by user',
            'Event provider/facility type'
        ];
        const labels = getAllByTestId('label');
        labelTexts.forEach((text, idx) => {
            expect(labels[idx]).toHaveTextContent(text);
        });
    });
});

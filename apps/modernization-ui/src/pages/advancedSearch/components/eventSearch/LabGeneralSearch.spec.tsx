import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { LabGeneralSearch } from './LabGeneralSearch';

describe('LabGeneralSearch component tests', () => {
    it('should render lab details based on the search', () => {
        const { result } = renderHook(() => useForm());
        const { getByTestId, getByLabelText, getAllByTestId } = render(
            <LabGeneralSearch control={result.current.control} />
        );
        const labelTexts = [
            'Program area',
            'Jurisdiction',
            'Pregnancy test',
            'Event id type',
            'Event id',
            'Event date type'
        ];
        const labels = getAllByTestId('label');
        labelTexts.forEach((text, idx) => {
            expect(labels[idx]).toHaveTextContent(text);
        });
    });
});

import { MultiSelectControl } from './MultiSelectControl';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { render } from '@testing-library/react';

describe('MultiSelectControl component tests', () => {
    it('should render Controller component of react hook form and show its label and Multiselect', () => {
        const { result } = renderHook(() => useForm());
        const { getByText } = render(
            <MultiSelectControl
                control={result.current.control}
                options={[{ value: 'sample value' }]}
                name="search_input"
                label="Test label"
            />
        );
        expect(getByText('Test label')).toBeTruthy();
    });
});

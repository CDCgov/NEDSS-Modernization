import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { EventTypes } from './EventType';

describe('EventTypes component tests', () => {
    it('should render event type report based on selection', () => {
        const mockOnChangeMethod = (event: any) => {};
        const { result } = renderHook(() => useForm());
        const { getByTestId } = render(
            <EventTypes
                control={result.current.control}
                name="event-types-component"
                onChangeMethod={mockOnChangeMethod}
            />
        );
        const labelEle = getByTestId('label');
        const dropdownEle = getByTestId('dropdown');
        const dropdownOptionEles = dropdownEle.querySelectorAll('option');

        // Label should be present with value Event type
        expect(labelEle).toHaveTextContent('Event type');

        // Dropdown should be available and have 2 options for Investigation and Laboratory report
        expect(dropdownOptionEles[1]).toHaveTextContent('Investigation');
        expect(dropdownOptionEles[2]).toHaveTextContent('Laboratory report');
    });
});

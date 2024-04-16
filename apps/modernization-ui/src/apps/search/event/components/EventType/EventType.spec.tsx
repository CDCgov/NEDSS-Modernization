import { render } from '@testing-library/react';
import { renderHook, act } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { EventTypes } from './EventType';
import { SEARCH_TYPE } from 'apps/search/advancedSearch/AdvancedSearch';
import userEvent from '@testing-library/user-event';

describe('EventTypes component tests', () => {
    it('should render event type report based on selection', async () => {
        const mockOnChangeMethod = jest.fn();
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

        const mockOnChange = jest.fn();
        act(() => {
            const field = result.current.control._fields['event-types-component'];
            if (field && field._f) {
                field._f.onChange?.(mockOnChange);
            }
        });
        userEvent.selectOptions(dropdownEle, SEARCH_TYPE.INVESTIGATION);
        expect(mockOnChangeMethod).toHaveBeenCalledWith(SEARCH_TYPE.INVESTIGATION);
    });
});

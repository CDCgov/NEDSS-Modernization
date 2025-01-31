import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { FormProvider, useForm } from 'react-hook-form';
import { FilterProvider, useFilter } from './useFilter';

const TestComponent = () => {
    const { activeFilter, toggleFilter } = useFilter();
    const formMethods = useForm();

    return (
        <FormProvider {...formMethods}>
            <div>
                <span data-testid="filterable">{activeFilter.toString()}</span>
                <button onClick={toggleFilter}>Toggle</button>
            </div>
        </FormProvider>
    );
};

const renderWrapper = () => {
    return render(
        <FilterProvider>
            <TestComponent />
        </FilterProvider>
    );
};

describe('FilterProvider', () => {
    it('should provide default filterable state as false', () => {
        const { getByTestId } = renderWrapper();

        expect(getByTestId('filterable').textContent).toBe('false');
    });

    it('should toggle filterable state', () => {
        const { getByTestId, getByText } = renderWrapper();

        const toggleButton = getByText('Toggle');
        act(() => {
            toggleButton.click();
        });

        expect(getByTestId('filterable').textContent).toBe('true');
    });
});

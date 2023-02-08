import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { SelectControl } from './SelectControl';

describe('SelectControl component tests', () => {
    it('should render select dropdown with provided options', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(
            <SelectControl 
            control={result.current.control} 
            options={
                [{
                    name: 'Account Number', value: 'ACCOUNT_NUMBER'
                }, {
                    name: 'Drivers License Number', value: 'DRIVERS_LICENSE_NUMBER'
                }
            ]}
            name="search_input" 
            label="Test label" />
        );
        const options = container.getElementsByTagName('option');
        expect(options[1].getAttribute('value')).toBe('ACCOUNT_NUMBER');
        expect(options[1].innerHTML).toBe('Account Number');
        expect(options[2].getAttribute('value')).toBe('DRIVERS_LICENSE_NUMBER');
        expect(options[2].innerHTML).toBe('Drivers License Number');
    });
});

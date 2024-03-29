import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { AddressForm } from './AddressForm';

describe('AddressForm component tests', () => {
    it('should render a grid with 3 inputs labels which are Street Address, City and Zipcode', () => {
        const { result } = renderHook(() => useForm());
        const { container, getByLabelText } = render(<AddressForm control={result.current.control} />);
        expect(getByLabelText('Street address')).toBeTruthy();
        expect(getByLabelText('City')).toBeTruthy();
        expect(container.querySelectorAll('.usa-label')[2].textContent).toBe('State');
        expect(getByLabelText('Zip code')).toBeTruthy();
    });

    it('should render a dropdown to select State from the provided options', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(<AddressForm control={result.current.control} errors={null} />);
        const options = container.getElementsByTagName('option');

        expect(options[0]).toHaveTextContent('- Select -');

        for (let i = 1; i < options.length; i++) {
            expect(options[i].value).toBe(options[i].textContent);
        }
    });
});

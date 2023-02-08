import { fireEvent, render } from '@testing-library/react';
import { SelectInput } from './SelectInput';

describe('SelectInput component tests', () => {
    describe('when default value is provided', () => {
        it('should render DropDown which has a label as Test Label and preselected option as the default value passed', () => {
            const { 
                container
             } = render(<SelectInput 
                id="test-id" 
                label="Test Label" 
                options={
                    [{
                        name: 'Account Number', value: 'ACCOUNT_NUMBER'
                    }, {
                        name: 'Drivers License Number', value: 'DRIVERS_LICENSE_NUMBER'
                    }
                ]}
                name="test-name"
                htmlFor="test-id"
                defaultValue="DRIVERS_LICENSE_NUMBER"/>);
            expect(container.querySelector('option:checked')?.innerHTML).toBe('Drivers License Number');
        });
    });
    describe('when default value is not provided', () => {
        it('should render DropDown which has a label as Test Label and should show - Select - as the placeholder/default selected option', () => {
            const { 
                container
             } = render(<SelectInput 
                id="test-id" 
                label="Test Label" 
                options={
                    [{
                        name: 'Account Number', value: 'ACCOUNT_NUMBER'
                    }, {
                        name: 'Drivers License Number', value: 'DRIVERS_LICENSE_NUMBER'
                    }
                ]}
                name="test-name"
                htmlFor="test-id"/>);
            expect(container.querySelector('option:checked')?.innerHTML).toBe('- Select -');
        });
    });
    describe('when one of the options is clicked upon', () => {
        it('should mark that option as checked', async () => {
            const { 
                container,
                getByTestId
             } = render(<SelectInput 
                id="test-id" 
                label="Test Label" 
                options={
                    [{
                        name: 'Account Number', value: 'ACCOUNT_NUMBER'
                    }, {
                        name: 'Drivers License Number', value: 'DRIVERS_LICENSE_NUMBER'
                    }
                ]}
                name="test-name"
                htmlFor="test-id"/>);
            // click on the option you want
            fireEvent.change(getByTestId('dropdown'), { target: { value: 'ACCOUNT_NUMBER' } });
            // check the option is now selected
            expect(container.querySelector('option:checked')?.innerHTML).toBe('Account Number');
        });
    });
});

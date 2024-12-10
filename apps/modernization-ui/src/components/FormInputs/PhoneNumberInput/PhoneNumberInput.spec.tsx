import { render } from '@testing-library/react';
import { PhoneNumberInput } from './PhoneNumberInput';

describe('PhoneNumberInput component tests', () => {
    describe('When phone number is valid and formatted', () => {
        it('should return true', () => {
            const { queryByRole } = render(
                <PhoneNumberInput label="test-phone-input" defaultValue="555-555-5555" onChange={jest.fn()} />
            );
            expect(queryByRole('alert')).not.toBeInTheDocument();
        });
    });

    describe('When phone number is invalid', () => {
        it('should return false', () => {
            const { getByRole } = render(
                <PhoneNumberInput
                    label="test-phone-input-false"
                    defaultValue="&X}5"
                    error="Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9)."
                    onChange={jest.fn()}
                />
            );
            expect(getByRole('alert')).toHaveTextContent(
                'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
            );
        });
    });
});

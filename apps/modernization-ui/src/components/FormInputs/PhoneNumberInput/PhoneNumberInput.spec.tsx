import { render } from '@testing-library/react';
import { PhoneNumberInput } from './PhoneNumberInput';

describe('PhoneNumberInput component tests', () => {
    describe('When phone number is valid and formatted', () => {
        it('should return true', () => {
            const { getByTestId } = render(
                <PhoneNumberInput label="test-phone-input" defaultValue="555-555-5555" onChange={onchange} />
            );
            expect(getByTestId('errorMessage').innerHTML).toBe('');
        });
    });
    describe('When phone number is partially valid', () => {
        it('should return true', () => {
            const { getByTestId } = render(
                <PhoneNumberInput label="test-phone-partial" defaultValue="555" onChange={onchange} />
            );
            expect(getByTestId('errorMessage').innerHTML).toBe('');
        });
    });
    describe('When phone number is invalid', () => {
        it('should return false', () => {
            const { getByTestId } = render(
                <PhoneNumberInput
                    label="test-phone-input-false"
                    defaultValue="&X}5"
                    onChange={onchange}
                    error="Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9)."
                />
            );
            expect(getByTestId('errorMessage').innerHTML).toBe(
                'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
            );
        });
    });
});

import { fireEvent, render } from "@testing-library/react";
import { PhoneNumberInput } from "./PhoneNumberInput";

describe('PhoneNumberInput component tests', () => {
    describe('When phone number is valid and formatted', () => {
        it('should return true', () => {
            const { } = render(<PhoneNumberInput label="test-phone-input" defaultValue="555-555-5555" onChange={onchange} />)
            expect (PhoneNumberInput).getCurrentState().toBeTruthy();
        })
    });
    describe('When phone number is partially valid', () => {
        it('should return true', () => {
            const { } = render(<PhoneNumberInput label="test-phone-partial" defaultValue="555" onChange={onchange} />)
            expect (PhoneNumberInput).getCurrentState().toBeTruthy();
        })
    })
    describe('When phone number is invalid', () => {
        it('should return false', () => {
            const { } = render(<PhoneNumberInput label="test-phone-input-false" defaultValue="&X}5" onChange={onchange} />)
            expect (PhoneNumberInput).getCurrentState().toBeFalsy();
        })
    });
});
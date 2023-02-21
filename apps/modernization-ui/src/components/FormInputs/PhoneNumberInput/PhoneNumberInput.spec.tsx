import { fireEvent, render } from "@testing-library/react";
import { warning } from "react-router/lib/router";
import { PhoneNumberInput } from "./PhoneNumberInput";

describe('PhoneNumberInput component tests', () => {
    describe('When phone number is valid and formatted', () => {
        it('should return true', () => {
            const { container } = render(<PhoneNumberInput label="test-phone-input" defaultValue="555-555-5555" onChange={onchange} />)
            const warning = container.getElementsByClassName('text-red');
            expect(warning[0].innerHTML).toBe('');
        })
    });
    describe('When phone number is partially valid', () => {
        it('should return true', () => {
            const { container } = render(<PhoneNumberInput label="test-phone-partial" defaultValue="555" onChange={onchange} />)
            const warning = container.getElementsByClassName('text-red');
            expect(warning[0].innerHTML).toBe('');
        })
    })
    describe('When phone number is invalid', () => {
        it('should return false', () => {
            const { container } = render(<PhoneNumberInput label="test-phone-input-false" defaultValue="&X}5" onChange={onchange} />)
            const warning = container.getElementsByClassName('text-red');
            expect(warning[0].innerHTML).toBe('Not a valid number');
        })
    });
});
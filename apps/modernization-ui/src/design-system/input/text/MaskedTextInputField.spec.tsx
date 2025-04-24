import { render } from "@testing-library/react";
import { MaskedTextInput } from "./MaskedTextInput";
import userEvent from "@testing-library/user-event";

const PHONE_MASK = '___-___-____';
const TEST_PHONE_NUMBER = '123-456-7890';
const TEST_DIGITS = '1234567890';

describe('MaskedTextInput', () => {
    it('should handle input masking correctly', async () => {
        const handleChange = jest.fn();
        const { getByRole } = render(
            <div>
                <label htmlFor="phone-input">Phone Input</label>
                <MaskedTextInput
                    id="phone-input"
                    mask={PHONE_MASK}
                    value=""
                    onChange={handleChange}
                />
            </div>
        );

        const input = getByRole('textbox');
        await userEvent.type(input, TEST_DIGITS);

        expect(input).toHaveValue(TEST_PHONE_NUMBER);
        expect(handleChange).toHaveBeenCalledWith(TEST_PHONE_NUMBER);
    });
});
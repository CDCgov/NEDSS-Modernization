import { axe } from "jest-axe";
import { MaskedTextInput } from "./MaskedTextInput"
import { render, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { useState } from "react";

describe('MaskedTextInput', () => {
    it('should render with no accessibilty violations', async () => {
        const { container } = render(
            <div>
                <label htmlFor="test-input">Test Input</label>
                <MaskedTextInput 
                id="test-input"
                mask="___-___-____"
                value=""
                onChange={() => {}}
            />
            </div>
        );
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should handle input masking correctly', async () => {
        const handleChange = jest.fn();
        const { getByRole } = render(
            <div>
                <label htmlFor="phone-input">Phone Input</label>
                <MaskedTextInput 
                    id="phone-input"
                    mask="___-___-____"
                    value=""
                    onChange={handleChange}
                />
            </div>
        );

        const input = getByRole('textbox');
        await userEvent.type(input, '1234567890');

        expect(input).toHaveValue('123-456-7890');
        expect(handleChange).toHaveBeenCalledWith('123-456-7890');
    });

    it('should respond to parent component clearing value', async () => {
        const TestWrapper = () => {
            const [value, setValue] = useState('123-456-7890')

            return (
                <div>
                <label htmlFor="clearable-input">Clearable Input</label>
                <MaskedTextInput 
                    id="clearable-input"
                    mask="___-___-____"
                    value={value}
                    onChange={(newValue) => {setValue(newValue ?? '')}}
                />
                <button onClick={() => setValue('')}>Clear</button>
            </div>
            );
        };

        const { getByRole } = render(<TestWrapper />);
        const input = getByRole('textbox');
        expect(input).toHaveValue('123-456-7890');

        const clearButton = getByRole('button', { name: 'Clear' });
        await userEvent.click(clearButton);

        await waitFor(() => {
            expect(input).toHaveValue('');
        });
    });
});
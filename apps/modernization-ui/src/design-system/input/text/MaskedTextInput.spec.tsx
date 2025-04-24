import { axe } from "jest-axe";
import { MaskedTextInput } from "./MaskedTextInput"
import { render } from "@testing-library/react";

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

});
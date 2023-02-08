import { CheckBoxControl } from "./CheckBoxControl";
import { useForm } from "react-hook-form";
import { renderHook } from '@testing-library/react-hooks';
import { render } from "@testing-library/react";

describe('CheckBoxControl component tests', () => {
    it('should render Controller component of react hook form and show its label and checkbox', () => {
        const { result } = renderHook(() => useForm());
        const { container, getByLabelText } = render(<CheckBoxControl control={result.current.control} id="test-id" name="test-name" label="Test label" />);
        expect(getByLabelText('Test label')).toBeTruthy();
        expect(container.querySelector('input')?.getAttribute('type')).toBe('checkbox');
    });
});


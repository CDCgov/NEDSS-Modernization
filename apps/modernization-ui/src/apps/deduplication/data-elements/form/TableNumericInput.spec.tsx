import { act, render } from '@testing-library/react';
import { TableNumericInput } from './TableNumericInput';
import userEvent from '@testing-library/user-event';

const onChange = jest.fn();
const onBlur = jest.fn();

describe('TableNumericInput', () => {
    it('renders input', () => {
        const { getByLabelText, getByRole } = render(
            <TableNumericInput
                name="Test name"
                label="test label"
                min={0}
                max={1}
                step={0.01}
                onBlur={onBlur}
                onChange={onChange}
            />
        );
        expect(getByRole('label')).toHaveTextContent('test label');
        const element = getByLabelText('test label');
        expect(element).toHaveAttribute('max', '1');
        expect(element).toHaveAttribute('min', '0');
        expect(element).toHaveAttribute('step', '0.01');
    });

    it('triggers callbacks', () => {
        const { getByLabelText } = render(
            <TableNumericInput
                name="Test name"
                label="test label"
                min={0}
                max={1}
                step={0.01}
                onBlur={onBlur}
                onChange={onChange}
                disabled={false}
            />
        );
        const element = getByLabelText('test label');
        act(() => {
            userEvent.click(element);
            userEvent.type(element, '0.03');
            userEvent.tab();
        });

        expect(onChange).toHaveBeenCalledTimes(3);
        expect(onBlur).toHaveBeenCalledTimes(1);
    });
});
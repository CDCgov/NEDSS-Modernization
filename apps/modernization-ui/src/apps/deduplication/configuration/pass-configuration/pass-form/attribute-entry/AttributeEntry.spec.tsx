import { render } from '@testing-library/react';
import { AttributeEntry } from './AttributeEntry';
import userEvent from '@testing-library/user-event';

const onChange = jest.fn();

describe('AttributeEntry', () => {
    it('should display label', () => {
        const { getByText } = render(<AttributeEntry label="Some label" selected={false} onChange={onChange} />);

        expect(getByText('Some label')).toBeInTheDocument();
    });

    it('should display description', () => {
        const { getByText } = render(
            <AttributeEntry label="Some label" description="my description" selected={false} onChange={onChange} />
        );

        expect(getByText('my description')).toBeInTheDocument();
    });

    it('should set checked state correctly when false', () => {
        const { getByLabelText } = render(
            <AttributeEntry label="Some label" description="my description" selected={false} onChange={onChange} />
        );
        const checkbox = getByLabelText('Some label');
        expect(checkbox).not.toBeChecked();

        userEvent.click(checkbox);

        expect(onChange).toHaveBeenCalledTimes(1);
    });

    it('should set checked state correctly when true', () => {
        const { getByLabelText } = render(
            <AttributeEntry label="Some label" description="my description" selected={true} onChange={onChange} />
        );
        const checkbox = getByLabelText('Some label');
        expect(checkbox).toBeChecked();

        userEvent.click(checkbox);

        expect(onChange).toHaveBeenCalledTimes(1);
    });
});

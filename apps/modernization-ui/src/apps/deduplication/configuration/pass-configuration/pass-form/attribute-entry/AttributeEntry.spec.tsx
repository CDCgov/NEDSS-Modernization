import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { AttributeEntry } from './AttributeEntry';

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

    it('should set checked state correctly when false', async () => {
        const { getByLabelText } = render(
            <AttributeEntry label="Some label" description="my description" selected={false} onChange={onChange} />
        );

        const user = userEvent.setup();

        const checkbox = getByLabelText('Some label');
        expect(checkbox).not.toBeChecked();

        await user.click(checkbox);

        expect(onChange).toHaveBeenCalledTimes(1);
    });

    it('should set checked state correctly when true', async () => {
        const { getByLabelText } = render(
            <AttributeEntry label="Some label" description="my description" selected={true} onChange={onChange} />
        );

        const user = userEvent.setup();

        const checkbox = getByLabelText('Some label');
        expect(checkbox).toBeChecked();

        await user.click(checkbox);

        expect(onChange).toHaveBeenCalledTimes(1);
    });
});

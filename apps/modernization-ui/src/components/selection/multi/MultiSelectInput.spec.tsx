import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { MultiSelectInput } from './MultiSelectInput';

describe('Given a MultiSelectInput component', () => {
    const options = [
        { name: 'name-one', value: '1' },
        { name: 'name-two', value: '2' },
        { name: 'name-three', value: '3' }
    ];

    it('should display options when clicked', async () => {
        const { getByRole, getByText } = render(
            <MultiSelectInput id="testing-multi-select" label="testing multi-select" options={options} />
        );

        const component = getByRole('combobox');

        const user = userEvent.setup();

        await user.click(component);

        expect(getByText('name-one')).toBeInTheDocument();
        expect(getByText('name-two')).toBeInTheDocument();
        expect(getByText('name-three')).toBeInTheDocument();
    });

    it('should display single selected value', () => {
        const { getByText, queryByText } = render(<MultiSelectInput options={options} value={['2']} />);

        expect(getByText('name-two')).toBeInTheDocument();

        expect(queryByText('name-one')).not.toBeInTheDocument();
        expect(queryByText('name-three')).not.toBeInTheDocument();
    });

    it('should display multiple selected value', () => {
        const { getByText, queryByText } = render(<MultiSelectInput options={options} value={['2', '1']} />);

        expect(getByText('name-two')).toBeInTheDocument();

        expect(queryByText('name-three')).not.toBeInTheDocument();
    });

    it('should remove the selected item when clicked', async () => {
        const { queryByText, getByLabelText } = render(<MultiSelectInput options={options} value={['2']} />);

        const selected = getByLabelText('Remove name-two');

        const user = userEvent.setup();

        await user.click(selected);

        expect(queryByText('name-two')).not.toBeInTheDocument();
    });
});

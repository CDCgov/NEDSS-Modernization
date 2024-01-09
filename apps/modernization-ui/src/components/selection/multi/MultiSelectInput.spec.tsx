import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { MultiSelectInput } from './MultiSelectInput';

describe('Given a MultiSelectInput component', () => {
    const options = [
        { name: 'label-one', value: '1' },
        { name: 'label-two', value: '2' },
        { name: 'label-three', value: '3' }
    ];

    it('should display options when clicked', async () => {
        const { getByRole, findByText } = render(
            <MultiSelectInput id="testing-multi-select" label="testing multi-select" options={options} />
        );

        const component = getByRole('combobox');

        userEvent.click(component);

        expect(await findByText('label-one')).toBeInTheDocument();
        expect(await findByText('label-two')).toBeInTheDocument();
        expect(await findByText('label-three')).toBeInTheDocument();
    });

    it('should display single selected value', () => {
        const { getByText, queryByText } = render(<MultiSelectInput options={options} value={['2']} />);

        expect(getByText('label-two')).toBeInTheDocument();

        expect(queryByText('label-one')).not.toBeInTheDocument();
        expect(queryByText('label-three')).not.toBeInTheDocument();
    });

    it('should display multiple selected value', () => {
        const { getByText, queryByText } = render(<MultiSelectInput options={options} value={['2', '1']} />);

        expect(getByText('label-two')).toBeInTheDocument();

        expect(queryByText('label-three')).not.toBeInTheDocument();
    });

    it('should remove the selected item when clicked', () => {
        const { queryByText, getByLabelText } = render(<MultiSelectInput options={options} value={['2']} />);

        const selected = getByLabelText('Remove label-two');

        userEvent.click(selected);

        expect(queryByText('label-two')).not.toBeInTheDocument();
    });
});

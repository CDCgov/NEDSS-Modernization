import { render, fireEvent, waitFor } from '@testing-library/react';

import { MultiSelectInput } from './MultiSelectInput';

describe('Given a MultiSelectInput component', () => {
    const options = [
        { name: 'label-one', value: '1' },
        { name: 'label-two', value: '2' },
        { name: 'label-three', value: '3' }
    ];

    it('should display options when clicked', () => {
        const { getByText, container } = render(<MultiSelectInput options={options} />);

        fireEvent.click(container);

        waitFor(() => {
            expect(getByText('label-one')).toBeInTheDocument();
            expect(getByText('label-two')).toBeInTheDocument();
            expect(getByText('label-three')).toBeInTheDocument();
        });
    });

    it('should display single selected value', async () => {
        const { queryByText, findByText } = render(<MultiSelectInput options={options} value={['2']} />);

        await findByText('label-two');

        expect(queryByText('label-one')).not.toBeInTheDocument();
        expect(queryByText('label-three')).not.toBeInTheDocument();
    });

    it('should display multiple selected value', async () => {
        const { queryByText, findByText } = render(<MultiSelectInput options={options} value={['2', '1']} />);

        await findByText('label-two');

        expect(queryByText('label-one')).toBeInTheDocument();

        expect(queryByText('label-three')).not.toBeInTheDocument();
    });

    it('should remove the selected item when clicked', async () => {
        const { findByText, queryByText } = render(<MultiSelectInput options={options} value={['2']} />);

        const selected = await findByText('label-two');

        fireEvent.click(selected);

        waitFor(() => {
            expect(queryByText('label-two')).not.toBeInTheDocument();
        });
    });
});

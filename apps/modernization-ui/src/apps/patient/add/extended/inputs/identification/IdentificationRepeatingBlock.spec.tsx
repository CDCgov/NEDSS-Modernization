import { vi } from 'vitest';
import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { IdentificationRepeatingBlock, IdentificationRepeatingBlockProps } from './IdentificationRepeatingBlock';

vi.mock('apps/patient/data/identification/useIdentificationCodedValues', () => ({
    useIdentificationCodedValues: () => ({
        types: [{ value: 'type-value', name: 'type-name' }],
        authorities: [{ value: 'authority-value', name: 'authority-name' }]
    })
}));

const Fixture = ({ values, onChange = jest.fn(), isDirty = jest.fn() }: Partial<IdentificationRepeatingBlockProps>) => (
    <IdentificationRepeatingBlock id="identifications" values={values} onChange={onChange} isDirty={isDirty} />
);

describe('IdentificationMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(
            <Fixture
                values={[{ asOf: '02/19/2023', type: { value: 'type-value', name: 'type-name' }, id: '12341241' }]}
            />
        );

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Authority');
        expect(headers[3]).toHaveTextContent('Value');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<Fixture />);

        const dateInput = getByLabelText('Identification as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = getByLabelText('Type');
        expect(race).toHaveValue('');

        const use = getByLabelText('Assigning authority');
        expect(use).toHaveValue('');

        const street1 = getByLabelText('ID value');
        expect(street1).toHaveValue('');
    });
});

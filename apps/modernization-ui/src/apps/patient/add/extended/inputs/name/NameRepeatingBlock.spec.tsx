import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { NameRepeatingBlock, NameRepeatingBlockProps } from './NameRepeatingBlock';

const Fixture = ({ values, onChange = jest.fn(), isDirty = jest.fn() }: Partial<NameRepeatingBlockProps>) => (
    <NameRepeatingBlock id="names" values={values} onChange={onChange} isDirty={isDirty} />
);

describe('NameRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(
            <Fixture
                values={[
                    {
                        asOf: '07/11/1997',
                        type: { name: 'type-name', value: 'type-value' }
                    }
                ]}
            />
        );

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Last');
        expect(headers[3]).toHaveTextContent('First');
        expect(headers[4]).toHaveTextContent('Middle');
        expect(headers[5]).toHaveTextContent('Suffix');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<Fixture />);

        const dateInput = getByLabelText('Name as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const type = getByLabelText('Type');
        expect(type).toHaveValue('');

        const last = getByLabelText('Last');
        expect(last).toHaveValue('');

        const prefix = getByLabelText('Prefix');
        expect(prefix).toHaveValue('');

        const secondLast = getByLabelText('Second last');
        expect(secondLast).toHaveValue('');

        const middle = getByLabelText('Middle');
        expect(middle).toHaveValue('');

        const secondMiddle = getByLabelText('Second middle');
        expect(secondMiddle).toHaveValue('');

        const first = getByLabelText('First');
        expect(first).toHaveValue('');

        const suffix = getByLabelText('Suffix');
        expect(suffix).toHaveValue('');

        const degree = getByLabelText('Degree');
        expect(degree).toHaveValue('');
    });
});

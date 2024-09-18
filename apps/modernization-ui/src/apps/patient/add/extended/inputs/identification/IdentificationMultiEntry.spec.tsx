import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { IdentificationMultiEntry } from './IdentificationMultiEntry';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));
const onChange = jest.fn();
const isDirty = jest.fn();

describe('IdentificationMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<IdentificationMultiEntry onChange={onChange} isDirty={isDirty} />);

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Authority');
        expect(headers[3]).toHaveTextContent('Value');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<IdentificationMultiEntry onChange={onChange} isDirty={isDirty} />);

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

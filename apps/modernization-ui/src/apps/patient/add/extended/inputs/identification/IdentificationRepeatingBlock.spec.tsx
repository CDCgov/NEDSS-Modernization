import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { IdentificationRepeatingBlock } from './IdentificationRepeatingBlock';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));

const mockEntry = {
    state: {
        data: [
            {
                asOf: internalizeDate(new Date()),
                type: 'AN',
                idValue: '12341241'
            }
        ]
    }
};

jest.mock('design-system/entry/multi-value/useMultiValueEntryState', () => ({
    useMultiValueEntryState: () => mockEntry
}));

const onChange = jest.fn();
const isDirty = jest.fn();

const Fixture = () => <IdentificationRepeatingBlock id="identifications" onChange={onChange} isDirty={isDirty} />;

describe('IdentificationMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<Fixture />);

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

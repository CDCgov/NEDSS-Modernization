import { render } from '@testing-library/react';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { BasicIdentificationRepeatingBlock } from './BasicIdentificationRepeatingBlock';

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

const Fixture = () => <BasicIdentificationRepeatingBlock id="identifications" onChange={onChange} isDirty={isDirty} />;

describe('BasicIdentificationRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<Fixture />);

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('Type');
        expect(headers[1]).toHaveTextContent('Authority');
        expect(headers[2]).toHaveTextContent('Value');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText } = render(<Fixture />);

        const type = getByLabelText('Type');
        expect(type).toHaveValue('');

        const authority = getByLabelText('Assigning authority');
        expect(authority).toHaveValue('');

        const id = getByLabelText('ID value');
        expect(id).toHaveValue('');
    });
});

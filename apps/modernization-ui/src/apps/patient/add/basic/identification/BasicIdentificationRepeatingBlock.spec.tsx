import { render } from '@testing-library/react';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import {
    BasicIdentificationRepeatingBlock,
    BasicIdentificationRepeatingBlockProps
} from './BasicIdentificationRepeatingBlock';

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));

const Fixture = ({
    values,
    onChange = jest.fn(),
    isDirty = jest.fn(),
    isValid = jest.fn()
}: Partial<BasicIdentificationRepeatingBlockProps>) => (
    <BasicIdentificationRepeatingBlock
        id="identifications"
        values={values}
        onChange={onChange}
        isDirty={isDirty}
        isValid={isValid}
    />
);

describe('BasicIdentificationRepeatingBlock', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(
            <Fixture
                values={[
                    {
                        type: { value: 'type-value', name: 'type-name' },
                        id: '12341241'
                    }
                ]}
            />
        );

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('Type');
        expect(headers[1]).toHaveTextContent('Assigning authority');
        expect(headers[2]).toHaveTextContent('ID value');
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

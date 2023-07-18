import { fireEvent, render } from '@testing-library/react';
import { EthnicityForm } from './EthnicityForm';

const values = {
    ethnicGroups: [
        { value: 'ethnicity-1', name: 'Ethnicity 1' },
        { value: 'ethnicity-2', name: 'Ethnicity 2' }
    ],
    ethnicityUnknownReasons: [{ value: 'unknown-reason', name: 'Unknown Reason' }],
    detailedEthnicities: [{ value: 'detailed-ethnicity-1', name: 'Detailed Ethnicity 1', group: 'ethnicity-1' }]
};

jest.mock('pages/patient/profile/ethnicity', () => ({
    ...jest.requireActual('pages/patient/profile/ethnicity'),
    usePatientEthnicityCodedValues: () => values
}));

describe('EthnicityForm', () => {
    const intial = { asOf: null, ethnicGroup: null, unknownReason: null, detailed: [] };

    it('Should renders table component', async () => {
        const { container } = render(<EthnicityForm entry={intial} onChanged={() => {}} onCancel={() => {}} />);

        const tableHeader = container.getElementsByClassName('label-text');
        expect(tableHeader[0]).toHaveTextContent('As of:');
    });

    it('should submit the form with the selected values', async () => {
        const handleChange = jest.fn();
        const handleCancel = jest.fn();

        const { getByText, getByLabelText } = render(
            <EthnicityForm entry={intial} onChanged={handleChange} onCancel={handleCancel} />
        );

        const datepicker = getByLabelText(/As of/);
        fireEvent.change(datepicker, { target: { value: '2022-01-01' } });

        // Select an ethnicity
        const ethnicitySelect = getByLabelText(/Ethnicity/);

        fireEvent.change(ethnicitySelect, { target: { value: 'ETH1' } });

        const saveButton = getByText('Save');
        fireEvent.click(saveButton);
        setTimeout(() => {
            expect(handleChange).toHaveBeenCalledWith({
                datepicker: '2022-01-01',
                ethnicity: 'ETH1'
            });
        });
    });

    it('should press the cancel button', () => {
        const handleChange = jest.fn();
        const handleCancel = jest.fn();

        const { getByTestId } = render(
            <EthnicityForm entry={intial} onChanged={handleChange} onCancel={handleCancel} />
        );
        const cancelButton = getByTestId('cancel-btn');
        fireEvent.click(cancelButton);

        setTimeout(() => {
            expect(handleCancel).toBeCalled();
        });
    });
});

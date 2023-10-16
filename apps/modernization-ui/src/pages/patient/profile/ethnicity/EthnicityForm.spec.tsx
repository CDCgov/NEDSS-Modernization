import { fireEvent, render, screen } from '@testing-library/react';
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

const mockHandleChange = jest.fn();
const mockHandleCancel = jest.fn();

describe('EthnicityForm', () => {
    const intial = { asOf: null, ethnicGroup: null, unknownReason: null, detailed: [] };

    it('Should renders table component', async () => {
        render(<EthnicityForm entry={intial} onChanged={() => {}} onCancel={() => {}} />);

        const tableHeader = await screen.findByText('As of:');

        expect(tableHeader).toBeTruthy();
    });

    it('should submit the form with the selected values', async () => {
        const { getByLabelText, getByText } = render(
            <EthnicityForm entry={intial} onChanged={mockHandleChange} onCancel={mockHandleCancel} />
        );

        const datepicker = getByLabelText(/As of/);

        fireEvent.change(datepicker, { target: { value: '2022-01-01' } });

        // Select an ethnicity
        const ethnicitySelect = await screen.findByPlaceholderText('-Select-');

        fireEvent.change(ethnicitySelect, { target: { value: 'ETH1' } });
        fireEvent.blur(ethnicitySelect);

        const saveButton = getByText('Save');
        expect(saveButton).toBeTruthy();

        fireEvent.click(saveButton);

        expect(mockHandleChange).toHaveBeenCalledWith({
            datepicker: '2022-01-01',
            ethnicity: 'ETH1'
        });
    });

    it.skip('should press the cancel button', async () => {
        const { getByTestId } = render(
            <EthnicityForm entry={intial} onChanged={mockHandleCancel} onCancel={mockHandleCancel} />
        );

        const cancelButton = await screen.findByTestId('cancel-btn');
        fireEvent.click(cancelButton);

        // expect(mockHandleCancel).toBeCalled();
    });
});

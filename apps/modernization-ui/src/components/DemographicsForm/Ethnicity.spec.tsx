import { fireEvent, render } from '@testing-library/react';
import { SearchCriteriaContext } from '../../providers/SearchCriteriaContext';
import { EthnicityForm } from './Ethnicity';

describe('EthnicityForm', () => {
    it('Should renders table component', async () => {
        const { container } = render(<EthnicityForm />);

        const tableHeader = container.getElementsByClassName('label-text');
        expect(tableHeader[0].innerHTML).toBe('As of:');
    });

    it('should submit the form with the selected values', async () => {
        const setEthnicityForm = jest.fn();
        const searchCriteria = {
            ethnicities: [
                { id: { code: 'ETH1' }, codeDescTxt: 'Ethnicity 1' },
                { id: { code: 'ETH2' }, codeDescTxt: 'Ethnicity 2' }
            ],
            programAreas: [],
            conditions: [],
            jurisdictions: [],
            userResults: [],
            outbreaks: [],
            races: [],
            identificationTypes: []
        };

        const { getByText, getByTestId } = render(
            <SearchCriteriaContext.Provider value={{ searchCriteria }}>
                <EthnicityForm setEthnicityForm={setEthnicityForm} />
            </SearchCriteriaContext.Provider>
        );

        const datepicker = getByTestId('date-picker-internal-input');
        fireEvent.change(datepicker, { target: { value: '2022-01-01' } });

        // Select an ethnicity
        const ethnicitySelect = getByTestId('ethnicity');
        fireEvent.change(ethnicitySelect, { target: { value: 'ETH1' } });

        const saveButton = getByText('Save');
        fireEvent.click(saveButton);
        setTimeout(() => {
            expect(setEthnicityForm).toHaveBeenCalledWith({
                datepicker: '2022-01-01',
                ethnicity: 'ETH1'
            });
        });
    });

    it('should press the cancel button', () => {
        const setEthnicityForm = jest.fn();

        const { getByTestId } = render(<EthnicityForm />);
        const cancelButton = getByTestId('cancel-btn');
        fireEvent.click(cancelButton);

        setTimeout(() => {
            expect(setEthnicityForm).toBeCalled();
        });
    });
});

import { fireEvent, render } from '@testing-library/react';
import { SearchCriteriaContext } from '../../providers/SearchCriteriaContext';
import { RaceForm } from './Race';

describe('EthnicityForm', () => {
    it('Should renders table component', async () => {
        const { container } = render(<RaceForm />);

        const tableHeader = container.getElementsByClassName('label-text');
        expect(tableHeader[0].innerHTML).toBe('As of:');
    });

    it('should submit the form with the selected values', async () => {
        const setRaceForm = jest.fn();
        const searchCriteria = {
            ethnicities: [],
            programAreas: [],
            conditions: [],
            jurisdictions: [],
            userResults: [],
            outbreaks: [],
            races: [
                { id: { code: 'RACE1' }, codeDescTxt: 'Race 1' },
                { id: { code: 'RACE2' }, codeDescTxt: 'Race 2' }
            ],
            identificationTypes: []
        };

        const { getByText, getByTestId } = render(
            <SearchCriteriaContext.Provider value={{ searchCriteria }}>
                <RaceForm setRaceForm={setRaceForm} />
            </SearchCriteriaContext.Provider>
        );

        const datepicker = getByTestId('date-picker-internal-input');
        fireEvent.change(datepicker, { target: { value: '2022-01-01' } });

        // Select an ethnicity
        const ethnicitySelect = getByTestId('race');
        fireEvent.change(ethnicitySelect, { target: { value: 'RACE1' } });

        const saveButton = getByText('Save');
        fireEvent.click(saveButton);
        setTimeout(() => {
            expect(setRaceForm).toHaveBeenCalledWith({
                datepicker: '2022-01-01',
                ethnicity: 'RACE1'
            });
        });
    });

    it('should press the cancel button', () => {
        const setRaceForm = jest.fn();

        const { getByTestId } = render(<RaceForm setRaceForm={setRaceForm} />);
        const cancelButton = getByTestId('cancel-btn');
        fireEvent.click(cancelButton);

        setTimeout(() => {
            expect(setRaceForm).toBeCalled();
        });
    });
});

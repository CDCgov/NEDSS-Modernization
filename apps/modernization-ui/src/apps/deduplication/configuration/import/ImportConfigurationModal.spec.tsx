import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BlockingAttribute, MatchingAttribute, MatchMethod } from 'apps/deduplication/api/model/Pass';
import { ImportConfigurationModal } from './ImportConfigurationModal';
import { AlgorithmExport } from 'apps/deduplication/api/model/AlgorithmExport';

const onImport = jest.fn();
const onCancel = jest.fn();

// mock file.text()
File.prototype.text = function () {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result as string);
        reader.onerror = () => reject(new Error('Failed to read file'));
        reader.readAsText(this);
    });
};

const Fixture = ({ visible = true }) => {
    return <ImportConfigurationModal visible={visible} onCancel={onCancel} onImport={onImport} />;
};
describe('ImportConfigurationModal', () => {
    it('should show proper heading', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Import configuration file')).toBeInTheDocument();
    });

    it('should show proper info', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Input accepts only JSON file type')).toBeInTheDocument();
    });

    it('should show proper label', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Drag configuration file here or choose from folder')).toBeInTheDocument();
    });

    it('should set file type', () => {
        const { getByLabelText } = render(<Fixture />);

        const input = getByLabelText('Drag configuration file here or choose from folder');
        expect(input).toHaveAttribute('accept', 'application/json');
    });

    it('should trigger on cancel when close button clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const close = getAllByRole('button')[0]; // close 'X'
        await user.click(close);
        expect(onCancel).toHaveBeenCalledTimes(1);
    });

    it('should trigger on cancel when cancel button clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const cancel = getAllByRole('button')[1];
        expect(cancel).toHaveTextContent('Cancel');
        await user.click(cancel);
        expect(onCancel).toHaveBeenCalledTimes(1);
    });

    it('should trigger import when valid configuration file uploaded', async () => {
        const user = userEvent.setup();

        const { getAllByRole, getByLabelText } = render(<Fixture />);

        const fileInput = getByLabelText('Drag configuration file here or choose from folder');
        const algorithmExport: AlgorithmExport = {
            dataElements: {
                firstName: { active: true, oddsRatio: 5.0, logOdds: 1.609437 }
            },
            algorithm: {
                passes: [
                    {
                        id: 1,
                        name: 'Pass A',
                        description: 'Some other description',
                        active: true,
                        blockingCriteria: [BlockingAttribute.BIRTHDATE],
                        matchingCriteria: [
                            { attribute: MatchingAttribute.FIRST_NAME, method: MatchMethod.JAROWINKLER },
                            { attribute: MatchingAttribute.LAST_NAME, method: MatchMethod.EXACT }
                        ],
                        lowerBound: 2.0,
                        upperBound: 3.0
                    }
                ]
            }
        };
        const fileContent = JSON.stringify(algorithmExport);
        const file = new File([fileContent], 'test.json', { type: 'application/json' });

        await user.upload(fileInput, file);
        const importButton = getAllByRole('button')[2];
        expect(importButton).toHaveTextContent('Import');

        await user.click(importButton);

        expect(onImport).toHaveBeenCalledTimes(1);
        expect(onImport).toHaveBeenCalledWith(algorithmExport);
    });

    it('should show warning when invalid configuration uploaded', async () => {
        const user = userEvent.setup();

        const { getAllByRole, getByLabelText, queryByText } = render(<Fixture />);

        const fileInput = getByLabelText('Drag configuration file here or choose from folder');

        const fileContent = 'Invalid Json';
        const file = new File([fileContent], 'test.json', { type: 'application/json' });

        await user.upload(fileInput, file);
        const importButton = getAllByRole('button')[2];
        expect(importButton).toHaveTextContent('Import');

        await user.click(importButton);

        expect(onImport).toHaveBeenCalledTimes(0);

        await waitFor(() => {
            expect(
                queryByText(
                    'The imported JSON file was invalid. Please review the file and ensure the file is the appropriate format and all values are valid.'
                )
            ).toBeInTheDocument();
        });
    });
});

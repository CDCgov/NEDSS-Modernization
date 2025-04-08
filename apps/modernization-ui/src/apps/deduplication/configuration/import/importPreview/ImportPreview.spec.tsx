import { AlgorithmExport } from 'apps/deduplication/api/model/AlgorithmExport';
import { ImportPreview } from './ImportPreview';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

const importedAlgorithm: AlgorithmExport = {
    dataElements: {},
    algorithm: { passes: [] }
};
const onAccept = jest.fn();
const onCancel = jest.fn();
const Fixture = () => {
    return <ImportPreview importedAlgorithm={importedAlgorithm} onAccept={onAccept} onCancel={onCancel} />;
};
describe('Import preview', () => {
    it('should have a cancel button', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const cancelButton = getAllByRole('button')[0];
        expect(cancelButton).toHaveTextContent('Cancel');

        await user.click(cancelButton);
        expect(onCancel).toHaveBeenCalledTimes(1);
    });

    it('should have an add configuration button', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const addButton = getAllByRole('button')[1];
        expect(addButton).toHaveTextContent('Add configuration');

        await user.click(addButton);
        expect(onAccept).toHaveBeenCalledTimes(1);
    });
});

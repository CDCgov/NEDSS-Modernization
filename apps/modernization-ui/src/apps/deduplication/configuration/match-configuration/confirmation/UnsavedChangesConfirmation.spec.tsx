import { render } from '@testing-library/react';
import { UnsavedChangesConfirmation } from './UnsavedChangesConfirmation';

const onAccept = jest.fn();
const onCancel = jest.fn();
describe('UnsavedChangesConfirmation', () => {
    it('should have the proper heading', () => {
        const { getByRole } = render(
            <UnsavedChangesConfirmation visible={true} passName="Pass name" onAccept={onAccept} onCancel={onCancel} />
        );

        expect(getByRole('heading')).toHaveTextContent('Unsaved changes');
    });

    it('should have the button text', () => {
        const { getAllByRole } = render(
            <UnsavedChangesConfirmation visible={true} passName="Pass name" onAccept={onAccept} onCancel={onCancel} />
        );

        expect(getAllByRole('button')[0]).toHaveTextContent('No, back to configuration');
        expect(getAllByRole('button')[1]).toHaveTextContent('Yes, leave');
    });
});

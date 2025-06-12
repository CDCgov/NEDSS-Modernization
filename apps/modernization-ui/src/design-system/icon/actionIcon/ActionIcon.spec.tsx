import { render } from '@testing-library/react';
import { axe } from 'vitest-axe';
import userEvent from '@testing-library/user-event';
import { ActionIcon } from './ActionIcon';

describe('ActionIcon', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<ActionIcon name="close" />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should trigger onAction when Enter key is pressed', async () => {
        const onAction = jest.fn();
        const user = userEvent.setup();

        render(<ActionIcon name="close" onAction={onAction} tabIndex={0} />);

        await user.tab();
        await user.keyboard('{Enter}');

        expect(onAction).toHaveBeenCalledTimes(1);
    });

    it('should trigger onAction when Space key is pressed', async () => {
        const onAction = jest.fn();
        const user = userEvent.setup();

        render(<ActionIcon name="close" onAction={onAction} tabIndex={0} />);

        await user.tab();
        await user.keyboard(' ');

        expect(onAction).toHaveBeenCalledTimes(1);
    });

    it('should not trigger onAction for other keys', async () => {
        const onAction = jest.fn();
        const user = userEvent.setup();

        render(<ActionIcon name="close" onAction={onAction} tabIndex={0} />);

        await user.tab();
        await user.keyboard('{Tab}');

        expect(onAction).not.toHaveBeenCalled();
    });
});

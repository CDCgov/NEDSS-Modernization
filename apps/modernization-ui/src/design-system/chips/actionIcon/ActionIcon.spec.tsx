import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import userEvent from '@testing-library/user-event';
import { ActionIcon } from './ActionIcon';

describe('ActionIcon', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<ActionIcon name="close" />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should trigger onKeyAction when Enter key is pressed', async () => {
        const onKeyAction = jest.fn();
        const user = userEvent.setup();

        render(<ActionIcon name="close" onKeyAction={onKeyAction} tabIndex={0} />);

        await user.tab();
        await user.keyboard('{Enter}');

        expect(onKeyAction).toHaveBeenCalledTimes(1);
    });

    it('should trigger onKeyAction when Space key is pressed', async () => {
        const onKeyAction = jest.fn();
        const user = userEvent.setup();

        render(<ActionIcon name="close" onKeyAction={onKeyAction} tabIndex={0} />);

        await user.tab();
        await user.keyboard(' ');

        expect(onKeyAction).toHaveBeenCalledTimes(1);
    });

    it('should not trigger onKeyAction for other keys', async () => {
        const onKeyAction = jest.fn();
        const user = userEvent.setup();

        render(<ActionIcon name="close" onKeyAction={onKeyAction} tabIndex={0} />);

        await user.tab();
        await user.keyboard('{Tab}');

        expect(onKeyAction).not.toHaveBeenCalled();
    });
});

import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { PersonMatchHeader } from './PersonMatchHeader';

const onImportClick = vi.fn();
const onNavClick = vi.fn();

describe('PersonMatchHeader', () => {
    it('should not render buttons by default', () => {
        const { queryByRole } = render(<PersonMatchHeader />);

        expect(queryByRole('button')).toBeNull();
    });

    it('should render buttons when enabled', () => {
        const { getAllByRole } = render(<PersonMatchHeader showButtons={true} />);

        const buttons = getAllByRole('button');
        expect(buttons).toHaveLength(3);
        expect(buttons[0]).toHaveTextContent('Configure data elements');
    });

    it('should trigger callbacks when buttons are clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(
            <PersonMatchHeader
                showButtons={true}
                onConfigureDataElementsClick={onNavClick}
                onImportClick={onImportClick}
            />
        );

        const buttons = getAllByRole('button');

        await user.click(buttons[0]);
        expect(onNavClick).toHaveBeenCalledTimes(1);

        await user.click(buttons[1]);
        expect(onImportClick).toHaveBeenCalledTimes(1);
    });
});

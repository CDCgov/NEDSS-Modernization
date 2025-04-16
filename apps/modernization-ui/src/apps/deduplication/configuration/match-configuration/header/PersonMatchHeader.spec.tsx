import { render } from '@testing-library/react';
import { PersonMatchHeader } from './PersonMatchHeader';
import userEvent from '@testing-library/user-event';

const onImportClick = jest.fn();
const onNavClick = jest.fn();

describe('PersonMatchHeader', () => {
    it('should not render buttons by default', () => {
        const { queryByRole } = render(<PersonMatchHeader />);

        expect(queryByRole('button')).toBeNull();
    });

    it('should render buttons when enabled', () => {
        const { getAllByRole } = render(<PersonMatchHeader showButtons />);

        const buttons = getAllByRole('button');
        expect(buttons).toHaveLength(3);
        expect(buttons[0]).toHaveTextContent('Configure data elements');
    });

    it('should trigger callbacks when buttons are clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(
            <PersonMatchHeader showButtons onConfigureDataElementsClick={onNavClick} onImportClick={onImportClick} />
        );

        const buttons = getAllByRole('button');

        await user.click(buttons[0]);
        expect(onNavClick).toHaveBeenCalledTimes(1);

        await user.click(buttons[1]);
        expect(onImportClick).toHaveBeenCalledTimes(1);
    });
});

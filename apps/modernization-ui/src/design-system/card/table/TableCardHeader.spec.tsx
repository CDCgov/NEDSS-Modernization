import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Button } from 'design-system/button';
import { TableCardHeader, TableCardHeaderProps } from './TableCardHeader';

// mock ColumnPreferencesPanel component
jest.mock('design-system/table/preferences/ColumnPreferencesPanel', () => ({
    ColumnPreferencesPanel: () => <div>Mocked Column Preferences Panel</div>
}));

const Fixture = (props: Partial<TableCardHeaderProps>) => {
    return (
        <TableCardHeader
            title="Test Title"
            level={2}
            subtext="Test Subtext"
            actions={
                <>
                    <Button>Action 1</Button>
                    <Button>Action 2</Button>
                </>
            }
            {...props}
        />
    );
};

describe('TableCardHeader', () => {
    it('renders the title and subtext', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Test Title')).toBeInTheDocument();
        expect(getByText('Test Subtext')).toBeInTheDocument();
    });

    it('renders the actions', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Action 1')).toBeInTheDocument();
        expect(getByText('Action 2')).toBeInTheDocument();
    });

    it('renders the settings button when showSettings is true', () => {
        const { getByLabelText } = render(<Fixture />);
        expect(getByLabelText('Settings')).toBeInTheDocument();
    });

    it('renders the correct heading level', () => {
        const { getByText } = render(<Fixture level={3} />);
        const heading = getByText('Test Title');
        expect(heading.tagName).toBe('H3');
    });

    it('renders without subtext if not provided', () => {
        const { queryByText } = render(<Fixture subtext={undefined} />);
        expect(queryByText('Test Subtext')).not.toBeInTheDocument();
    });

    it('renders without settings overlay panel by default', async () => {
        const { queryByText } = render(<Fixture />);
        expect(queryByText('Mocked Column Preferences Panel')).not.toBeInTheDocument();
    });

    it('shows settings overlay panel when settings button clicked', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const settingsButton = getByLabelText('Settings');
        const user = userEvent.setup();
        await user.click(settingsButton);
        expect(queryByText('Mocked Column Preferences Panel')).toBeInTheDocument();
    });

    it('renders the result count', () => {
        const { getByText } = render(<Fixture resultCount={10} />);
        expect(getByText('10')).toBeInTheDocument();
    });
});

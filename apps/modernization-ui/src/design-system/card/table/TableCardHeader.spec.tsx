import { render } from '@testing-library/react';
import { TableCardHeader, TableCardHeaderProps } from './TableCardHeader';

const Fixture = (props: Partial<TableCardHeaderProps>) => {
    return (
        <TableCardHeader
            title="Test Title"
            headingLevel={2}
            subtext="Test Subtext"
            actions={[
                { children: 'Action 1', onClick: jest.fn() },
                { children: 'Action 2', onClick: jest.fn() }
            ]}
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

    it('does not render the settings button when showSettings is false', () => {
        const { queryByLabelText } = render(<Fixture showSettings={false} />);
        expect(queryByLabelText('Settings')).not.toBeInTheDocument();
    });

    it('renders the correct heading level', () => {
        const { getByText } = render(<Fixture headingLevel={3} />);
        const heading = getByText('Test Title');
        expect(heading.tagName).toBe('H3');
    });

    it('renders without subtext if not provided', () => {
        const { queryByText } = render(<Fixture subtext={undefined} />);
        expect(queryByText('Test Subtext')).not.toBeInTheDocument();
    });

    it('renders without actions if not provided', () => {
        const { queryByText } = render(<Fixture actions={undefined} />);
        expect(queryByText('Action 1')).not.toBeInTheDocument();
        expect(queryByText('Action 2')).not.toBeInTheDocument();
    });

    it('renders the result count', () => {
        const { getByText } = render(<Fixture resultCount={10} />);
        expect(getByText('10')).toBeInTheDocument();
    });
});

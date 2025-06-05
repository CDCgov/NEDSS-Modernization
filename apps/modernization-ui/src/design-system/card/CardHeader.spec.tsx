import { Button } from 'design-system/button';
import { CardHeader, CardHeaderProps } from './CardHeader';
import { render, screen } from '@testing-library/react';
import { HeadingLevel } from 'components/heading';

const Fixture = (props: Partial<CardHeaderProps>) => {
    return <CardHeader id="testing" title="Test Title" level={2} {...props} />;
};

describe('CardHeader', () => {
    it('renders the title', () => {
        const { getByText } = render(<Fixture title="Title text" />);
        expect(getByText('Title text')).toBeInTheDocument();
    });

    it('renders the subtext', () => {
        const { getByText } = render(<Fixture subtext="Test Subtext" />);

        expect(getByText('Test Subtext')).toBeInTheDocument();
    });

    it.each([1, 2, 3, 4, 5])('renders the heading level %d', (value) => {
        const { getByText } = render(<Fixture level={value as HeadingLevel} />);
        const heading = getByText('Test Title');
        expect(heading.tagName).toBe(`H${value}`);
    });

    it('renders the actions', () => {
        render(
            <Fixture
                actions={
                    <>
                        <Button>Action 1</Button>
                        <Button>Action 2</Button>
                    </>
                }
            />
        );

        expect(screen.getByRole('button', { name: 'Action 1' })).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Action 2' })).toBeInTheDocument();
    });
});

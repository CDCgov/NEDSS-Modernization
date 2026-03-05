import { render } from '@testing-library/react';
import { Tag } from './Tag';
import { axe } from 'jest-axe';

describe('Tag', () => {
    test('should render with no accessibility violations', async () => {
        const { container } = render(<Tag variant="default">Test Content</Tag>);
        expect(await axe(container)).toHaveNoViolations();
    });

    test('renders children correctly', () => {
        const { getByText } = render(<Tag variant="default">Test Content</Tag>);
        expect(getByText('Test Content')).toBeInTheDocument();
    });

    test('uses default props when not specified', () => {
        const { getByText } = render(<Tag variant="default">Default Tag</Tag>);
        expect(getByText('Default Tag')).toHaveClass('medium');
        expect(getByText('Default Tag')).toHaveClass('regular');
    });
});

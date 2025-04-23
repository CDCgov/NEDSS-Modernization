import { render } from '@testing-library/react';
import { Tag } from './Tag';

describe('Tag', () => {
    test('renders children correctly', () => {
        const { getByText } = render(<Tag variant="default">Test Content</Tag>);
        expect(getByText('Test Content')).toBeInTheDocument();
    });

    test('applies variant class correctly', () => {
        const { container } = render(<Tag variant="success">Success Tag</Tag>);
        expect(container.firstChild).toHaveClass('success');
    });

    test('applies size class correctly', () => {
        const { container } = render(
            <Tag variant="default" size="small">
                Small Tag
            </Tag>
        );
        expect(container.firstChild).toHaveClass('small');
    });

    test('applies weight class correctly', () => {
        const { container } = render(
            <Tag variant="default" weight="bold">
                Bold Tag
            </Tag>
        );
        expect(container.firstChild).toHaveClass('bold');
    });

    test('uses default props when not specified', () => {
        const { container } = render(<Tag variant="default">Default Tag</Tag>);
        expect(container.firstChild).toHaveClass('medium');
        expect(container.firstChild).toHaveClass('regular');
    });

    test('combines multiple props correctly', () => {
        const { container } = render(
            <Tag variant="error" size="large" weight="bold">
                Error Tag
            </Tag>
        );
        expect(container.firstChild).toHaveClass('error');
        expect(container.firstChild).toHaveClass('large');
        expect(container.firstChild).toHaveClass('bold');
    });

    test('renders multiple children', () => {
        const { getByText } = render(
            <Tag variant="info">
                <span>First</span>
                <span>Second</span>
            </Tag>
        );
        expect(getByText('First')).toBeInTheDocument();
        expect(getByText('Second')).toBeInTheDocument();
    });
});

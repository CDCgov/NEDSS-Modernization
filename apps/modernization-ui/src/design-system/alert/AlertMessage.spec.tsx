import { render } from '@testing-library/react';
import { AlertMessage } from './AlertMessage';

describe('AlertMessage', () => {
    // Title
    it('should render title if present', () => {
        const { getByRole } = render(
            <AlertMessage title="The title" type="information">
                Content
            </AlertMessage>
        );

        const heading = getByRole('heading');
        expect(heading).toHaveTextContent('The title');
    });

    it('should not render title if not present', () => {
        const { queryByRole } = render(<AlertMessage type="information">Content</AlertMessage>);

        const heading = queryByRole('heading');
        expect(heading).not.toBeInTheDocument();
    });

    // Content
    it('should render content', () => {
        const { getByText } = render(<AlertMessage type="information">The content goes here</AlertMessage>);

        const content = getByText('The content goes here');
        expect(content).toBeInTheDocument();
    });

    // Icon
    it('should render the information icon', () => {
        const { container } = render(<AlertMessage type="information">Content</AlertMessage>);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#info');
    });

    it('should render the success icon', () => {
        const { container } = render(<AlertMessage type="success">Content</AlertMessage>);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#check_circle');
    });

    it('should render the warning icon', () => {
        const { container } = render(<AlertMessage type="warning">Content</AlertMessage>);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#warning');
    });

    it('should render the error icon', () => {
        const { container } = render(<AlertMessage type="error">Content</AlertMessage>);

        const icon = container.querySelector('svg use');
        expect(icon).toHaveAttribute('xlink:href', 'undefined#error');
    });

    it('should render the medium icon by default', () => {
        const { container } = render(<AlertMessage type="information">Content</AlertMessage>);

        const icon = container.querySelector('svg');
        expect(icon).toHaveClass('medium');
    });

    it('should render the small icon if slim is specified', () => {
        const { container } = render(
            <AlertMessage type="information" slim>
                Content
            </AlertMessage>
        );

        const icon = container.querySelector('svg');
        expect(icon).toHaveClass('small');
    });

    it('should not render the icon if iconless is specified', () => {
        const { container } = render(
            <AlertMessage type="information" iconless>
                Content
            </AlertMessage>
        );

        const icon = container.querySelector('svg');
        expect(icon).toBeNull();
    });
    
    it('should set aria-label from title and string children when no aria-label prop provided', () => {
        const { getByRole } = render(
            <AlertMessage title="The title" type="information">
                The content goes here
            </AlertMessage>
        );

        expect(getByRole('alert', { name: 'The title. The content goes here' })).toBeInTheDocument();
    });

    it('should set aria-label from custom aria-label prop when provided', () => {
        const { getByRole } = render(
            <AlertMessage title="The title" type="information" aria-label="Custom aria label">
                The content goes here
            </AlertMessage>
        );

        expect(getByRole('alert', { name: 'The title. Custom aria label' })).toBeInTheDocument();
    });
});

import React from 'react';
import { render, screen } from '@testing-library/react';
import { Button } from './Button';

describe('Button component tests', () => {
    it('should render the button with the label', () => {
        render(<Button>Click me</Button>);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
    });

    it('should render the button with the icon', () => {
        render(<Button icon={<span>icon</span>} />);
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label and icon', () => {
        render(<Button icon={<span>icon</span>}>Click me</Button>);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label on the left', () => {
        render(
            <Button icon={<span>icon</span>} labelPosition="left">
                Click me
            </Button>
        );
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label on the right', () => {
        render(
            <Button icon={<span>icon</span>} labelPosition="right">
                Click me
            </Button>
        );
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label and icon and label on the right', () => {
        render(
            <Button icon={<span>icon</span>} labelPosition="right">
                Click me
            </Button>
        );
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label and icon and label on the left', () => {
        render(
            <Button icon={<span>icon</span>} labelPosition="left">
                Click me
            </Button>
        );
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('renders the destructive className when set', () => {
        render(<Button destructive>Destructive</Button>);
        expect(screen.getByRole('button')).toHaveClass('destructive');
    });

    it('renders the secondary className when set', () => {
        render(<Button secondary>Destructive</Button>);
        expect(screen.getByRole('button')).toHaveClass('secondary');
    });

    it('renders the secondary and destructive classNames when set', () => {
        render(
            <Button destructive secondary>
                Destructive
            </Button>
        );
        expect(screen.getByRole('button')).toHaveClass('secondary');
        expect(screen.getByRole('button')).toHaveClass('destructive');
    });

    it('renders the tertiary className when set', () => {
        render(<Button tertiary>tertiary</Button>);
        expect(screen.getByRole('button')).toHaveClass('tertiary');
    });
});

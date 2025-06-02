import React from 'react';
import { render, screen, within } from '@testing-library/react';
import { Button } from './Button';

describe('Button component tests', () => {
    it('should render the button with the label', () => {
        render(<Button>Click me</Button>);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
    });

    it('should render the button with the icon', () => {
        render(<Button icon="add" labelPosition="right" />);
        const button = screen.getByRole('button');

        const icon = within(button).getByRole('img', { hidden: true });

        expect(icon.querySelector('use')?.getAttribute('xlink:href')).toContain('#add');
    });

    it('should render the button with the label and icon', () => {
        render(
            <Button icon="add" labelPosition="right">
                Click me
            </Button>
        );
        const button = screen.getByRole('button');

        expect(button).toHaveTextContent('Click me');

        const icon = within(button).getByRole('img', { hidden: true });

        expect(icon.querySelector('use')?.getAttribute('xlink:href')).toContain('#add');
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

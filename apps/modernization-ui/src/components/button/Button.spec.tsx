import React from 'react';
import { render, screen } from '@testing-library/react';
import { Button } from './Button';

describe('Button component tests', () => {
    it('should render the button with the label', () => {
        render(<Button label="Click me" />);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
    });

    it('should render the button with the icon', () => {
        render(<Button icon={<span>icon</span>} />);
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label and icon', () => {
        render(<Button label="Click me" icon={<span>icon</span>} />);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label on the left', () => {
        render(<Button label="Click me" icon={<span>icon</span>} labelPosition="left" />);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label on the right', () => {
        render(<Button label="Click me" icon={<span>icon</span>} labelPosition="right" />);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label and icon and label on the right', () => {
        render(<Button label="Click me" icon={<span>icon</span>} labelPosition="right" />);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });

    it('should render the button with the label and icon and label on the left', () => {
        render(<Button label="Click me" icon={<span>icon</span>} labelPosition="left" />);
        expect(screen.getByRole('button')).toHaveTextContent('Click me');
        expect(screen.getByRole('button')).toContainHTML('<span>icon</span>');
    });
});

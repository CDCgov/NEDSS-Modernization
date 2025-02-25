import React from 'react';
import { render } from '@testing-library/react';
// import '@testing-library/jest-dom/extend-expect';
import { Icon } from './Icon';

describe('Icon component', () => {
    it('applies the correct class names', () => {
        const { container } = render(<Icon name="calendar" className="custom-class" />);
        const svgElement = container.querySelector('svg');
        expect(svgElement).toHaveClass('custom-class');
    });

    it('applies sizing class when sizing prop is provided', () => {
        const { container } = render(<Icon name="calendar" sizing="large" />);
        const svgElement = container.querySelector('svg');
        expect(svgElement).toHaveClass('large');
    });

    it('applies sizing class when fallback size prop is provided', () => {
        const { container } = render(<Icon name="calendar" size="small" />);
        const svgElement = container.querySelector('svg');
        expect(svgElement).toHaveClass('small');
    });

    it('sets aria-hidden to true when aria-label and aria-labelledby are not provided', () => {
        const { container } = render(<Icon name="calendar" />);
        const svgElement = container.querySelector('svg');
        expect(svgElement).toHaveAttribute('aria-hidden', 'true');
    });
});

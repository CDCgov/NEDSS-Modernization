import React from 'react';
import { render } from '@testing-library/react';
import { NoData } from './NoData';

describe('NoData Component', () => {
    it('should display "No Data" text by default', () => {
        const { getByText, container } = render(<NoData />);
        const span = container.querySelector('span');
        expect(getByText('No Data')).toBeInTheDocument();
        expect(span).toHaveClass('no-data');
    });

    it('should apply custom className', () => {
        const { container } = render(<NoData className="custom-class" />);
        const span = container.querySelector('span');
        expect(span).toHaveClass('custom-class');
    });

    it('should display dashes when display prop is "dashes"', () => {
        const { getByText } = render(<NoData display="dashes" />);
        expect(getByText('---')).toBeInTheDocument();
    });

    it('should display whitespace when display prop is "whitespace"', () => {
        const { container } = render(<NoData display="whitespace" />);
        const span = container.querySelector('span');
        expect(span?.innerHTML.trim()).toBe('&nbsp;');
    });
});

import { render, within } from '@testing-library/react';
import { Banner } from './banner';

describe('Banner component tests', () => {
    it('should render banner children inside the div tag', () => {
        const { container } = render(
            <Banner className="custom-class" />
        );
        expect(container.firstChild).toHaveClass('usa-banner custom-class');
        expect(container.querySelector('div')).toHaveClass('usa-accordion');
    });
});
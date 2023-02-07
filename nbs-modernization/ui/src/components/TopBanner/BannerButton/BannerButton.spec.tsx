import { render } from '@testing-library/react';
import { BannerButton } from './BannerButton';

describe('BannerButton component tests', () => {
    it('should render a banner button', () => {
        const { container } = render(<BannerButton type="button" className="custom-class" isOpen />);
        expect(container.firstChild).toHaveClass('usa-accordion__button usa-banner__button custom-class');
        expect(container.querySelector('span')).toHaveClass('usa-banner__button-text');
    });
});
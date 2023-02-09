import { render } from '@testing-library/react';
import { BannerFlag } from './BannerFlag';

describe('BannerFlag component tests', () => {
    it('should render an image in the banner header', () => {
        const { container } = render(<BannerFlag className="custom-class" />);
        expect(container.firstChild).toHaveClass('usa-banner__header-flag custom-class');
    });
});
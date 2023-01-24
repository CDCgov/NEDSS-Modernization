import { render } from '@testing-library/react';
import { BannerIcon } from './BannerIcon';

describe('BannerIcon component tests', () => {
    it('should render image icon in the banner', () => {
        const { container } = render(<BannerIcon className="custom-class" />);
        expect(container.firstChild).toHaveClass('usa-banner__icon usa-media-block__img custom-class');
    });
});
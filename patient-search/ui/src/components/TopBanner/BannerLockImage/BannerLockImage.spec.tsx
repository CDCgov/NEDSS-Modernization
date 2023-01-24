import { render } from '@testing-library/react';
import { BannerLockImage } from './BannerLockImage';

describe('BannerLockImage component tests', () => {
    it('should render an svg image inside the span', () => {
        const { container } = render(<BannerLockImage className="custom-class" title="sample image" description="sample description" />)
        expect(container.firstChild).toHaveClass('icon-lock custom-class');
        expect(container.querySelector('title')?.innerHTML).toBe('sample image');
    });
});
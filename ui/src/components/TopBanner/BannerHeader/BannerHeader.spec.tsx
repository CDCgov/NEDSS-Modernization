import { render } from '@testing-library/react';
import { BannerButton } from '../BannerButton/BannerButton';
import { BannerHeader } from './BannerHeader';

describe('BannerHeader component tests', () => {
    it('should render banner header', () => {
        const { container } = render(<BannerHeader className="custom-class" isOpen flagImg headerText headerActionText>
            <BannerButton isOpen>Sample Button</BannerButton>
        </BannerHeader>)
        expect(container.firstChild).toHaveClass('usa-banner__header usa-banner__header--expanded custom-class');
        expect(container.querySelector('span')?.innerHTML).toBe('Sample Button');
    });
});
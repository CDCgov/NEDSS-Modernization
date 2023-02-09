import { render } from '@testing-library/react';
import { MediaBlockBody } from '../../MediaBlockBody/MediaBlockBody';
import { BannerGuidance } from '../BannerGuidance/BannerGuidance';
import { BannerIcon } from '../BannerIcon/BannerIcon';
import { BannerContent } from './BannerContent';

describe('BannerContent component tests', () => {
    it('should render content of the banner', () => {
        const { container } = render(<BannerContent className="custom-class" isOpen>
            <div>
                <BannerGuidance>
                    <BannerIcon />
                    <MediaBlockBody>
                        <p>
                            Some dummy text
                        </p>
                    </MediaBlockBody>
                </BannerGuidance>
            </div>
        </BannerContent>);

        expect(container.firstChild).toHaveClass('usa-banner__content usa-accordion__content maxw-full custom-class');
    });
});
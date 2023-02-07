import { render } from '@testing-library/react';
import { MediaBlockBody } from '../../MediaBlockBody/MediaBlockBody';
import { BannerIcon } from '../BannerIcon/BannerIcon';
import { BannerGuidance } from './BannerGuidance';

describe('BannerGuidance component tests', () => {
    it('should render banner guidance children inside a div', () => {
        const { container } = render(<BannerGuidance className="custom-class">
            <BannerIcon />
            <MediaBlockBody>
                <p>Some sample text</p>
            </MediaBlockBody>
        </BannerGuidance>);
        expect(container.firstChild).toHaveClass('usa-banner__guidance custom-class');
    });
});
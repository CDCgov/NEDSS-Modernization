import { MediaBlockBody } from './MediaBlockBody';
import { render } from '@testing-library/react';

describe('MediaBlockBody component tests', () => {
    it('should render media block children inside the div tag', () => {
        const { container, getByLabelText } = render(
            <MediaBlockBody className="custom-class">
                <p>Some content</p>
            </MediaBlockBody>
        );
        expect(container.firstChild).toHaveClass('usa-media-block__body custom-class');
    })
});
import { render } from '@testing-library/react';
import RichTooltip from './RichTooltip';

describe('when a rich tooltip is displayed', () => {
    it('should render with no errors.', async () => {
        const mockRichTooltipAnchorRef: React.RefObject<HTMLElement> = {
            current: document.createElement('div', { is: 'mockRichTooltipAnchorRef' })
        };
        const { container } = render(
            <div>
                <RichTooltip elementRef={mockRichTooltipAnchorRef}>Contents</RichTooltip>
            </div>
        );

        expect(container).toBeTruthy();
        expect(mockRichTooltipAnchorRef.current).toBeTruthy();
    });
});

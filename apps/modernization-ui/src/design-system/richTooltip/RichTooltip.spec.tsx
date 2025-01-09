import { render } from '@testing-library/react';
import RichTooltip from './RichTooltip';
import { useRef } from 'react';
import userEvent from '@testing-library/user-event';

describe('when a rich tooltip is displayed', () => {
    it('should render with no errors by default.', async () => {
        const mockRichTooltipAnchorRef: React.RefObject<HTMLElement> = {
            current: document.createElement('div', { is: 'mockRichTooltipAnchorRef' })
        };
        const { container } = render(
            <div>
                <RichTooltip anchorRef={mockRichTooltipAnchorRef}>Contents</RichTooltip>
            </div>
        );

        expect(container).toBeTruthy();
        expect(mockRichTooltipAnchorRef.current).toBeTruthy();
    });

    it('should render with no errors when a mouse enter event triggers the popup message.', async () => {
        const MockRichTooltipAnchorElement = () => {
            const mockRichTooltipAnchorRef = useRef<HTMLDivElement>(null);
            return (
                <div>
                    <div ref={mockRichTooltipAnchorRef} data-testid="mock-rich-tooltip-anchor-div">
                        <p>Mock Anchor Element for Tooltip</p>
                    </div>
                    <div>
                        <RichTooltip anchorRef={mockRichTooltipAnchorRef}>
                            <p>Tooltip Content Text</p>
                        </RichTooltip>
                    </div>
                </div>
            );
        };

        const { container, getByTestId } = render(<MockRichTooltipAnchorElement />);
        userEvent.hover(getByTestId('mock-rich-tooltip-anchor-div'));
        expect(container).toBeTruthy();
    });

    it('should properly display the children props passed into the component.', async () => {
        const MockRichTooltipAnchorElement = () => {
            const mockRichTooltipAnchorRef = useRef<HTMLDivElement>(null);
            return (
                <div>
                    <div ref={mockRichTooltipAnchorRef} data-testid="mock-rich-tooltip-anchor-div">
                        <p>Mock Anchor Element for Tooltip</p>
                    </div>
                    <div>
                        <RichTooltip anchorRef={mockRichTooltipAnchorRef}>
                            <p>Tooltip Content Text</p>
                        </RichTooltip>
                    </div>
                </div>
            );
        };

        const { getByTestId, findByText } = render(<MockRichTooltipAnchorElement />);
        userEvent.hover(getByTestId('mock-rich-tooltip-anchor-div'));
        expect(await findByText('Tooltip Content Text')).toBeInTheDocument();
        expect(await findByText('Tooltip Content Text')).toBeVisible();
    });

    it('should not display the children props passed into the component when the mouse is not over the tooltip.', async () => {
        const MockRichTooltipAnchorElement = () => {
            const mockRichTooltipAnchorRef = useRef<HTMLDivElement>(null);
            return (
                <div>
                    <div ref={mockRichTooltipAnchorRef} data-testid="mock-rich-tooltip-anchor-div">
                        <p>Mock Anchor Element for Tooltip</p>
                    </div>
                    <div data-testid="mock-rich-tooltip-container-div">
                        <RichTooltip anchorRef={mockRichTooltipAnchorRef}>
                            <p>Tooltip Content Text</p>
                        </RichTooltip>
                    </div>
                </div>
            );
        };

        const { getByTestId } = render(<MockRichTooltipAnchorElement />);
        userEvent.hover(getByTestId('mock-rich-tooltip-anchor-div'));
        userEvent.unhover(getByTestId('mock-rich-tooltip-anchor-div'));
        expect(getByTestId('mock-rich-tooltip-container-div')).toBeEmptyDOMElement();
    });
});

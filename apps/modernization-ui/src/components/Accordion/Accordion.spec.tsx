import { Accordion } from './Accordion';
import { render } from '@testing-library/react';

describe('when Accordion renders', () => {
    it('should properly link button and panel with matching ids', () => {
        const { container } = render(
            <Accordion title="test" open>
                <h1>Content</h1>
            </Accordion>
        );

        const button = container.querySelector('button');
        const panel = container.querySelector('[role="region"]');

        expect(button?.getAttribute('aria-controls')).toBe(panel?.getAttribute('id'));
        expect(panel?.getAttribute('aria-labelledby')).toBe(button?.getAttribute('id'));
    });

    it('should have role region for accessibility announcement', () => {
        const { container } = render(
            <Accordion title="test" open>
                <h1>Content</h1>
            </Accordion>
        );

        const panel = container.querySelector('[role="region"]');
        expect(panel).toBeInTheDocument();
        expect(panel?.getAttribute('role')).toBe('region');
    });
});

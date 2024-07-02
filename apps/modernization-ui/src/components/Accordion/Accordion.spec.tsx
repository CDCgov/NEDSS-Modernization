import { Accordion } from './Accordion';
import { render } from '@testing-library/react';

describe('when Accordion renders', () => {
    const { container } = render(<Accordion title="test" open><h1>Content</h1></Accordion>);
    it('should be open', () => {
        const details = container.getElementsByTagName('summary');
        expect(details).toBeInTheDocument;
    });
});

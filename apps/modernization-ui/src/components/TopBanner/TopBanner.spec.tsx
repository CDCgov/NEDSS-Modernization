import { render } from '@testing-library/react';
import { TopBanner } from './TopBanner';

describe('TopBanner component tests', () => {
    it('should render top banner header and content inside the banner', () => {
        const { container, getByLabelText } = render(<TopBanner />);
        expect(container.querySelector('strong')?.innerHTML).toBe('An official website of the United States government');
    });
});
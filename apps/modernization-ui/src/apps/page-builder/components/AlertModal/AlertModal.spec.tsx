import { render } from '@testing-library/react';
import { AlertModal } from './AlertModal';

describe('when AlertModal renders', () => {
    it('should display success', () => {
        const { container } = render(<AlertModal type="success" />);
        expect(container.getElementsByClassName('success').length).toBe(1);
    });
});

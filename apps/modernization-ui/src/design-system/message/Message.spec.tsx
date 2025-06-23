import { render } from '@testing-library/react';
import { axe } from 'vitest-axe';

import { Message } from './Message';

describe('when a message is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Message type="information">A message</Message>);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display a message', () => {
        const { getByText } = render(<Message type="information">A message</Message>);

        const message = getByText('A message');

        expect(message).toBeInTheDocument();
    });
});

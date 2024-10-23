import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import RichTooltip from './RichTooltip';

describe('when a rich tooltip is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <RichTooltip labelTitle="Test label title" labelText="Test label text">
                Contents
            </RichTooltip>
        );

        expect(await axe(container)).toHaveNoViolations();
    });
});

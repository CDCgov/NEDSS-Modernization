import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import ExtendedTooltip from './ExtendedTooltip';

describe('when an extended tooltip is displayed', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <ExtendedTooltip labelTitle="Test label title" labelText="Test label text">
                Contents
            </ExtendedTooltip>
        );

        expect(await axe(container)).toHaveNoViolations();
    });
});

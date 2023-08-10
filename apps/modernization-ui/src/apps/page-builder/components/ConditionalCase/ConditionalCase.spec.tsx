import { render } from '@testing-library/react';
import { ConditionalCase } from './ConditionalCase';
import { AlertProvider } from '../../../../alert';
import { PageProvider } from 'page';

describe('General information component tests', () => {
    it('should display condition form', () => {
        const { getByTestId } = render(
            <AlertProvider>
                <PageProvider>
                    <ConditionalCase />
                </PageProvider>
            </AlertProvider>
        );
        expect(getByTestId('header-title').innerHTML).toContain('Jurisdictional Questions');
    });
});

import { PatientFileLayout } from './PatientFileLayout';
import { SkipLinkProvider } from 'SkipLink';
import { render, screen } from '@testing-library/react';

describe('PatientFileLayout', () => {
    it('should focus the header when skip link is clicked', async () => {
        render(
            <SkipLinkProvider>
                <PatientFileLayout
                    patient={{
                        id: 17,
                        patientId: 397,
                        local: 'local-id-value',
                        status: 'ACTIVE',
                        deletability: 'Deletable',
                        name: {
                            first: 'John',
                            last: 'Doe'
                        }
                    }}
                    navigation={() => <div>Nav</div>}>
                    <div>Content</div>
                </PatientFileLayout>
            </SkipLinkProvider>
        );
        const skipLink = screen.getByRole('link', { name: /skip to main content/i });
        const header = screen.getByRole('banner');

        expect(skipLink.getAttribute('href')).toBe(`#${header.id}`);
    });
});

import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { PagesResponse } from 'apps/page-builder/generated';

import { PageManagementProvider } from '../../usePageManagement';

import { PublishPage } from './PublishPage';

// Mock the PagePublishControllerService and PageInformationService to prevent fetch/network calls
vi.mock('apps/page-builder/generated', () => ({
    PagePublishControllerService: {
        publishPage: vi.fn().mockResolvedValue({}),
    },
    PageInformationService: {
        find: vi.fn().mockResolvedValue({ conditions: [] }),
    },
}));

describe('When PublishPage renders', () => {
    const modalRef = { current: null };
    const content: PagesResponse = {
        id: 123,
        name: 'Test Page',
        status: 'status',
        tabs: [
            {
                id: 123456,
                name: 'Test Tab',
                visible: true,
                order: 1,
                sections: [
                    {
                        id: 1234,
                        name: 'Section1',
                        visible: true,
                        order: 1,
                        subSections: [],
                    },
                    {
                        id: 5678,
                        name: 'Section2',
                        visible: true,
                        order: 2,
                        subSections: [],
                    },
                ],
            },
        ],
    };
    it('should display textarea', async () => {
        const { findByLabelText } = render(
            <PageManagementProvider page={content} fetch={vi.fn()} refresh={vi.fn()} loading={false}>
                <AlertProvider>
                    <PublishPage modalRef={modalRef} />
                </AlertProvider>
            </PageManagementProvider>
        );
        expect(await findByLabelText(/Version notes/)).toHaveRole('textbox');
    });
});

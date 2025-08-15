import { AlertProvider } from 'alert';
import { PageManagementProvider } from '../../usePageManagement';
import { PublishPage } from './PublishPage';
import { render } from '@testing-library/react';
import { PagesResponse } from 'apps/page-builder/generated';
import { vi } from 'vitest';

// Mock the PagePublishControllerService and PageInformationService to prevent fetch/network calls
vi.mock('apps/page-builder/generated', () => ({
    PagePublishControllerService: {
        publishPage: vi.fn().mockResolvedValue({})
    },
    PageInformationService: {
        find: vi.fn().mockResolvedValue({ conditions: [] })
    }
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
                        subSections: []
                    },
                    {
                        id: 5678,
                        name: 'Section2',
                        visible: true,
                        order: 2,
                        subSections: []
                    }
                ]
            }
        ]
    };
    it('should display textarea', () => {
        const { container } = render(
            <PageManagementProvider page={content} fetch={jest.fn()} refresh={jest.fn()} loading={false}>
                <AlertProvider>
                    <PublishPage modalRef={modalRef} />
                </AlertProvider>
            </PageManagementProvider>
        );
        const input = container.getElementsByTagName('textarea');
        expect(input).toHaveLength(1);
    });
    it('should display label', () => {
        const { container } = render(
            <PageManagementProvider page={content} fetch={jest.fn()} refresh={jest.fn()} loading={false}>
                <AlertProvider>
                    <PublishPage modalRef={modalRef} />
                </AlertProvider>
            </PageManagementProvider>
        );
        const label = container.getElementsByTagName('label');
        expect(label).toHaveLength(1);
    });
});

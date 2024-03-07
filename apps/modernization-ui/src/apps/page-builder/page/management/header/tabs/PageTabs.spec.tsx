import { render } from '@testing-library/react';
import { PagesResponse } from 'apps/page-builder/generated';
import { PageTabs } from './PageTabs';
import { PageManagementProvider } from '../../usePageManagement';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

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

describe('When PageTabs renders', () => {
    it('should display the Manage Tabs button when passed onAddSuccess', () => {
        const { container } = render(
            <DragDropProvider pageData={content}>
                <PageManagementProvider page={content} fetch={jest.fn()} refresh={jest.fn()} loading={false}>
                    <PageTabs pageId={999} tabs={content.tabs!} onAddSuccess={jest.fn()} />
                </PageManagementProvider>
            </DragDropProvider>
        );
        const button = container.getElementsByTagName('button');
        expect(button[0]).toBeInTheDocument();
    });
    it('should not display the Manage Tabs button when not passed onAddSuccess', () => {
        const { container } = render(
            <DragDropProvider pageData={content}>
                <PageManagementProvider page={content} fetch={jest.fn()} refresh={jest.fn()} loading={false}>
                    <PageTabs pageId={999} tabs={content.tabs!} />
                </PageManagementProvider>
            </DragDropProvider>
        );
        const button = container.getElementsByTagName('button');
        expect(button).toHaveLength(0);
    });
});

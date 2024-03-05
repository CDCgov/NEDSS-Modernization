import { render } from '@testing-library/react';
import { ReorderModal } from './ReorderModal';
import { PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { PageManagementProvider } from '../../../usePageManagement';

describe('when ReorderModal renders', () => {
    const page: PagesResponse = {
        id: 123,
        name: 'Test Page',
        status: 'status-value',
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
    const fetch = () => {
        jest.fn();
    };

    const refresh = () => {
        jest.fn();
    };
    const props = {
        modalRef: { current: null },
        pageName: 'Test Page'
    };
    it('should display Tab', () => {
        const { getByText } = render(
            <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                <DragDropProvider pageData={page}>
                    <ReorderModal {...props} />
                </DragDropProvider>
            </PageManagementProvider>
        );
        expect(getByText('Test Tab')).toBeTruthy();
    });

    it('should display Sections', () => {
        const { getByText } = render(
            <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                <DragDropProvider pageData={page}>
                    <ReorderModal {...props} />
                </DragDropProvider>
            </PageManagementProvider>
        );
        expect(getByText('Section1')).toBeTruthy();
        expect(getByText('Section2')).toBeTruthy();
    });
});

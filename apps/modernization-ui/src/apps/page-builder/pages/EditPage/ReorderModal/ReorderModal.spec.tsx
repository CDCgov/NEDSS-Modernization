import { render } from '@testing-library/react';
import { ReorderModal } from './ReorderModal';
import { PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

describe('when ReorderModal renders', () => {
    const content: PagesResponse = {
        id: 123,
        name: 'Test Page',
        tabs: [
            {
                id: 123456,
                name: 'Test Tab',
                sections: [
                    {
                        id: 1234,
                        name: 'Section1',
                        subSections: [],
                        visible: true
                    },
                    {
                        id: 5678,
                        name: 'Section2',
                        subSections: [],
                        visible: true
                    }
                ],
                visible: true
            }
        ]
    };
    const props = {
        modalRef: { current: null },
        pageName: 'Test Page'
    };
    it('should display Sections', () => {
        const { getByText } = render(<DragDropProvider pageData={content} currentTab={0}><ReorderModal {...props} /></DragDropProvider>);
        expect(getByText('Test Page')).toBeTruthy();
    });
});

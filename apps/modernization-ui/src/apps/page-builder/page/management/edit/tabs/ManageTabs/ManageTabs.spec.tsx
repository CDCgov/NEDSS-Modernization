import { ManageTabs } from './ManageTabs';
import { render } from '@testing-library/react';
import { PagesResponse } from 'apps/page-builder/generated';
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
                    id: 123456,
                    name: 'Test Section',
                    visible: true,
                    order: 1,
                    subSections: [
                        {
                            id: 123,
                            isGrouped: false,
                            name: 'Subsection1',
                            visible: true,
                            order: 1,
                            questions: [],
                            isGroupable: true
                        },
                        {
                            id: 456,
                            isGrouped: false,
                            name: 'Subsection2',
                            visible: true,
                            order: 2,
                            questions: [],
                            isGroupable: true
                        }
                    ]
                }
            ]
        }
    ]
};

const { getByTestId } = render(
    <DragDropProvider pageData={content}>
        <ManageTabs pageId={0} onAddSuccess={jest.fn()} tabs={content.tabs!} />
    </DragDropProvider>
);

describe('when ManageTabs renders', () => {
    it('should display tabs', () => {
        expect(getByTestId('label')).toBeInTheDocument();
        expect(getByTestId('label')).toHaveTextContent('Test Tab (1)');
    });
});

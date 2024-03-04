import { render } from '@testing-library/react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { ManageSection } from './ManageSection';

describe('when ManageSection renders', () => {
    const content: PagesResponse = {
        id: 123,
        name: 'Test Page',
        status: 'status',
        tabs: [
            {
                id: 123456,
                name: 'Test Page',
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
                                isGroupable: true,
                                questions: []
                            },
                            {
                                id: 456,
                                isGrouped: false,
                                name: 'Subsection2',
                                visible: true,
                                order: 2,
                                isGroupable: true,
                                questions: []
                            }
                        ]
                    }
                ]
            }
        ]
    };

    const tabData: PagesTab = {
        id: 123,
        name: 'tab-name',
        order: 1,
        visible: true,
        sections: []
    };

    it('should show the heading', () => {
        const { getByTestId } = render(
            <DragDropProvider pageData={content}>
                <ManageSection pageId={1} tab={tabData} key={1} onContentChange={jest.fn()} onCancel={jest.fn()} />
            </DragDropProvider>
        );
        const header = getByTestId('header');
        expect(header).toHaveTextContent('Manage sections');
    });

    it('should display two buttons', () => {
        const { container } = render(
            <DragDropProvider pageData={content}>
                <ManageSection pageId={1} tab={tabData} key={1} onContentChange={jest.fn()} onCancel={jest.fn()} />
            </DragDropProvider>
        );
        const buttons = container.getElementsByTagName('button');
        expect(buttons).toHaveLength(2);
    });
});

import { render} from '@testing-library/react';
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
                                name: 'Subsection1',
                                visible: true,
                                order: 1,
                                questions: []
                            },
                            {
                                id: 456,
                                name: 'Subsection2',
                                visible: true,
                                order: 2,
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
            <DragDropProvider pageData={content} currentTab={0}>
                <ManageSection
                    pageId={1}
                    tab={tabData}
                    key={1}
                    onContentChange={jest.fn()}
                    onCancel={jest.fn()}
                    setSelectedForEdit={jest.fn()}
                    selectedForEdit={undefined}
                    setSelectedForDelete={jest.fn()}
                    selectedForDelete={undefined}
                    handleDelete={jest.fn()}
                    reset={jest.fn()}
                />
            </DragDropProvider>
        );
        const header = getByTestId('header');
        expect(header).toHaveTextContent('Manage sections');
    })

    it('should display two buttons', () => {
        const { container } = render(
            <DragDropProvider pageData={content} currentTab={0}>
                <ManageSection
                    pageId={1}
                    tab={tabData}
                    key={1}
                    onContentChange={jest.fn()}
                    onCancel={jest.fn()}
                    setSelectedForEdit={jest.fn()}
                    selectedForEdit={undefined}
                    setSelectedForDelete={jest.fn()}
                    selectedForDelete={undefined}
                    handleDelete={jest.fn()}
                    reset={jest.fn()}
                />
            </DragDropProvider>
        );
        const buttons = container.getElementsByTagName('button');
        expect(buttons).toHaveLength(2);
    });
});

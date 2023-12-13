import { PagesResponse } from 'apps/page-builder/generated';
import { ManageTabsTile } from './ManageTabsTile';
import { render } from '@testing-library/react';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

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
    tab: content.tabs![0],
    index: 0,
    setSelectedEditTab: jest.fn(),
    setDeleteTab: jest.fn(),
    selectedForDelete: content.tabs![0],
    setSelectedForDelete: jest.fn(),
    reset: jest.fn()
}

    const { container } = render(
        <DragDropProvider pageData={content} currentTab={0}>
            <DragDropContext onDragEnd={() => {}}>
                <Droppable droppableId='testId'>
                    {(provided) => (
                        <div
                            {...provided.droppableProps}
                            ref={provided.innerRef}>
                            <ManageTabsTile {...props}></ManageTabsTile>
                        </div>
                    )}
                </Droppable>
            </DragDropContext>
        </DragDropProvider>);

describe('when ManageTabsTile renders', () => {
    it('should display Tab name', () => {
        const label = container.getElementsByClassName('manage-tabs-tile__label');
        expect(label[0]).toHaveTextContent('Test Tab');
    });
});

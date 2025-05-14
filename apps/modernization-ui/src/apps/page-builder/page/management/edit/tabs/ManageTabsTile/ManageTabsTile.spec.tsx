import { PagesResponse } from 'apps/page-builder/generated';
import { ManageTabsTile } from './ManageTabsTile';
import { render } from '@testing-library/react';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';

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

const props = {
    tab: content.tabs![0],
    index: 0,
    setSelectedForEdit: jest.fn(),
    setDeleteTab: jest.fn(),
    selectedForDelete: content.tabs![0],
    setSelectedForDelete: jest.fn(),
    deleteTab: jest.fn(),
    reset: jest.fn(),
    onChangeVisibility: jest.fn()
};

const { getByTestId } = render(
    <DragDropProvider pageData={content}>
        <DragDropContext onDragEnd={() => {}}>
            <Droppable droppableId="testId">
                {(provided) => (
                    <div {...provided.droppableProps} ref={provided.innerRef}>
                        <ManageTabsTile {...props}></ManageTabsTile>
                    </div>
                )}
            </Droppable>
        </DragDropContext>
    </DragDropProvider>
);

describe('when ManageTabsTile renders', () => {
    it('should display Tab name', () => {
        expect(getByTestId('label')).toBeInTheDocument();
        expect(getByTestId('label')).toHaveTextContent('Test Tab (2)');
    });
});

import { render } from '@testing-library/react';
import { PagesSection, PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import { ManageSectionTile } from './ManageSectionTile';

describe('when ManageSectionTile renders', () => {
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
    const section: PagesSection = {
        id: 123456,
        name: 'Test Section',
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
        ],
        visible: true
    };
    it('should display Name and number of subsections', () => {
        const { getByTestId } = render(
            <DragDropProvider pageData={content} currentTab={0}>
                <DragDropContext onDragEnd={() => {}}>
                    <Droppable droppableId="testId">
                        {(provided) => (
                            <div {...provided.droppableProps} ref={provided.innerRef} className="test__sections">
                                <ManageSectionTile
                                    section={section}
                                    index={1}
                                    setSelectedForEdit={jest.fn}
                                    setSelectedForDelete={jest.fn}
                                    selectedForDelete={section}
                                    handleDelete={jest.fn}
                                    reset={jest.fn}
                                />
                            </div>
                        )}
                    </Droppable>
                </DragDropContext>
            </DragDropProvider>
        );
        expect(getByTestId('manageSectionTileId').innerHTML).toBe('Test Section&nbsp;(2)');
    });
});

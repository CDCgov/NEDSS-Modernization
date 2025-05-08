import { render } from '@testing-library/react';
import { PagesSection, PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';
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
                                isGrouped: false,
                                name: 'Subsection1',
                                visible: true,
                                order: 1,
                                isGroupable: true,
                                questionIdentifier: 'identifier',
                                questions: []
                            },
                            {
                                id: 456,
                                isGrouped: false,
                                name: 'Subsection2',
                                visible: true,
                                order: 2,
                                isGroupable: true,
                                questionIdentifier: 'identifier',
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
                isGrouped: false,
                name: 'Subsection1',
                visible: true,
                order: 1,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            },
            {
                id: 456,
                isGrouped: false,
                name: 'Subsection2',
                visible: true,
                order: 2,
                isGroupable: true,
                questionIdentifier: 'identifier',
                questions: []
            }
        ],
        visible: true
    };
    it('should display Name and number of subsections', () => {
        const { getByTestId } = render(
            <DragDropProvider pageData={content}>
                <DragDropContext onDragEnd={() => {}}>
                    <Droppable droppableId="testId">
                        {(provided) => (
                            <div {...provided.droppableProps} ref={provided.innerRef} className="test__sections">
                                <ManageSectionTile
                                    section={section}
                                    index={1}
                                    setSelectedForDelete={jest.fn}
                                    selectedForDelete={undefined}
                                    handleDelete={jest.fn}
                                    setOnAction={jest.fn}
                                    onAction={false}
                                    setSectionState={jest.fn}
                                    setSelectedForEdit={jest.fn}
                                    onChangeVisibility={jest.fn}
                                />
                            </div>
                        )}
                    </Droppable>
                </DragDropContext>
            </DragDropProvider>
        );
        expect(getByTestId('manageSectionTileId').innerHTML).toBe('Test Section (2)');
    });
});

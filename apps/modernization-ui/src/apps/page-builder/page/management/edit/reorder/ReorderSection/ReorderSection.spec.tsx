import { render } from '@testing-library/react';
import { ReorderSection } from './ReorderSection';
import { PagesSection, PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';

describe('when ReorderSection renders', () => {
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
    it('should display Subsections', () => {
        const { container } = render(
            <DragDropProvider pageData={content}>
                <DragDropContext onDragEnd={() => {}}>
                    <Droppable droppableId="testId">
                        {(provided) => (
                            <div {...provided.droppableProps} ref={provided.innerRef} className="test__sections">
                                <ReorderSection section={section} index={1} visible />
                            </div>
                        )}
                    </Droppable>
                </DragDropContext>
            </DragDropProvider>
        );
        const subsection = container.getElementsByClassName('subsection');
        expect(subsection.length).toEqual(2);
    });
});

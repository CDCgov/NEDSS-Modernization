import { render } from '@testing-library/react';
import { ReorderSubsection } from './ReorderSubsection';
import { PagesSubSection, PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';

describe('when ReorderSubsection renders', () => {
    const content: PagesResponse = {
        id: 123,
        name: 'Test Page',
        status: 'status-value',
        tabs: [
            {
                id: 123456,
                name: 'Test Page',
                visible: true,
                order: 1,
                sections: [
                    {
                        id: 1234,
                        name: 'Section1',
                        order: 1,
                        subSections: [],
                        visible: true
                    },
                    {
                        id: 5678,
                        name: 'Section2',
                        order: 2,
                        subSections: [],
                        visible: true
                    }
                ]
            }
        ]
    };
    const subsection: PagesSubSection = {
        id: 123456,
        isGrouped: false,
        name: 'Test Section',
        order: 1,
        isGroupable: true,
        questionIdentifier: 'identifier',
        questions: [
            {
                allowFutureDates: true,
                coInfection: true,
                dataType: 'asdf',
                description: 'asdf',
                visible: true,
                enabled: true,
                id: 123,
                mask: 'asdf',
                name: 'asdf',
                question: 'asdf',
                tooltip: 'asdf',
                standard: 'asdf',
                required: true,
                subGroup: 'asdf',
                order: 1
            },
            {
                allowFutureDates: true,
                coInfection: false,
                dataType: 'asdf',
                description: 'asdf',
                visible: true,
                enabled: true,
                id: 234,
                mask: 'asdf',
                name: 'asdf',
                question: 'asdf',
                tooltip: 'asdf',
                standard: 'asdf',
                required: false,
                subGroup: 'asdf',
                order: 2
            }
        ],
        visible: true
    };
    const { container } = render(
        <DragDropProvider pageData={content}>
            <DragDropContext onDragEnd={() => {}}>
                <Droppable droppableId="testId">
                    {(provided) => (
                        <div {...provided.droppableProps} ref={provided.innerRef} className="test__subsections">
                            <ReorderSubsection subsection={subsection} index={1} visible />
                        </div>
                    )}
                </Droppable>
            </DragDropContext>
        </DragDropProvider>
    );
    it('should display Questions', () => {
        const questions = container.getElementsByClassName('question');
        expect(questions.length).toEqual(2);
    });
});

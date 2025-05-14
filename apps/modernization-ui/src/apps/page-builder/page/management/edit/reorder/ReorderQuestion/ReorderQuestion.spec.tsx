import { render } from '@testing-library/react';
import { ReorderQuestion } from './ReorderQuestion';
import { PagesQuestion, PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from '@hello-pangea/dnd';

describe('when ReorderQuestion renders', () => {
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
    const question: PagesQuestion = {
        id: 123,
        name: 'Test Question',
        order: 1,
        allowFutureDates: true,
        coInfection: true,
        dataType: 'asdf',
        description: 'asdf',
        visible: true,
        enabled: true,
        mask: 'asdf',
        question: 'asdf',
        tooltip: 'asdf',
        standard: 'asdf',
        required: false,
        subGroup: 'asdf'
    };
    const { getByText } = render(
        <DragDropProvider pageData={content}>
            <DragDropContext onDragEnd={() => {}}>
                <Droppable droppableId="testId">
                    {(provided) => (
                        <div {...provided.droppableProps} ref={provided.innerRef} className="test__questions">
                            <ReorderQuestion question={question} index={1} visible />
                        </div>
                    )}
                </Droppable>
            </DragDropContext>
        </DragDropProvider>
    );
    it('should display Question Name', () => {
        expect(getByText('Test Question')).toBeTruthy();
    });
});

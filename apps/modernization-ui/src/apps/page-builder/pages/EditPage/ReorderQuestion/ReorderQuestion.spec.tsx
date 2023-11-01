import { render } from '@testing-library/react';
import { ReorderQuestion } from './ReorderQuestion';
import { PagesQuestion, PagesTab } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

describe('when ReorderSubsection renders', () => {
    const content: PagesTab = {
        id: 123456,
        name: 'Test Page',
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
    };
    const question: PagesQuestion = {
        allowFutureDates: true,
        coInfection: true,
        dataType: 'asdf',
        description: 'asdf',
        display: true,
        enabled: true,
        id: 123,
        mask: 'asdf',
        name: 'Test Question',
        question: 'asdf',
        tooltip: 'asdf',
        standard: 'asdf',
        required: false,
        subGroup: 'asdf'
    };
    const { getByText } = render(
        <DragDropProvider data={content}>
            <DragDropContext onDragEnd={() => {}}>
                <Droppable droppableId='testId'>
                    {(provided) => (
                        <div
                            {...provided.droppableProps}
                            ref={provided.innerRef}
                            className="test__questions">
                    <ReorderQuestion question={question} index={1} visible/>
                </div>)}
                </Droppable>
            </DragDropContext></DragDropProvider>);
    it('should display Question Name', () => {
        expect(getByText('Test Question')).toBeTruthy();
    });
});

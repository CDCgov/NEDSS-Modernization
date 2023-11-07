import { render } from '@testing-library/react';
import { ReorderSection } from './ReorderSection';
import { PagesSection, PagesTab } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

describe('when ReorderSection renders', () => {
    const content: PagesTab = {
        id: 123456,
        name: 'Test Page',
        sections: [
            {
        id: 123456,
        name: 'Test Section',
        subSections: [
            {
                id: 123,
                name: 'Subsection1',
                questions: [],
                visible: true
            },
            {
                id: 456,
                name: 'Subsection2',
                questions: [],
                visible: true
            }
        ],
        visible: true
    }
        ],
        visible: true
    };
    const section: PagesSection = {
        id: 123456,
        name: 'Test Section',
        subSections: [
            {
                id: 123,
                name: 'Subsection1',
                questions: [],
                visible: true
            },
            {
                id: 456,
                name: 'Subsection2',
                questions: [],
                visible: true
            }
        ],
        visible: true
    };
    it('should display Subsections', () => {
        const { container } = render(
            <DragDropProvider data={content} pageDropId={0}>
                <DragDropContext onDragEnd={() => {}}>
                    <Droppable droppableId='testId'>
                        {(provided) => (
                            <div
                                {...provided.droppableProps}
                                ref={provided.innerRef}
                                className="test__sections">
                            <ReorderSection section={section} index={1} visible />
                    </div>)}
                    </Droppable>
                </DragDropContext>
            </DragDropProvider>);
        const subsection = container.getElementsByClassName('reorder-subsection');
        expect(subsection.length).toEqual(2);
    });
});

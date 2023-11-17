import { render } from '@testing-library/react';
import { PageInfo } from './PageInfo';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';
import { BrowserRouter } from 'react-router-dom';

describe('when ReorderSubsection renders', () => {
    const content = {
        id: 1000377
    };

    const { getByText } = render(
        <BrowserRouter>
            <DragDropProvider data={content} pageDropId={0} tabId={1}>
                <DragDropContext onDragEnd={() => {}}>
                    <Droppable droppableId="testId">
                        {(provided) => (
                            <div {...provided.droppableProps} ref={provided.innerRef} className="test__questions">
                                <PageInfo page={content.id} />
                            </div>
                        )}
                    </Droppable>
                </DragDropContext>
            </DragDropProvider>
        </BrowserRouter>
    );
    it('should display page information label', () => {
        expect(getByText('Page Info')).toBeTruthy();
        expect(getByText('Event type')).toBeTruthy();
        expect(getByText('Message mapping guide')).toBeTruthy();
        expect(getByText('Page name')).toBeTruthy();
        expect(getByText('Data mart name')).toBeTruthy();
        expect(getByText('Description')).toBeTruthy();
        expect(getByText('Related Conditions')).toBeTruthy();
    });
});

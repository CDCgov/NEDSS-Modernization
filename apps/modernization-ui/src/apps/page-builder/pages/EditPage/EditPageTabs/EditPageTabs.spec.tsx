import { fireEvent, render, screen } from '@testing-library/react';
import { EditPageTabs } from './EditPageTabs';
import { PagesTab } from 'apps/page-builder/generated';
import { PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

const props = {
    tabs: [{ name: 'first tab', visible: true }, { name: 'second tab', visible: true }] as PagesTab[],
    active: 0,
    setActive: jest.fn(),
    onAddSuccess: jest.fn()
};

describe('EditPageTabs', () => {
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
    it('should render', () => {
        render(<DragDropProvider pageData={content} currentTab={0}><EditPageTabs {...props} /></DragDropProvider>);
        expect(screen.getAllByText('first tab')[0]).toBeInTheDocument();
        expect(screen.getAllByText('second tab')[0]).toBeInTheDocument();
    });

    it('it should render component with the active tab', () => {
        render(<DragDropProvider pageData={content} currentTab={0}><EditPageTabs {...props} /></DragDropProvider>);
        const tabs = screen.getByTestId('edit-page-tabs');
        expect(tabs.firstChild).toHaveClass('active');
    });

    it('should have the Manage tabs button', () => {
        render(<DragDropProvider pageData={content} currentTab={0}><EditPageTabs {...props} /></DragDropProvider>);
        const manageButton = screen.getByTestId('openManageTabs');

        expect(manageButton).toBeInTheDocument();
        expect(manageButton).toHaveTextContent('Manage tabs');
    });

    describe('when a tab is clicked', () => {
        it('is set to active', () => {
            render(<DragDropProvider pageData={content} currentTab={0}><EditPageTabs {...props} /></DragDropProvider>);
            const secondTab = screen.getAllByText('second tab');
            fireEvent.click(secondTab[0]);

            expect(secondTab[0].parentElement).not.toHaveClass('active');
        });
    });
});

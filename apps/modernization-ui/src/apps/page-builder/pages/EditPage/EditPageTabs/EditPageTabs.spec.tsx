import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { EditPageTabs } from './EditPageTabs';
import { PagesTab } from 'apps/page-builder/generated';
import { PagesResponse } from 'apps/page-builder/generated';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

const props = {
    tabs: [
        { name: 'first tab', visible: true },
        { name: 'second tab', visible: true }
    ] as PagesTab[],
    active: 0,
    setActive: jest.fn(),
    onAddSuccess: jest.fn()
};

describe('EditPageTabs', () => {
    const content: PagesResponse = {
        id: 123,
        name: 'Test Page',
        status: 'status-value',
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
    it('should render', () => {
        const { getByText } = render(
            <DragDropProvider pageData={content} currentTab={0}>
                <EditPageTabs page={191} {...props} />
            </DragDropProvider>
        );
        expect(getByText('first tab')).toBeInTheDocument();
        expect(getByText('second tab')).toBeInTheDocument();
    });

    it('it should render component with the active tab', () => {
        const { getByTestId } = render(
            <DragDropProvider pageData={content} currentTab={0}>
                <EditPageTabs page={191} {...props} />
            </DragDropProvider>
        );

        const tabs = getByTestId('edit-page-tabs');
        expect(tabs.firstChild).toHaveClass('active');
    });

    it('should have the Manage tabs button', () => {
        const { getByTestId } = render(
            <DragDropProvider pageData={content} currentTab={0}>
                <EditPageTabs page={191} {...props} />
            </DragDropProvider>
        );

        const manageButton = getByTestId('openManageTabs');

        expect(manageButton).toBeInTheDocument();
        expect(manageButton).toHaveTextContent('Manage tabs');
    });

    describe('when a tab is clicked', () => {
        it('is set to active', () => {
            const { getByText } = render(
                <DragDropProvider pageData={content} currentTab={0}>
                    <EditPageTabs page={191} {...props} />
                </DragDropProvider>
            );
            const secondTab = getByText('second tab');
            userEvent.click(secondTab);

            expect(secondTab.parentElement).not.toHaveClass('active');
        });
    });
});

import { render } from '@testing-library/react';
import { SubsectionHeader } from './SubsectionHeader';
import { PagesResponse, PagesSubSection } from 'apps/page-builder/generated';
import { PageManagementProvider } from '../../usePageManagement';
import { AlertProvider } from 'alert';
import userEvent from '@testing-library/user-event';

const onAdd = jest.fn();
const handleExpanded = jest.fn();
const onDelete = jest.fn();
const onEdit = jest.fn();
const onGroup = jest.fn();

const unGroupedSubsection: PagesSubSection = {
    id: 1,
    name: 'Test Subsection',
    order: 23,
    visible: true,
    isGrouped: false,
    isGroupable: true,
    questionIdentifier: 'TSTSBSCTN',
    blockName: undefined,
    questions: [{ id: 2, name: 'test Question', order: 24, isPublished: false }]
};

const groupedSubsection: PagesSubSection = {
    id: 1,
    name: 'Test Subsection',
    order: 23,
    visible: true,
    isGrouped: true,
    isGroupable: false,
    questionIdentifier: 'TSTSBSCTN',
    blockName: 'BLOCK_NAME',
    questions: [{ id: 2, name: 'test Question', order: 24, isPublished: false }]
};

describe('Subsection header', () => {
    it('should display group question option for Draft page', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Draft'
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={unGroupedSubsection}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));

        expect(queryByText('Group questions')).toBeInTheDocument();
    });

    it('should display group question option for Published with Draft page', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Published with Draft'
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={unGroupedSubsection}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Group questions')).toBeInTheDocument();
    });

    it('should not display group question option for Published page', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Published'
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={unGroupedSubsection}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Group questions')).not.toBeInTheDocument();
    });

    it('should not display group question option if a question is published', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Published with Draft'
        };

        const subsectionWithPublishedQuestion: PagesSubSection = {
            id: 1,
            name: 'Test Subsection',
            order: 23,
            visible: true,
            isGrouped: false,
            isGroupable: true,
            questionIdentifier: 'TSTSBSCTN',
            blockName: 'BLOCK_NAME',
            questions: [{ id: 2, name: 'test Question', order: 24, isPublished: true }]
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={subsectionWithPublishedQuestion}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Group questions')).not.toBeInTheDocument();
    });

    it('should not display ungroup question option for Published page', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Published'
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={groupedSubsection}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Ungroup questions')).not.toBeInTheDocument();
    });

    it('should display ungroup question option for Draft page', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Draft'
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={groupedSubsection}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Ungroup questions')).toBeInTheDocument();
    });

    it('should display ungroup question option for Published with Draft page', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Published with Draft'
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={groupedSubsection}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Ungroup questions')).toBeInTheDocument();
    });

    it('should not display ungroup question option if a question is published', async () => {
        const page: PagesResponse = {
            id: 12039120,
            name: 'Test Page',
            status: 'Published with Draft'
        };

        const subsectionWithPublishedQuestion: PagesSubSection = {
            id: 1,
            name: 'Test Subsection',
            order: 23,
            visible: true,
            isGrouped: true,
            isGroupable: true,
            questionIdentifier: 'TSTSBSCTN',
            blockName: 'BLOCK_NAME',
            questions: [{ id: 2, name: 'test Question', order: 24, isPublished: true }]
        };
        const { queryByText, getByRole } = render(
            <AlertProvider>
                <PageManagementProvider page={page} fetch={jest.fn()} refresh={jest.fn} loading={false}>
                    <SubsectionHeader
                        subsection={subsectionWithPublishedQuestion}
                        onAddQuestion={onAdd}
                        onExpandedChange={handleExpanded}
                        isExpanded={true}
                        onDeleteSubsection={onDelete}
                        onEditSubsection={onEdit}
                        onGroupQuestion={onGroup}
                    />
                </PageManagementProvider>
            </AlertProvider>
        );
        const user = userEvent.setup();
        await user.click(getByRole('menu'));
        expect(queryByText('Ungroup questions')).not.toBeInTheDocument();
    });
});

import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { PagesQuestion, PagesResponse, PagesSection, PagesSubSection, PagesTab } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router';

import { PageManagementProvider } from '../../usePageManagement';
import { PageContent } from '../content/PageContent';

vi.mock('apps/page-builder/generated');

vi.mock('apps/page-builder/hooks/api/useFindValueset', () => {
    return {
        useFindValuesets: () => ({
            isLoading: false,
            search: vi.fn(),
            response: undefined,
        }),
    };
});

vi.mock('apps/page-builder/hooks/api/useFindAvailableQuestions', () => {
    return {
        useFindAddableQuestions: () => ({
            isLoading: false,
            search: vi.fn(),
        }),
    };
});

const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft',
};

const fetch = () => {
    vi.fn();
};

const refresh = () => {
    vi.fn();
};

const dateQuestion: PagesQuestion = {
    id: 3,
    name: 'date test question',
    order: 3,
    dataType: 'DATE',
    isStandard: true,
};

const dropDownQuestion: PagesQuestion = {
    id: 4,
    name: 'test drop down question',
    order: 4,
    isStandard: true,
    displayComponent: 1007,
};

const subSections: PagesSubSection = {
    id: 2,
    isGrouped: false,
    name: 'test subsection',
    order: 2,
    questions: [dateQuestion, dropDownQuestion],
    isGroupable: true,
    questionIdentifier: 'identifier',
    visible: true,
};

const sections: PagesSection = {
    id: 1,
    name: 'test section',
    order: 1,
    subSections: [subSections],
    visible: true,
};

const tabs: PagesTab = {
    id: 0,
    name: 'test tab',
    order: 0,
    sections: [sections],
    visible: true,
};

describe('when page loads', () => {
    it('date input should have calendar next to input field', async () => {
        const { getByTestId, findByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <PageContent tab={tabs} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByTestId('calendar-icon')).toBeInTheDocument();
        expect(await findByLabelText('calendar')).toBeVisible();
    });

    it('drop down input should be true for display component 1007', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <PageContent tab={tabs} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByTestId('dropdown-input')).toBeInTheDocument();
    });
});

import { AlertProvider } from 'alert';
import { PagesQuestion, PagesResponse } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router';
import { PageManagementProvider } from '../../usePageManagement';
import { EditStaticElement } from './EditStaticElement';
import { render } from '@testing-library/react';

const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft'
};

const hyperlinkElement: PagesQuestion = {
    id: 3,
    name: 'hyperlink label',
    defaultValue: 'google.com',
    order: 3,
    adminComments: 'admin comments',
    displayComponent: 1003
};

const commentsElement: PagesQuestion = {
    id: 4,
    name: 'testing comments element',
    order: 4,
    adminComments: 'admin comments',
    displayComponent: 1014
};

const lineSeparatorElement: PagesQuestion = {
    id: 4,
    order: 4,
    adminComments: 'admin comments',
    displayComponent: 1012,
    name: ''
};

const fetch = () => {
    jest.fn();
};

const refresh = () => {
    jest.fn();
};

describe('When modal loads with hyperlink element', () => {
    it('the save changes button should be disabled', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={hyperlinkElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('submit-btn')).toBeDisabled();
    });

    it('the input fields should be populated with defaults', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={hyperlinkElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('hyperlinkLabel')).toHaveValue('hyperlink label');
        expect(getByLabelText('linkUrl')).toHaveValue('google.com');
        expect(getByLabelText('adminComments')).toHaveValue('admin comments');
    });

    it('static type select should be disables', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={hyperlinkElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('staticType')).toBeDisabled();
    });
});

describe('When modal loads with comments element', () => {
    it('the save changes button should be disabled', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={commentsElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('submit-btn')).toBeDisabled();
    });

    it('the input fields should be populated with defaults', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={commentsElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('commentsText')).toHaveValue('testing comments element');
        expect(getByLabelText('adminComments')).toHaveValue('admin comments');
    });
});

describe('When modal loads with line separator element', () => {
    it('the save changes button should be disabled', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={lineSeparatorElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('submit-btn')).toBeDisabled();
    });

    it('the input fields should be populated with defaults', () => {
        const { getByLabelText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
                        <EditStaticElement question={lineSeparatorElement} />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByLabelText('adminComments')).toHaveValue('admin comments');
    });
});

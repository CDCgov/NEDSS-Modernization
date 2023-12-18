import { act, fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import AddStaticElement from './AddStaticElement';
import { PageManagementProvider } from '../../usePageManagement';
import { PagesResponse } from 'apps/page-builder/generated';
import userEvent from '@testing-library/user-event';

const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft'
};

describe('When page loads', () => {
    it('the add static button should be disabled', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByTestId('submit-btn').hasAttribute('disabled'));
    });

    it('it should render with one select input', () => {
        const { getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByText('Choose a static element')).toBeInTheDocument();
    });
});

describe('When line separator is chosen', () => {
    it('only the comments input and static element type inputs should be displayed', () => {
        const { getByTestId, getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Line separator' );
            fireEvent.blur(staticTypeInput);
        });

        expect(getByText('Administrative Comments')).toBeInTheDocument();
    });
});

describe('When hyperlink is chosen', () => {
    it('only label, linkURL and admin comments should display', () => {
        const { getByTestId, getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Hyperlink' );
            fireEvent.blur(staticTypeInput);
        });

        expect(getByText('Administrative Comments')).toBeInTheDocument();
        expect(getByText('Link URL')).toBeInTheDocument();
    });
});

describe('When comments is chosen', () => {
    it('only displays comments text and admin comments', () => {
        const { getByTestId, getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Comments (read-only)');
            fireEvent.blur(staticTypeInput);
        });

        expect(getByText('Administrative Comments')).toBeInTheDocument();
        expect(getByText('Comments text')).toBeInTheDocument();
    });
});

describe('When participants is chosen', () => {
    it('only displays admin comments', () => {
        const { getByTestId, getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');

        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Participant list (read-only)' );
            fireEvent.blur(staticTypeInput);
        });

        expect(getByText('Administrative Comments')).toBeTruthy();
    });
});

describe('When electronic doc list is chosen', () => {
    it('only displays admin comments', () => {
        const { getByTestId, getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Electronic document list (read-only)');
            fireEvent.blur(staticTypeInput);
        });

        expect(getByText('Administrative Comments')).toBeInTheDocument();
    });
});

describe('When all inputs are entered', () => {
    it('button enables once all inputs are selected', () => {
        const { getByTestId, container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');

        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Electronic document list (read-only)' );
            fireEvent.blur(staticTypeInput);
        });

        const adminInput = getByTestId("adminComments");
        act(() => {
            userEvent.type(adminInput, "Something comments");
            fireEvent.blur(adminInput);
        });


    });
});

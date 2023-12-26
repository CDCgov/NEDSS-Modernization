import { act, fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { AddStaticElement } from './AddStaticElement';
import { PageManagementProvider } from '../../usePageManagement';
import { PagesResponse } from 'apps/page-builder/generated';
import userEvent from '@testing-library/user-event';
import { useGetPageDetails } from '../../useGetPageDetails';

const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft'
};

const fetch = () => {
    console.log("dummy fetch");
}

describe('When page loads', () => {
    it('the add static button should be disabled', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByTestId('submit-btn')).toBeDisabled();
    });

    it('it should render with one select input', () => {
        const { getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        expect(getByText('Choose a static element')).toBeInTheDocument();
    });
});

describe('When line separator is chosen', () => {
    it('only the comments input and static element type inputs should be displayed', async () => {
        const { getByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Line separator');
        });

        act(() => {
            fireEvent.blur(staticTypeInput);
        });

        expect(await findByText('Administrative Comments')).toBeInTheDocument();
    });
});

describe('When hyperlink is chosen', () => {
    it('only label, linkURL and admin comments should display', async () => {
        const { getByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Hyperlink');
        });

        act(() => {
            fireEvent.blur(staticTypeInput);
        });

        expect(await findByText('Administrative Comments')).toBeInTheDocument()
        expect(await findByText('Link URL')).toBeInTheDocument()

        
    });
});

describe('When comments is chosen', () => {
    it('only displays comments text and admin comments', async () => {
        const { getByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Comments (read-only)');
        });

        act(() => {
            fireEvent.blur(staticTypeInput);
        });

        expect(await findByText('Administrative Comments')).toBeInTheDocument()
        expect(await findByText('Comments text')).toBeInTheDocument()

    });
});

describe('When participants is chosen', () => {
    it('only displays admin comments', async () => {
        const { getByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');

        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Participant list (read-only)');
        });

        act(() => {
            fireEvent.blur(staticTypeInput);
        });

        expect(await findByText('Administrative Comments')).toBeInTheDocument()

    });
});

describe('When electronic doc list is chosen', () => {
    it('only displays admin comments', async () => {
        const { getByTestId, findByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');
        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Electronic document list (read-only)');
        });

        act(() => {
            fireEvent.blur(staticTypeInput);
        });

        expect(await findByText('Administrative Comments')).toBeInTheDocument()
    });
});

describe('When all inputs are entered', () => {
    it('button enables once all inputs are selected', async () => {
        const { getByTestId, findByTestId } = render(
            <BrowserRouter>
                <AlertProvider>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <AddStaticElement />
                    </PageManagementProvider>
                </AlertProvider>
            </BrowserRouter>
        );

        const staticTypeInput = getByTestId('staticType');

        act(() => {
            userEvent.selectOptions(staticTypeInput, 'Hyperlink');
        });

        act(() => {
            fireEvent.blur(staticTypeInput);
        });

        const labelInput = getByTestId('hyperlinkLabel');

        act(() => {
            userEvent.type(labelInput, 'Something label');
        });

        act(() => {
            fireEvent.blur(labelInput);
        });

        const linkInput = getByTestId('linkUrl');
        act(() => {
            userEvent.type(linkInput, 'www.test.com');
        });

        act(() => {
            fireEvent.blur(linkInput);
        });

        expect(await findByTestId('submit-btn')).toBeEnabled()
    });
});

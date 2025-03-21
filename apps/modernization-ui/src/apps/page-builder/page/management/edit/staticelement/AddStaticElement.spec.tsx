import { render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { AddStaticElement } from './AddStaticElement';
import { PageManagementProvider } from '../../usePageManagement';
import { PagesResponse } from 'apps/page-builder/generated';
import userEvent from '@testing-library/user-event';

const page: PagesResponse = {
    id: 12039120,
    name: 'test page',
    status: 'Draft'
};

const fetch = () => {
    jest.fn();
};

const refresh = () => {
    jest.fn();
};

const Fixture = () => (
    <AlertProvider>
        <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
            <AddStaticElement />
        </PageManagementProvider>
    </AlertProvider>
);

describe('When page loads', () => {
    it('the add static button should be disabled', () => {
        const { getByTestId } = render(<Fixture />);

        expect(getByTestId('submit-btn')).toBeDisabled();
    });

    it('it should render with one select input', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Choose a static element')).toBeInTheDocument();
    });
});

describe('When line separator is chosen', () => {
    it('only the comments input and static element type inputs should be displayed', async () => {
        const { getByTestId, getByText } = render(<Fixture />);

        const staticTypeInput = getByTestId('staticType');

        const user = userEvent.setup();

        await user.selectOptions(staticTypeInput, 'Line separator');

        expect(getByText('Administrative Comments')).toBeInTheDocument();
    });
});

describe('When hyperlink is chosen', () => {
    it('only label, linkURL and admin comments should display', async () => {
        const { getByTestId, getByText } = render(<Fixture />);

        const staticTypeInput = getByTestId('staticType');
        const user = userEvent.setup();

        await user.selectOptions(staticTypeInput, 'Hyperlink');

        expect(getByText('Administrative Comments')).toBeInTheDocument();
        expect(getByText('Link URL')).toBeInTheDocument();
    });
});

describe('When comments is chosen', () => {
    it('only displays comments text and admin comments', async () => {
        const { getByTestId, getByText } = render(<Fixture />);

        const staticTypeInput = getByTestId('staticType');

        const user = userEvent.setup();

        await user.selectOptions(staticTypeInput, 'Comments (read-only)');

        expect(getByText('Administrative Comments')).toBeInTheDocument();
        expect(getByText('Comments text')).toBeInTheDocument();
    });
});

describe('When all inputs are entered', () => {
    it('button enables once all inputs are selected', async () => {
        const { getByTestId, getByRole, getByLabelText } = render(<Fixture />);

        const staticTypeInput = getByTestId('staticType');

        const user = userEvent.setup();

        await user.selectOptions(staticTypeInput, 'Hyperlink');

        const labelInput = getByLabelText('hyperlinkLabel');
        const linkInput = getByLabelText('linkUrl');

        await user.type(labelInput, 'Something label{tab}').then(() => user.type(linkInput, 'www.test.com'));

        expect(getByRole('button', { name: 'Save changes' })).toBeEnabled();
    });
});

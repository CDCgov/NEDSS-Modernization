import { render, waitFor, within } from '@testing-library/react';
import * as generated from 'generated';
import * as options from 'options/selectableResolver';
import { Layout } from 'layout';
import { ReactNode } from 'react';
import { createMemoryRouter, RouterProvider, useNavigate } from 'react-router';
import { AddReportConfiguration } from './AddReportConfiguration';
import userEvent from '@testing-library/user-event';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useNavigate: vi.fn(), // Mock useParams to return a default value
    };
});
vi.mock('generated');
vi.mock('options/selectableResolver');

vi.mock('libs/permission', async () => {
    const actual = await vi.importActual<typeof import('libs/permission')>('libs/permission');
    return {
        ...actual,
        Permitted: vi.fn(({ children }: { children: ReactNode }) => <>{children}</>),
    };
});

// don't actually let the cache cache
const localStorageMock: Storage = {
    getItem: (): string | null => null,
    setItem: (): void => {},
    removeItem: (): void => {},
    clear: (): void => {},
    key: (): string | null => '',
    length: 0,
};

let originalLocalStorage: Storage;
beforeAll((): void => {
    originalLocalStorage = window.localStorage;
    (window as any).localStorage = localStorageMock;
});

afterAll((): void => {
    (window as any).localStorage = originalLocalStorage;
});

afterEach(() => {
    vi.restoreAllMocks();
});

const renderWithRouter = () => {
    const routes = [
        {
            path: '/',
            element: <Layout />,
            children: [{ index: true, element: <AddReportConfiguration /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/'] });
    return render(<RouterProvider router={router} />);
};

describe('add report configuration page', () => {
    const mockOptionApiImpl = (url: string) => {
        if (url.includes('datasources')) {
            return Promise.resolve([
                { value: '1', name: 'nbs_ods.data_source', label: 'NBS Data Source' },
                { value: '2', name: 'nbs_rdb.lab_source', label: 'NBS Lab Source' },
            ]);
        } else if (url.includes('libraries')) {
            return Promise.resolve([
                { value: '1', name: 'library_one', label: 'A library to beat all libraries' },
                { value: '2', name: 'library_two', label: 'The best library of the best' },
                { value: '3', name: 'nbs_custom', label: 'Recommended default. The obvious one' },
            ]);
        } else if (url.includes('users')) {
            return Promise.resolve([
                { value: '1', name: 'User One' },
                { value: '2', name: 'User Two' },
            ]);
        } else if (url.includes('sections')) {
            return Promise.resolve([
                { value: '1000', name: 'Default' },
                { value: '1002', name: 'My reports' },
            ]);
        } else if (url.includes('filters')) {
            return Promise.resolve([
                { value: '7', name: 'Where Clause Builder' },
                { value: '1', name: 'My filter' },
                { value: '6', name: 'Text filter' },
            ]);
        } else if (url.includes('columns')) {
            return Promise.resolve([
                { value: '2001', name: 'FULL_NAME', label: 'Full Name' },
                { value: '2002', name: 'DATE_OF_BIRTH', label: 'Date of Birth' },
            ]);
        } else {
            return Promise.resolve([]);
        }
    };

    it('renders empty form and marks fields required', async () => {
        const mockApi = vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        const { getByRole, findByRole, findByText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        await waitFor(() => expect(mockApi).toHaveBeenCalled());

        expect(await findByText('Data source')).toBeVisible();
        expect(await findByText('Description')).toBeVisible();
        expect(await findByText('3. Available filters')).toBeVisible();

        const user = userEvent.setup();

        await user.click(await findByRole('button', { name: 'Submit' }));

        for (const field of ['Data source', 'Name', 'Owner', 'Group', 'Section name', 'Report execution library']) {
            expect(await findByText(`The ${field} is required.`)).toBeVisible();
        }
    });

    it('fills out form and redirects on submit', async () => {
        const mockApi = vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        vi.mocked(generated.ReportControllerService.createReport).mockResolvedValue({ reportUid: 1, dataSourceUid: 2 });
        const navigate = vi.fn();
        vi.mocked(useNavigate).mockReturnValue(navigate);
        const { getByRole, findByRole, findByLabelText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        await waitFor(() => expect(mockApi).toHaveBeenCalled());

        const user = userEvent.setup();

        // fill out and cancel -> confirm data source
        expect(await findByLabelText('Name')).toBeDisabled();
        await user.selectOptions(await findByLabelText('Data source'), '1');
        await user.click(await findByRole('button', { name: 'Confirm data source' }));
        await user.click(await findByRole('button', { name: 'Cancel' }));
        expect(await findByLabelText('Name')).toBeDisabled();
        await user.click(await findByRole('button', { name: 'Confirm data source' }));
        await user.click(await findByRole('button', { name: 'Confirm' }));
        expect(await findByLabelText('Name')).not.toBeDisabled();

        // fill out report config
        await user.type(await findByLabelText('Name'), 'Name');
        await user.type(await findByLabelText('Description'), 'Description');
        await user.selectOptions(await findByLabelText('Owner'), '0'); // System
        await user.selectOptions(await findByLabelText('Group'), 'PRIVATE');
        await user.selectOptions(await findByLabelText('Section name'), 'My reports');
        const libDropDown = await findByRole('combobox', {name: "Report execution library"})
        const libOptions = await within(libDropDown).findAllByRole('option')
        await user.selectOptions(libDropDown, libOptions[1]); // nbs custom at top (after `- Select -` placeholder)
        
        // add filter
        await user.selectOptions(await findByLabelText('Filter'), '1');
        await user.selectOptions(await findByLabelText('Type'), 'Single');
        await user.selectOptions(await findByLabelText('Associated column'), 'DATE_OF_BIRTH (Date of Birth)');
        await user.click(await findByLabelText('Required as basic filter'));
        await user.click(await findByRole('button', {name: 'Add filter'}));
        // should add to table and form reset
        expect(await findByRole('cell', {name: 'My filter'})).toBeVisible();
        expect(await findByLabelText('Filter')).toHaveValue('');
        
        await user.click(await findByRole('button', {name: 'Submit'}))

        expect(navigate).toHaveBeenCalledWith('/report/management/configuration/1/2')
        
    });
});

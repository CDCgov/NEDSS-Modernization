import { render, waitFor, within } from '@testing-library/react';
import * as generated from 'generated';
import * as options from 'options/selectableResolver';
import { Layout } from 'layout';
import { ReactNode } from 'react';
import { createMemoryRouter, RouterProvider, useLoaderData, useNavigate } from 'react-router';
import { EditReportConfiguration } from './EditReportConfiguration';
import userEvent from '@testing-library/user-event';
import { LoadingBlock } from 'libs/loading/block';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useLoaderData: vi.fn(),
        useParams: vi.fn(() => ({ reportUid: '2', dataSourceUid: '1' })), // Mock useParams to return a default value
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

const MOCK_CONFIG: generated.ReportConfiguration = {
    title: 'Test Report',
    ownerUid: 0,
    group: generated.ReportConfiguration.group.PUBLIC,
    sectionCd: '1000',
    dataSource: {
        id: 1,
        name: 'nbs_ods.data_source',
        hasJurisdictionSecurity: true,
        hasFacilitySecurity: false,
    },
    library: {
        id: 2,
        runner: 'python',
        name: 'nbs_sr_99',
        description: 'a library',
        isBuiltin: true,
        allowColumnSelection: false,
    },
    columns: [
        {
            id: 2001,
            name: 'FULL_NAME',
            title: 'Full Name',
            sourceTypeCode: 'STRING',
            isDisplayable: true,
            isFilterable: true,
        },
        {
            id: 2002,
            name: 'DATE_OF_BIRTH',
            title: 'Date of Birth',
            sourceTypeCode: 'DATETIME',
            isDisplayable: true,
            isFilterable: true,
        },
    ],
    basicFilters: [
        {
            reportFilterUid: 4,
            isRequired: true,
            defaultIncludeNulls: false,
            filterType: { id: 1, name: 'My filter' },
            selectType: generated.BasicFilterConfiguration.selectType.SINGLE,
            reportColumnUid: 2001,
        },
    ],
    advancedFilter: {
        reportFilterUid: 123,
    },
};

const renderWithRouter = () => {
    const routes = [
        {
            path: '/:reportUid/:dataSourceUid',
            element: <Layout />,
            HydrateFallback: LoadingBlock,
            children: [{ index: true, element: <EditReportConfiguration /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/2/1/'] });
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

    it('renders the form and check validation', async () => {
        const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
        const mockApi = vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        vi.mocked(generated.ReportControllerService.editReport).mockResolvedValue({ reportUid: 2, dataSourceUid: 1 });
        const navigate = vi.fn();
        vi.mocked(useNavigate).mockReturnValue(navigate);
        const { findByRole, getByRole, findByLabelText, findByText, findAllByText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        expect(mockConfigApi).toHaveBeenCalled();

        await waitFor(() => expect(mockApi).toHaveBeenCalled());

        expect(await findByText('3. Available filters')).toBeVisible();
        expect(await findByLabelText('Data source')).toHaveDisplayValue('nbs_ods.data_source (NBS Data Source)');
        expect(await findByLabelText('Name')).toHaveDisplayValue('Test Report');
        expect(await findByLabelText('Description')).toHaveDisplayValue('');
        expect(await findByLabelText('Group')).toHaveDisplayValue('Public');
        expect(await findByLabelText('Owner')).toHaveDisplayValue('System');
        expect(await findByLabelText('Section name')).toHaveDisplayValue('Default');
        expect(await findByLabelText('Report execution library')).toHaveDisplayValue(
            'library_two (The best library of the best)'
        );

        const filterTable = await findByRole('group', { name: '3. Available filters' });
        const rows = await within(filterTable).findAllByRole('row');
        const myFilterCells = await within(rows[1]).findAllByRole('cell');
        expect(myFilterCells[0]).toHaveTextContent('My filter');
        expect(myFilterCells[1]).toHaveTextContent('Single');
        expect(myFilterCells[2]).toHaveTextContent('FULL_NAME (Full Name)');
        expect(myFilterCells[3]).toHaveTextContent('Yes');
        const advWhereCells = await within(rows[2]).findAllByRole('cell');
        expect(advWhereCells[0]).toHaveTextContent('Where Clause Builder');
        expect(advWhereCells[1]).toHaveTextContent('---');
        expect(advWhereCells[2]).toHaveTextContent('---');
        expect(advWhereCells[3]).toHaveTextContent('No');

        const user = userEvent.setup();

        await user.selectOptions(await findByLabelText('Group'), '- Select -');

        await user.click(await findByRole('button', { name: 'Submit' }));

        expect(await findAllByText(`The Group is required.`)).toHaveLength(2);

        await user.selectOptions(await findByLabelText('Group'), 'Private');

        await user.click(await findByRole('button', { name: 'Submit' }));

        expect(navigate).toHaveBeenCalledWith('/report/management/configuration/2/1');
    });
});

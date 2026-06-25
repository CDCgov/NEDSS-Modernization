import { render } from '@testing-library/react';
import * as generated from 'generated';
import * as options from 'options/selectableResolver';
import { Layout } from 'layout';
import { ReactNode } from 'react';
import { createMemoryRouter, RouterProvider, useLoaderData } from 'react-router';
import { ViewReportConfiguration } from './ViewReportConfiguration';
import userEvent from '@testing-library/user-event';
import { LoadingBlock } from 'libs/loading/block';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useLoaderData: vi.fn(),
        useParams: vi.fn(() => ({ reportUid: '2', dataSourceUid: '1' })), // Mock useParams to return a default value
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
            children: [{ index: true, element: <ViewReportConfiguration /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/2/1'] });
    return render(<RouterProvider router={router} />);
};

describe('view report configuration page', () => {
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

    it('renders the config', async () => {
        const mockApi = vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        const { getByRole, findByText, findAllByText, findAllByLabelText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        expect(mockApi).toHaveBeenCalled();

        expect(await findByText('3. Available filters')).toBeVisible();
        expect(await findByText('Public')).toBeVisible();
        expect(await findByText('library_two')).toBeVisible();
        expect(await findByText('(The best library of the best)')).toBeVisible();
        expect(await findByText('nbs_ods.data_source')).toBeVisible();
        expect(await findByText('(NBS Data Source)')).toBeVisible();
        expect(await findByText('Default')).toBeVisible();
        expect(await findByText('Test Report')).toBeVisible();
        expect(await findByText('System')).toBeVisible();
        expect(await findByText('My filter')).toBeVisible();
        expect(await findByText('Where Clause Builder')).toBeVisible();

        const user = userEvent.setup();

        await user.click((await findAllByLabelText('View'))[0]);

        expect(await findAllByText('My filter')).toHaveLength(2);
        expect(await findAllByText('Yes')).toHaveLength(2);
        expect(await findAllByText(/FULL_NAME \(Full Name\)/));
        expect(await findAllByText('Single'));
    });

    it('renders no filters', async () => {
        const mockApi = vi
            .mocked(useLoaderData)
            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [], advancedFilter: undefined });
        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        const { getByRole, findByText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        expect(mockApi).toHaveBeenCalled();

        expect(await findByText('3. Available filters')).toBeVisible();
        expect(await findByText('No data has been added.'));
    });

    it('handles delete', async () => {
        const mockApi = vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        const mockDeleteApi = vi
            .mocked(generated.ReportControllerService.deleteReport)
            .mockResolvedValue({ reportUid: 2, dataSourceUid: 1 });
        const { getByRole, findByRole, findByText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        expect(mockApi).toHaveBeenCalled();

        const user = userEvent.setup();

        await user.click(await findByRole('button', { name: 'Delete' }));

        expect(await findByRole('dialog')).toHaveClass('is-visible');
        expect(await findByText(/Delete report: Test Report/)).toBeVisible();

        await user.click(await findByRole('button', { name: 'No, cancel' }));

        expect(await findByRole('dialog')).toHaveClass('is-hidden');

        await user.click(await findByRole('button', { name: 'Delete' }));

        await user.click(await findByRole('button', { name: 'Yes, delete' }));

        expect(mockDeleteApi).toHaveBeenCalledWith({ reportUid: 2, dataSourceUid: 1 });
    });
});

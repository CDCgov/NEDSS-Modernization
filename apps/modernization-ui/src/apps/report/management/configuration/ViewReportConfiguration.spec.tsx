import { render } from '@testing-library/react';
import * as generated from 'generated';
import * as options from 'options/selectableResolver';
import { Layout } from 'layout';
import { ReactNode } from 'react';
import { createMemoryRouter, RouterProvider } from 'react-router';
import { ViewReportConfiguration } from './ViewReportConfiguration';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
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

const MOCK_CONFIG: generated.ReportConfiguration = {
    title: 'Test Report',
    ownerUid: 0,
    group: 'S',
    sectionCd: '1000',
    dataSource: {
        id: 1,
        name: 'nbs_ods.data_source',
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
        { reportFilterUid: 4, isRequired: true, defaultIncludeNulls: false, filterType: { id: 1, name: 'My filter' } },
    ],
    advancedFilter: {
        reportFilterUid: 123,
    },
};

const renderWithRouter = () => {
    const routes = [
        {
            path: '/',
            element: <Layout />,
            children: [{ index: true, element: <ViewReportConfiguration /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/'] });
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
        } else {
            return Promise.resolve([]);
        }
    };

    it('renders the config', async () => {
        const mockApi = vi
            .mocked(generated.ReportControllerService.getReportConfiguration)
            .mockResolvedValue(MOCK_CONFIG);
        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);
        const { getByRole, findByText } = renderWithRouter();

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
    });
});

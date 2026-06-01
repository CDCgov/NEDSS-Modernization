import { render } from '@testing-library/react';
import * as generated from 'generated';
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
        {
            id: 2003,
            name: 'DAYS_OLD',
            title: 'Days Old',
            sourceTypeCode: 'INTEGER',
            isDisplayable: true,
            isFilterable: true,
        },
        {
            id: 2004,
            name: 'SECRET_COLUMN',
            title: 'Secret Column',
            sourceTypeCode: 'INTEGER',
            isDisplayable: false,
            isFilterable: false,
        },
        {
            id: 2005,
            name: 'CONDITION',
            title: 'Condition Code',
            sourceTypeCode: 'STRING',
            isDisplayable: false,
            isFilterable: true,
            codeDescCd: 'D',
            codesetNm: 'RACE_CODE',
        },
    ],
    basicFilters: [],
    advancedFilter: undefined,
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
    it('renders the config', async () => {
        const mockApi = vi
            .mocked(generated.ReportControllerService.getReportConfiguration)
            .mockResolvedValue(MOCK_CONFIG);
        const { getByRole, findByText } = renderWithRouter();

        expect(getByRole('status')).toHaveTextContent('Loading');

        expect(mockApi).toHaveBeenCalled();

        expect(await findByText('3. Available filters')).toBeVisible();
    });
});

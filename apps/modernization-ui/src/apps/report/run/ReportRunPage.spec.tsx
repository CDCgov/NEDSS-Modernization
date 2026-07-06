import { fireEvent, render, waitFor, within } from '@testing-library/react';
import { ReportRunPage } from './ReportRunPage';
import * as generated from 'generated';
import userEvent from '@testing-library/user-event';
import { BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Layout } from 'layout';
import { createMemoryRouter, RouterProvider, useLoaderData } from 'react-router';
import { ReactNode } from 'react';
import fileDownload from 'js-file-download';
import { axe } from 'jest-axe';
import * as options from 'options/selectableResolver';
import { ConceptOptions, useConceptOptions } from 'options/concepts';
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
vi.mock('js-file-download', { spy: true });

vi.mock('generated');
vi.mock('options/selectableResolver');
vi.mock('options/concepts/useConceptOptions', () => ({
    useConceptOptions: vi.fn(),
}));

// mock identifier to display "Save" button
vi.mock('user', () => ({
    useUser: () => ({
        state: {
            user: {
                identifier: 0,
                name: {
                    display: 'User Name',
                },
            },
        },
    }),
}));

vi.mock('libs/permission', async () => {
    const actual = await vi.importActual<typeof import('libs/permission')>('libs/permission');
    return {
        ...actual,
        Permitted: vi.fn(({ children }: { children: ReactNode }) => <>{children}</>),
        permitsAll: vi.fn(() => () => true),
    };
});

vi.mock('configuration', () => {
    return {
        useConfiguration: () => ({ ready: true, properties: { entries: { NBS_STATE_CODE: '13' } } }),
    };
});

vi.mock('design-system/inPageNavigation/useInPageNavigation', () => ({
    __esModule: true,
    default: vi.fn(),
}));

// don't actually let the cache cache
const localStorageMock: Storage = {
    getItem: (): string | null => null,
    setItem: (): void => {},
    removeItem: (): void => {},
    clear: (): void => {},
    key: (): string | null => '',
    length: 0,
};

// mock location to see if redirect works
const locationMock = {
    href: '',
} as Location;

let originalLocalStorage: Storage;
let originalWindow: Location;
beforeAll((): void => {
    originalLocalStorage = window.localStorage;
    originalWindow = window.location;
    (window as any).localStorage = localStorageMock;
    (window as any).location = locationMock;
});

afterAll((): void => {
    (window as any).localStorage = originalLocalStorage;
    (window as any).location = originalWindow;
});

afterEach(() => {
    vi.restoreAllMocks();
});

const MOCK_CONFIG: ReportConfiguration = {
    title: 'Test Report',
    ownerUid: 0,
    group: ReportConfiguration.group.PUBLIC,
    sectionCd: '1000',
    dataSource: {
        id: 1,
        name: 'nbs_ods.data_source',
        hasJurisdictionSecurity: false,
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

const MOCK_BASIC_FILTER = {
    reportFilterUid: 1001,
    filterType: {
        id: 18,
        codeTable: undefined,
        descTxt: 'Basic Text Filter',
        code: 'TXT_01',
        codeSetName: undefined,
        type: 'BAS_TXT',
        name: 'Basic Text Filter',
    },
    isRequired: false,
    defaultIncludeNulls: false,
};

const MOCK_RESULT: generated.ReportExecutionResult = {
    result: {
        content_type: generated.LibraryExecutionResult.content_type.TABLE,
        header: 'Title',
        content: 'I am the result',
    },
    query: 'SELECT * FROM [NBS_ODSE].[dbo].[PHC_Demographic]',
    timestamp: '2026-06-17T19:11:35.595501658',
};

const MOCK_SAVE_RESULT: generated.ReportId = {
    reportUid: 1,
    dataSourceUid: 2,
};

const renderWithRouter = () => {
    const routes = [
        {
            path: '/:reportUid/:dataSourceUid',
            element: <Layout />,
            HydrateFallback: LoadingBlock,
            children: [{ index: true, element: <ReportRunPage /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/2/1'] });
    return render(<RouterProvider router={router} />);
};
describe('report run page', () => {
    describe('when given valid params', () => {
        it('renders the config', async () => {
            const mockApi = vi
                .mocked(useLoaderData)
                .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_BASIC_FILTER] });
            const { findByText, queryByText } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('On this page')).toBeVisible();
            expect(queryByText('No filters enabled')).toBeNull();
        });

        it('renders the no filter block when no filters', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
            const { findByText, queryByText } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('No filters available')).toBeVisible();
            expect(queryByText('On this page')).toBeNull();
        });

        it('run button submits config and opens in new tab', async () => {
            const user = userEvent.setup();
            vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
            const mockApi = vi.mocked(generated.ReportControllerService.runReport).mockResolvedValue(MOCK_RESULT);
            const windowOpen = vi.spyOn(window, 'open');

            const { findAllByRole, findByText } = renderWithRouter();

            const runButton = (await findAllByRole('button', { name: 'Run' }))[0];
            await user.click(runButton);
            expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: false }) });

            expect(await findByText(/Your report has opened in a new tab/)).toBeVisible();
            expect(windowOpen).toHaveBeenCalled();
            expect(fileDownload).not.toHaveBeenCalled();
        });

        it('export button submits config and downloads', async () => {
            const user = userEvent.setup();
            vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
            const mockApi = vi.mocked(generated.ReportControllerService.exportReport).mockResolvedValue(MOCK_RESULT);
            const windowOpen = vi.spyOn(window, 'open');

            const { findAllByRole, findByText } = renderWithRouter();

            const runButton = (await findAllByRole('button', { name: 'Export' }))[0];
            await user.click(runButton);
            expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: true }) });

            expect(await findByText(/Your report has downloaded/)).toBeVisible();
            expect(windowOpen).not.toHaveBeenCalled();
            expect(fileDownload).toHaveBeenCalled();
        });

        it('save button saves report execution request', async () => {
            const user = userEvent.setup();
            vi.mocked(useLoaderData).mockReturnValue(MOCK_CONFIG);
            const mockApi = vi.mocked(generated.ReportControllerService.runReport).mockResolvedValue(MOCK_RESULT);
            const mockSaveApi = vi
                .mocked(generated.ReportControllerService.saveReport)
                .mockResolvedValue(MOCK_SAVE_RESULT);

            const { findAllByRole, findByText, getAllByRole } = renderWithRouter();

            const runButton = (await findAllByRole('button', { name: 'Run' }))[0];
            await user.click(runButton);
            expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: false }) });

            expect(await findByText(/Your report has opened in a new tab/)).toBeVisible();
            const saveButtons = await getAllByRole('button', { name: 'Save' });
            await user.click(saveButtons[0]);

            expect(await findByText(/Overwrite saved report?/)).toBeVisible();
            await user.click(saveButtons[1]);

            expect(mockSaveApi).toHaveBeenCalledWith({
                dataSourceUid: 1,
                reportUid: 2,
                requestBody: expect.objectContaining({
                    advancedFilter: undefined,
                    basicFilters: [],
                    columnUids: undefined,
                    dataSourceUid: 1,
                    isExport: false,
                    reportUid: 2,
                    sort: undefined,
                }),
            });
            expect(window.location.href).toBe('/nbs/ManageReports.do');
        });
    });

    describe('basic filters', () => {
        describe('BasicFilter', () => {
            it('renders the column title when available', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    basicFilters: [{ ...MOCK_BASIC_FILTER, reportColumnUid: 2001 }],
                });
                const { findByText, findByLabelText, queryByText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                expect(await findByText('Basic Text Filter')).toBeVisible();
                expect(queryByText('Advanced filter')).toBeNull();
            });

            it('renders the filter name when column unavailable', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    basicFilters: [{ ...MOCK_BASIC_FILTER, reportColumnUid: 2099 }],
                });
                const { findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Basic Text Filter')).toBeVisible();
            });

            it('renders the filter name when no column', async () => {
                const mockApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_BASIC_FILTER }] });
                const { findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Basic Text Filter')).toBeVisible();
            });

            it('renders the required indicator', async () => {
                const mockApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_BASIC_FILTER, isRequired: true }] });
                const { findByText, findByRole } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByText('Basic Text Filter')).toHaveClass('required');
                expect(await findByRole('textbox')).toBeRequired();
            });

            describe('include nulls', () => {
                const NULLABLE_MOCK_BASIC_FILTER = {
                    ...MOCK_BASIC_FILTER,
                    filterType: { ...MOCK_BASIC_FILTER.filterType, code: 'J_S01_N' },
                };
                it('renders the include nulls checkbox when appropriate', async () => {
                    const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [NULLABLE_MOCK_BASIC_FILTER],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    const { container, findByLabelText, findByRole } = renderWithRouter();

                    expect(mockApi).toHaveBeenCalled();

                    const checkbox = await findByLabelText('Include Nulls for Basic Text Filter');
                    expect(checkbox).not.toBeChecked();
                    const user = userEvent.setup();
                    await user.click(checkbox);
                    expect(checkbox).toBeChecked();

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            advancedFilter: undefined,
                            basicFilters: [{ reportFilterUid: 1001, values: ['13'], includeNulls: true }],
                        }),
                    });
                });

                it('starts from default value', async () => {
                    const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...NULLABLE_MOCK_BASIC_FILTER, defaultIncludeNulls: true }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    const { container, findByLabelText, findByRole } = renderWithRouter();

                    expect(mockApi).toHaveBeenCalled();

                    const checkbox = await findByLabelText('Include Nulls for Basic Text Filter');
                    expect(checkbox).toBeChecked();
                    const user = userEvent.setup();
                    await user.click(checkbox);
                    expect(checkbox).not.toBeChecked();

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            advancedFilter: undefined,
                            basicFilters: [{ reportFilterUid: 1001, values: ['13'], includeNulls: false }],
                        }),
                    });
                });
            });
        });

        describe('BAS_TXT', () => {
            const MOCK_FILTER = {
                reportFilterUid: 1001,
                filterType: {
                    id: 18,
                    codeTable: undefined,
                    descTxt: 'Basic Text Filter',
                    code: 'TXT_01',
                    codeSetName: undefined,
                    type: 'BAS_TXT',
                    name: 'Basic Text Filter',
                },
                isRequired: true,
                reportColumnUid: 2001,
                defaultIncludeNulls: false,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findAllByText('Other filters')).toHaveLength(2);

                const input = await findByLabelText('Full Name');
                await userEvent.type(input, 'test');

                expect(input).toHaveValue('test');

                expect(await axe(container)).toHaveNoViolations();

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['test'] }],
                    }),
                });
            });

            it('does not submit on required', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Full Name');
                expect(input).toHaveValue('');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(input).toBeInvalid();
                expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                expect(mockResultApi).not.toHaveBeenCalled();
            });

            it('renders default value', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    basicFilters: [{ ...MOCK_FILTER, defaultValues: ['starter text'] }],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Full Name');
                expect(input).toHaveValue('starter text');

                await userEvent.type(input, ' and more');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['starter text and more'] }],
                    }),
                });
            });
        });

        ['BAS_TIM_RANGE', 'BAS_TIM_RANGE_CUSTOM'].forEach((filterType) => {
            describe(filterType, () => {
                const MOCK_FILTER = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: undefined,
                        descTxt: 'Basic Time Filter for Time Range accepts MM;YYYY to MM;YYYY',
                        code: 'T_T01',
                        codeSetName: undefined,
                        type: filterType,
                        name: 'Time Range',
                    },
                    isRequired: true,
                    reportColumnUid: 2001,
                    defaultIncludeNulls: false,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);

                    const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findAllByText('Time')).toHaveLength(2);

                    expect(await findByLabelText('Full Name')).toBeVisible();
                    const fromInput = await findByLabelText('From');
                    const toInput = await findByLabelText('To');
                    await userEvent.type(fromInput, '01/01/2025');
                    await userEvent.type(toInput, '01/01/2026');

                    expect(fromInput).toHaveValue('01/01/2025');
                    expect(toInput).toHaveValue('01/01/2026');

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            advancedFilter: undefined,
                            basicFilters: [{ reportFilterUid: 1001, values: ['01/01/2025', '01/01/2026'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByLabelText('Full Name')).toBeVisible();
                    const fromInput = await findByLabelText('From');
                    const toInput = await findByLabelText('To');

                    expect(fromInput).toHaveValue('');
                    expect(toInput).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(fromInput).toBeInvalid();
                    expect(toInput).toBeInvalid();
                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['01/01/2024', '01/01/2025'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);

                    const { findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByLabelText('Full Name')).toBeVisible();
                    const fromInput = await findByLabelText('From');
                    const toInput = await findByLabelText('To');

                    expect(fromInput).toHaveValue('01/01/2024');
                    expect(toInput).toHaveValue('01/01/2025');

                    await userEvent.type(fromInput, '{backspace}3');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            advancedFilter: undefined,
                            basicFilters: [{ reportFilterUid: 1001, values: ['01/01/2023', '01/01/2025'] }],
                        }),
                    });
                });
            });
        });
        describe('BAS_TIM_RANGE_LIST', () => {
            const MOCK_FILTER = {
                reportFilterUid: 1001,
                filterType: {
                    id: 5,
                    codeTable: undefined,
                    descTxt: 'Basic Time Filter for Time Range accepts MM;YYYY to MM;YYYY',
                    code: 'T_T02',
                    codeSetName: undefined,
                    type: 'BAS_TIM_RANGE_LIST',
                    name: 'Time Period',
                },
                isRequired: true,
                reportColumnUid: 2001,
                defaultIncludeNulls: false,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findAllByText('Time')).toHaveLength(2);

                expect(await findByLabelText('Full Name')).toBeVisible();
                const fromInput = await findByLabelText('From');
                const toInput = await findByLabelText('To');
                await userEvent.selectOptions(fromInput, '2025');
                await userEvent.selectOptions(toInput, '2026');

                expect(fromInput).toHaveValue('2025');
                expect(toInput).toHaveValue('2026');

                expect(await axe(container)).toHaveNoViolations();

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['2025', '2026'] }],
                    }),
                });
            });

            it('does not submit on required', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                const fromInput = await findByLabelText('From');
                const toInput = await findByLabelText('To');

                const thisYear = new Date().getFullYear();

                expect(fromInput).toHaveValue(`${thisYear - 20}`);
                expect(toInput).toHaveValue(`${thisYear}`);

                await userEvent.selectOptions(fromInput, '');
                await userEvent.selectOptions(toInput, '');

                expect(fromInput).toHaveValue('');
                expect(toInput).toHaveValue('');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(fromInput).toBeInvalid();
                expect(toInput).toBeInvalid();
                expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                expect(mockResultApi).not.toHaveBeenCalled();
            });

            it('renders default value', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    basicFilters: [{ ...MOCK_FILTER, defaultValues: ['2024', '2025'] }],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                const fromInput = await findByLabelText('From');
                const toInput = await findByLabelText('To');

                expect(fromInput).toHaveValue('2024');
                expect(toInput).toHaveValue('2025');

                await userEvent.selectOptions(fromInput, '2023');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['2023', '2025'] }],
                    }),
                });
            });
        });

        describe('BAS_MM_YYYY_RANGE', () => {
            const MOCK_FILTER = {
                reportFilterUid: 1001,
                filterType: {
                    id: 5,
                    codeTable: undefined,
                    descTxt: 'Basic Time Filter for Time Range accepts MM;YYYY to MM;YYYY',
                    code: 'T_T01',
                    codeSetName: undefined,
                    type: 'BAS_MM_YYYY_RANGE',
                    name: 'Month Year Range',
                },
                isRequired: true,
                reportColumnUid: 2001,
                defaultIncludeNulls: false,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findAllByText('Time')).toHaveLength(2);

                expect(await findByLabelText('Full Name')).toBeVisible();
                const fromMonthInput = await findByLabelText('From Month');
                await userEvent.selectOptions(fromMonthInput, '1');
                const fromYearInput = await findByLabelText('From Year');
                await userEvent.selectOptions(fromYearInput, '2025');
                const toMonthInput = await findByLabelText('To Month');
                await userEvent.selectOptions(toMonthInput, '1');
                const toYearInput = await findByLabelText('To Year');
                await userEvent.selectOptions(toYearInput, '2026');

                expect(fromMonthInput).toHaveValue('1');
                expect(fromYearInput).toHaveValue('2025');
                expect(toMonthInput).toHaveValue('1');
                expect(toYearInput).toHaveValue('2026');

                expect(await axe(container)).toHaveNoViolations();

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['01/2025', '01/2026'] }],
                    }),
                });
            });

            it('does not submit on required', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                const fromMonthInput = await findByLabelText('From Month');
                const fromYearInput = await findByLabelText('From Year');
                const toMonthInput = await findByLabelText('To Month');
                const toYearInput = await findByLabelText('To Year');

                expect(fromMonthInput).toHaveValue('');
                expect(fromYearInput).toHaveValue('');
                expect(toMonthInput).toHaveValue('');
                expect(toYearInput).toHaveValue('');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(fromMonthInput).toBeInvalid();
                expect(fromYearInput).toBeInvalid();
                expect(toMonthInput).toBeInvalid();
                expect(toYearInput).toBeInvalid();
                expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                expect(mockResultApi).not.toHaveBeenCalled();
            });

            it('renders default value', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    basicFilters: [{ ...MOCK_FILTER, defaultValues: ['01/2024', '01/2025'] }],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                const fromMonthInput = await findByLabelText('From Month');
                const fromYearInput = await findByLabelText('From Year');
                const toMonthInput = await findByLabelText('To Month');
                const toYearInput = await findByLabelText('To Year');

                expect(fromMonthInput).toHaveValue('1');
                expect(fromYearInput).toHaveValue('2024');
                expect(toMonthInput).toHaveValue('1');
                expect(toYearInput).toHaveValue('2025');

                await userEvent.selectOptions(fromMonthInput, '3');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['03/2024', '01/2025'] }],
                    }),
                });
            });
        });

        describe('BAS_JUR_LIST', () => {
            describe('State', () => {
                describe('single select', () => {
                    const MOCK_FILTER: BasicFilterConfiguration = {
                        reportFilterUid: 1001,
                        filterType: {
                            id: 5,
                            codeTable: undefined,
                            descTxt: 'Basic State Filter',
                            code: 'J_S01_N',
                            codeSetName: undefined,
                            type: 'BAS_JUR_LIST',
                            name: 'State',
                        },
                        isRequired: true,
                        selectType: BasicFilterConfiguration.selectType.SINGLE,
                        reportColumnUid: 2001,
                        defaultIncludeNulls: false,
                    };
                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        expect(await findAllByText('Geographic area')).toHaveLength(2);

                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        await userEvent.selectOptions(dropDown, '13');

                        expect(dropDown).toHaveValue('13');

                        expect(await axe(container)).toHaveNoViolations();

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: [{ reportFilterUid: 1001, values: ['13'], includeNulls: false }],
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(dropDown).toHaveValue('13'); // default state
                        await user.selectOptions(dropDown, '');

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);

                        expect(dropDown).toBeInvalid();
                        expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                        expect(mockResultApi).not.toHaveBeenCalled();
                    });

                    it('renders default value', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValues: ['13'] }],
                        });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { findByRole, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(dropDown).toHaveValue('13');

                        await userEvent.selectOptions(dropDown, '04');

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: [{ reportFilterUid: 1001, values: ['04'], includeNulls: false }],
                            }),
                        });
                    });
                });

                describe('multi select', () => {
                    const MOCK_FILTER: BasicFilterConfiguration = {
                        reportFilterUid: 1001,
                        filterType: {
                            id: 5,
                            codeTable: undefined,
                            descTxt: 'Basic State Filter',
                            code: 'J_S01_N',
                            codeSetName: undefined,
                            type: 'BAS_JUR_LIST',
                            name: 'County',
                        },
                        isRequired: true,
                        selectType: BasicFilterConfiguration.selectType.MULTI,
                        reportColumnUid: 2001,
                        defaultIncludeNulls: false,
                    };

                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByText, findByRole, findByLabelText, container } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Georgia' })).toBeVisible();
                        await user.click(dropDown);
                        await user.click(getByText('Arizona'));

                        expect(await findByRole('button', { name: 'Remove Georgia' })).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Arizona' })).toBeVisible();

                        expect(await axe(container)).toHaveNoViolations();

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: [{ reportFilterUid: 1001, values: ['13', '04'], includeNulls: false }],
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { findByRole, findAllByText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        // default state
                        expect(await findByRole('button', { name: 'Remove Georgia' })).toBeVisible();

                        await user.click(await findByRole('button', { name: 'Remove Georgia' }));

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);

                        expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                        expect(mockResultApi).not.toHaveBeenCalled();
                    });

                    it('renders default value', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValues: ['04'] }],
                        });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByText, findByRole, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('button', { name: 'Remove Arizona' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        await user.click(dropDown);
                        await user.click(getByText('Georgia'));

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: [{ reportFilterUid: 1001, values: ['04', '13'], includeNulls: false }],
                            }),
                        });
                    });
                });
            });
            describe('County', () => {
                const STATE_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1002,
                    filterType: {
                        id: 5,
                        codeTable: undefined,
                        descTxt: 'Basic State Filter',
                        code: 'J_S01',
                        codeSetName: undefined,
                        type: 'BAS_JUR_LIST',
                        name: 'State',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.SINGLE,
                    defaultValues: ['13'],
                    defaultIncludeNulls: false,
                    reportColumnUid: 2002,
                };

                const mockOptionApiImpl = (url: string) => {
                    if (url.includes('state')) {
                        return Promise.resolve([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);
                    } else if (url.includes('13')) {
                        return Promise.resolve([
                            { value: '13001', name: 'Dekalb County' },
                            { value: '13002', name: 'Clayton County' },
                        ]);
                    } else if (url.includes('04')) {
                        return Promise.resolve([
                            { value: '04001', name: 'Yuma County' },
                            { value: '04002', name: 'Phoenix County' },
                        ]);
                    } else {
                        return Promise.resolve([]);
                    }
                };
                describe('single select', () => {
                    const MOCK_FILTER: BasicFilterConfiguration = {
                        reportFilterUid: 1001,
                        filterType: {
                            id: 5,
                            codeTable: undefined,
                            descTxt: 'Basic County Filter',
                            code: 'J_C01_N',
                            codeSetName: undefined,
                            type: 'BAS_JUR_LIST',
                            name: 'County',
                        },
                        isRequired: true,
                        selectType: BasicFilterConfiguration.selectType.SINGLE,
                        reportColumnUid: 2001,
                        defaultIncludeNulls: false,
                    };
                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        expect(await findAllByText('Geographic area')).toHaveLength(2);

                        expect(await findByRole('option', { name: 'Dekalb County' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        let dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        await userEvent.selectOptions(dropDown, '13001');

                        expect(dropDown).toHaveValue('13001');

                        expect(await axe(container)).toHaveNoViolations();

                        // change state to arizona
                        const stateDropDown = await findByLabelText('Date of Birth');
                        await userEvent.selectOptions(stateDropDown, '04');

                        expect(mockConfigApi).toHaveBeenCalled();

                        dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toHaveValue('');

                        // make sure form values were really reset
                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(await findByRole('alert')).toBeVisible(); // county is required

                        dropDown = await findByLabelText('Full Name');
                        await userEvent.selectOptions(dropDown, '04001');

                        expect(dropDown).toHaveValue('04001');

                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: expect.arrayContaining([
                                    { reportFilterUid: 1001, values: ['04001'], includeNulls: false },
                                    { reportFilterUid: 1002, values: ['04'] },
                                ]),
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(dropDown).toHaveValue('');

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);

                        expect(dropDown).toBeInvalid();
                        expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                        expect(mockResultApi).not.toHaveBeenCalled();
                    });

                    it('renders default value', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                            ...MOCK_CONFIG,
                            basicFilters: [STATE_FILTER, { ...MOCK_FILTER, defaultValues: ['13001'] }],
                        });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { findByRole, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(dropDown).toHaveValue('13001');

                        await userEvent.selectOptions(dropDown, '13002');

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: expect.arrayContaining([
                                    { reportFilterUid: 1001, values: ['13002'], includeNulls: false },
                                    { reportFilterUid: 1002, values: ['13'] },
                                ]),
                            }),
                        });
                    });
                });

                describe('multi select', () => {
                    const MOCK_FILTER: BasicFilterConfiguration = {
                        reportFilterUid: 1001,
                        filterType: {
                            id: 5,
                            codeTable: undefined,
                            descTxt: 'Basic County Filter',
                            code: 'J_C01_N',
                            codeSetName: undefined,
                            type: 'BAS_JUR_LIST',
                            name: 'County',
                        },
                        isRequired: true,
                        selectType: BasicFilterConfiguration.selectType.MULTI,
                        reportColumnUid: 2001,
                        defaultIncludeNulls: false,
                    };

                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByText, findAllByText, findByRole, findByLabelText, container } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        let dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        await user.click(dropDown);
                        await user.click(getByText('Dekalb County'));
                        await user.click(dropDown);
                        await user.click(getByText('Clayton County'));

                        expect(await findByRole('button', { name: 'Remove Dekalb County' })).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Clayton County' })).toBeVisible();

                        expect(await axe(container)).toHaveNoViolations();

                        // change state to arizona
                        const stateDropDown = await findByLabelText('Date of Birth');
                        await userEvent.selectOptions(stateDropDown, '04');

                        expect(mockConfigApi).toHaveBeenCalled();

                        dropDown = await findByLabelText('Full Name');

                        // make sure form values were really reset
                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(await findAllByText('The Full Name is required.')).toHaveLength(2); // county is required

                        dropDown = await findByLabelText('Full Name');
                        await user.click(dropDown);
                        await user.click(getByText('Yuma County'));

                        expect(await findByRole('button', { name: 'Remove Yuma County' })).toBeVisible();

                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: expect.arrayContaining([
                                    { reportFilterUid: 1001, values: ['04001'], includeNulls: false },
                                    { reportFilterUid: 1002, values: ['04'] },
                                ]),
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(useLoaderData)
                            .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(dropDown).toHaveValue('');

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);

                        expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                        expect(mockResultApi).not.toHaveBeenCalled();
                    });

                    it('renders default value', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                            ...MOCK_CONFIG,
                            basicFilters: [STATE_FILTER, { ...MOCK_FILTER, defaultValues: ['13001'] }],
                        });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByText, findByRole, findByLabelText } = renderWithRouter();

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Dekalb County' })).toBeVisible();

                        await user.click(dropDown);
                        await user.click(getByText('Clayton County'));

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: expect.arrayContaining([
                                    { reportFilterUid: 1001, values: ['13001', '13002'], includeNulls: false },
                                    { reportFilterUid: 1002, values: ['13'] },
                                ]),
                            }),
                        });
                    });
                });
            });
        });

        describe('BAS_CON_LIST', () => {
            const mockOptionApiImpl = (url: string) => {
                if (url.includes('conditions')) {
                    return Promise.resolve([
                        { value: '11065', name: '2019 Novel Coronavirus' },
                        { value: '10560', name: 'AIDS' },
                    ]);
                } else {
                    return Promise.resolve([]);
                }
            };

            describe('single select', () => {
                const MOCK_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: 'nbs_srt..code_value_general',
                        descTxt: 'Basic Condition Filter Including Nulls',
                        code: 'C_D01_N',
                        codeSetName: 'PHC_TYPE',
                        type: 'BAS_CON_LIST',
                        name: 'Diseases (Including NULLS)',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.SINGLE,
                    defaultValues: [],
                    reportColumnUid: 2001,
                    defaultIncludeNulls: false,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findAllByText('Condition')).toHaveLength(2);

                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await userEvent.selectOptions(dropDown, '2019 Novel Coronavirus');

                    expect(dropDown).toHaveValue('11065');

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['11065'], includeNulls: false }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(dropDown).toBeInvalid();
                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['11065'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('11065');

                    await userEvent.selectOptions(dropDown, '10560');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['10560'], includeNulls: false }],
                        }),
                    });
                });
            });
            describe('multi select', () => {
                const MOCK_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: 'nbs_srt..code_value_general',
                        descTxt: 'Basic Condition Filter',
                        code: 'C_D01',
                        codeSetName: 'PHC_TYPE',
                        type: 'BAS_CON_LIST',
                        name: 'Diseases',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.MULTI,
                    defaultValues: [],
                    reportColumnUid: 2001,
                    defaultIncludeNulls: false,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByText, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await user.click(dropDown);
                    await user.click(getByText('2019 Novel Coronavirus'));
                    await user.click(dropDown);
                    await user.click(getByText('AIDS'));

                    expect(await findByRole('button', { name: 'Remove 2019 Novel Coronavirus' })).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove AIDS' })).toBeVisible();

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['11065', '10560'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['11065'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByText, findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove 2019 Novel Coronavirus' })).toBeVisible();

                    await user.click(dropDown);
                    await user.click(getByText('AIDS'));

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['11065', '10560'] }],
                        }),
                    });
                });
            });
        });

        describe('BAS_CVG_LIST', () => {
            const mockOptionApiImpl: ConceptOptions = {
                options: [
                    { value: '100', name: '100 - Chancroid' },
                    { value: '200', name: '200 - Chlamydia' },
                ],
                load: vi.fn(),
            };

            describe('single select', () => {
                const MOCK_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: 'nbs_srte..code_value_general',
                        descTxt: 'Basic Diagnosis Code Filter',
                        code: 'CVG_CUSTOM_N01',
                        codeSetName: 'CASE_DIAGNOSIS_STD',
                        type: 'BAS_CVG_LIST',
                        name: 'STD Case Diagnosis',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.SINGLE,
                    defaultValues: [],
                    defaultIncludeNulls: false,
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findAllByText('Condition')).toHaveLength(2);

                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await userEvent.selectOptions(dropDown, '100 - Chancroid');

                    expect(dropDown).toHaveValue('100');

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['100'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(dropDown).toBeInvalid();
                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['100'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('100');

                    await userEvent.selectOptions(dropDown, '200');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['200'] }],
                        }),
                    });
                });
            });
            describe('multi select', () => {
                const MOCK_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: 'nbs_srte..code_value_general',
                        descTxt: 'Basic Diagnosis Code Filter',
                        code: 'CVG_CUSTOM_N01',
                        codeSetName: 'CASE_DIAGNOSIS_STD',
                        type: 'BAS_CVG_LIST',
                        name: 'STD Case Diagnosis',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.MULTI,
                    defaultValues: [],
                    defaultIncludeNulls: false,
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByText, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await user.click(dropDown);
                    await user.click(getByText('100 - Chancroid'));
                    await user.click(dropDown);
                    await user.click(getByText('200 - Chlamydia'));

                    expect(await findByRole('button', { name: 'Remove 100 - Chancroid' })).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove 200 - Chlamydia' })).toBeVisible();

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['100', '200'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });
                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['200'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByText, findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove 200 - Chlamydia' })).toBeVisible();

                    await user.click(dropDown);
                    await user.click(getByText('100 - Chancroid'));

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['200', '100'] }],
                        }),
                    });
                });
            });
        });

        describe('BAS_DAYS', () => {
            const MOCK_FILTER = {
                reportFilterUid: 1001,
                filterType: {
                    id: 23,
                    codeTable: undefined,
                    descTxt: 'Days Filter for duplicate events',
                    code: 'D_01',
                    codeSetName: undefined,
                    type: 'BAS_DAYS',
                    name: 'Duplicate Investigations Time Frame',
                },
                isRequired: true,
                defaultIncludeNulls: false,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                expect(await findAllByText('Time')).toHaveLength(2);

                const input = await findByLabelText('Duplicate Investigations Time Frame');
                await user.type(input, '5');

                expect(input).toHaveValue(5);

                expect(await axe(container)).toHaveNoViolations();

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['5'] }],
                    }),
                });
            });

            it('does not submit on required', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Duplicate Investigations Time Frame');
                expect(input).toHaveValue(null);

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(await findAllByText('The Duplicate Investigations Time Frame is required.')).toHaveLength(2);
                expect(mockResultApi).not.toHaveBeenCalled();
            });

            it('does not submit on negative input value', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Duplicate Investigations Time Frame');

                // Using fireEvent.change because user.type cannot reliably simulate
                // typing negative signs into inputs with type="number"
                fireEvent.change(input, { target: { value: '-1' } });
                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(await findAllByText('Duplicate Investigations Time Frame must be at least 0.')).toHaveLength(2);
                expect(mockResultApi).not.toHaveBeenCalled();
            });

            it('do not allow negative sign input', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findByLabelText, container } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Duplicate Investigations Time Frame');
                await user.type(input, '-1');

                expect(input).toHaveValue(1);

                expect(await axe(container)).toHaveNoViolations();

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['1'] }],
                    }),
                });
            });

            it('does not submit on four digit value input value', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(useLoaderData)
                    .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Duplicate Investigations Time Frame');

                fireEvent.change(input, { target: { value: '2000' } });
                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(
                    await findAllByText('Duplicate Investigations Time Frame must not be greater than 999.')
                ).toHaveLength(2);
                expect(mockResultApi).not.toHaveBeenCalled();
            });

            it('renders default value', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    basicFilters: [{ ...MOCK_FILTER, defaultValues: ['1'] }],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockConfigApi).toHaveBeenCalled();

                const input = await findByLabelText('Duplicate Investigations Time Frame');
                expect(input).toHaveValue(1);

                await userEvent.type(input, '1');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);
                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [{ reportFilterUid: 1001, values: ['11'] }],
                    }),
                });
            });
        });

        describe('BAS_STD_HIV_WRKR', () => {
            const mockOptionApiImpl = (url: string) => {
                if (url.includes('person/std-hiv-worker/names')) {
                    return Promise.resolve([
                        { value: 'erso', name: 'Jyn Erso' },
                        { value: 'andor', name: 'Cassian Andor' },
                    ]);
                } else {
                    return Promise.resolve([]);
                }
            };

            describe('single select', () => {
                const MOCK_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: 'nbs_odse..person_name',
                        descTxt: 'Basic STD HIV Worker Filter',
                        code: 'STD_HIV_WRKR',
                        codeSetName: 'STD_HIV_WRKR',
                        type: 'BAS_STD_HIV_WRKR',
                        name: 'STD HIV Workers',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.SINGLE,
                    defaultValues: [],
                    defaultIncludeNulls: false,
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findAllByText('Other filters')).toHaveLength(2);

                    expect(await findByRole('option', { name: 'Jyn Erso' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await userEvent.selectOptions(dropDown, 'Jyn Erso');

                    expect(dropDown).toHaveValue('erso');

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['erso'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByRole('option', { name: 'Jyn Erso' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(dropDown).toBeInvalid();
                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['erso'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    expect(await findByRole('option', { name: 'Jyn Erso' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('erso');

                    await userEvent.selectOptions(dropDown, 'andor');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['andor'] }],
                        }),
                    });
                });
            });
            describe('multi select', () => {
                const MOCK_FILTER: BasicFilterConfiguration = {
                    reportFilterUid: 1001,
                    filterType: {
                        id: 5,
                        codeTable: 'nbs_odse..person_name',
                        descTxt: 'Basic STD HIV Worker Filter',
                        code: 'STD_HIV_WRKR',
                        codeSetName: 'STD_HIV_WRKR',
                        type: 'BAS_STD_HIV_WRKR',
                        name: 'STD HIV Workers',
                    },
                    isRequired: true,
                    selectType: BasicFilterConfiguration.selectType.MULTI,
                    defaultValues: [],
                    defaultIncludeNulls: false,
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByText, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: 'Jyn Erso' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await user.click(dropDown);
                    await user.click(getByText('Jyn Erso'));
                    await user.click(dropDown);
                    await user.click(getByText('Cassian Andor'));

                    expect(await findByRole('button', { name: 'Remove Jyn Erso' })).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove Cassian Andor' })).toBeVisible();

                    expect(await axe(container)).toHaveNoViolations();

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['erso', 'andor'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(useLoaderData)
                        .mockReturnValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: 'Jyn Erso' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(dropDown).toHaveValue('');

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);

                    expect(await findAllByText('The Full Name is required.')).toHaveLength(2);
                    expect(mockResultApi).not.toHaveBeenCalled();
                });

                it('renders default value', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi.mocked(useLoaderData).mockReturnValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValues: ['andor'] }],
                    });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByText, findByRole, findByLabelText } = renderWithRouter();

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: 'Cassian Andor' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove Cassian Andor' })).toBeVisible();

                    await user.click(dropDown);
                    await user.click(getByText('Jyn Erso'));

                    const exportButton = await findByRole('button', { name: 'Export' });
                    await user.click(exportButton);
                    expect(mockResultApi).toHaveBeenCalledWith({
                        requestBody: expect.objectContaining({
                            isExport: true,
                            basicFilters: [{ reportFilterUid: 1001, values: ['andor', 'erso'] }],
                        }),
                    });
                });
            });
        });
    });

    describe('advanced filter', () => {
        const MOCK_FILTER: generated.AdvancedFilterConfiguration = {
            reportFilterUid: 1001,
            defaultValue: undefined,
        };

        it('renders the empty filter builder when no default value', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { findAllByText, queryByText, findByRole, findByLabelText, getByLabelText, container } =
                renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findAllByText('Advanced filter')).toHaveLength(2);
            expect(queryByText('Basic filters')).toBeNull();

            const fieldSelect = getByLabelText('Field');
            expect(fieldSelect).toHaveValue('~');
            const user = userEvent.setup();
            await user.selectOptions(fieldSelect, 'Full Name');
            const opSelect = await findByLabelText('Logic');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'contains');
            const valueBox = await findByLabelText('Value');
            expect(valueBox).toHaveValue('');
            await user.type(valueBox, 'hi');

            expect(await axe(container)).toHaveNoViolations();

            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: expect.stringMatching(/[0-9-]+/),
                            combinator: 'AND',
                            rules: [
                                {
                                    id: expect.stringMatching(/[0-9-]+/),
                                    columnId: 2001,
                                    operator: 'CO',
                                    value: 'hi',
                                },
                            ],
                        },
                    },
                    basicFilters: [],
                }),
            });
        });

        it('allows submit when empty', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { findAllByText, findByRole } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findAllByText('Advanced filter')).toHaveLength(2);

            const exportButton = await findByRole('button', { name: 'Export' });
            const user = userEvent.setup();
            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: undefined,
                    basicFilters: [],
                }),
            });
        });

        it('validates rule states', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const codedValueGetter = vi.mocked(options.selectableResolver).mockResolvedValue([
                { value: '123', name: 'Terrible disease' },
                { value: '456', name: 'Not so awful disease' },
            ]);
            const {
                queryByText,
                getByText,
                findByText,
                findAllByText,
                getByLabelText,
                getByTestId,
                findByLabelText,
                findByRole,
            } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findAllByText('Advanced filter')).toHaveLength(2);

            const fieldSelect = getByLabelText('Field');
            expect(fieldSelect).toHaveValue('~');
            const user = userEvent.setup();
            await user.selectOptions(fieldSelect, 'Full Name');

            // trigger validation
            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(await findByText('Enter a logic value for Full Name.')).toBeVisible();

            // generally filled in text value
            const opSelect = getByLabelText('Logic');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'contains');

            expect(await findByText('Enter a value for Full Name.')).toBeVisible();

            const valueBox = getByLabelText('Value');
            expect(valueBox).toHaveValue('');
            await user.type(valueBox, 'hi');

            expect(queryByText('Enter a value for Full Name.')).toBeNull();

            // generally filled in number value
            await user.selectOptions(fieldSelect, 'DAYS_OLD');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, '=');

            expect(await findByText('Enter a value for Days Old.')).toBeVisible();

            const numberBox = await findByLabelText('Value');
            expect(numberBox).toHaveValue(null);
            await user.type(numberBox, '0{tab}');

            expect(queryByText('Enter a value for Days Old.')).toBeNull();

            // generally filled in coded list
            await user.selectOptions(fieldSelect, 'Condition Code');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'in');

            expect(await findByText('Enter a value for Condition Code.')).toBeVisible();

            await waitFor(() => expect(codedValueGetter).toHaveBeenCalledWith(`/nbs/api/options/races`));

            const dropDown = await findByLabelText('Value');
            expect(dropDown).toBeVisible();
            await user.click(dropDown);
            await user.click(getByText('Terrible disease'));

            expect(queryByText('Enter a value for Condition Code.')).toBeNull();

            // dates between
            await user.selectOptions(fieldSelect, 'DATE_OF_BIRTH');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'between');

            expect(await findByText('Enter From and To values for Date of Birth.')).toBeVisible();

            // The date entry will likely need to change once we switch to NBS components
            const dtContainer = getByTestId('date-range-editor');
            const dtInputFrom = await within(dtContainer).findByLabelText('From');
            const dtInputTo = await within(dtContainer).findByLabelText('To');
            await user.type(dtInputFrom, '10/18/2022{tab}');

            expect(await findByText('Enter From and To values for Date of Birth.')).toBeVisible();

            await user.type(dtInputTo, '10/17/2022{tab}');

            expect(await findByText('From date must be before To date for Date of Birth.')).toBeVisible();
            await user.clear(dtInputTo);
            await user.type(dtInputTo, '10/20/2022{tab}');

            expect(queryByText('From date must be before To date for Date of Birth.')).toBeNull();

            // numbers between
            await user.selectOptions(fieldSelect, 'DAYS_OLD');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'between');

            expect(await findByText('Enter From and To values for Days Old.')).toBeVisible();

            const numContainer = getByTestId('number-range-editor');
            const numInputFrom = await within(numContainer).findByLabelText('From');
            const numInputTo = await within(numContainer).findByLabelText('To');
            await user.type(numInputFrom, '10');

            expect(await findByText('Enter From and To values for Days Old.')).toBeVisible();

            await user.type(numInputTo, '0');

            expect(await findByText('From value must be before To value for Days Old.')).toBeVisible();

            await user.clear(numInputTo);
            await user.type(numInputTo, '20');

            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: expect.stringMatching(/[0-9-]+/),
                            combinator: 'AND',
                            rules: [
                                {
                                    id: expect.stringMatching(/[0-9-]+/),
                                    columnId: 2003,
                                    operator: 'BW',
                                    value: '10,20',
                                },
                            ],
                        },
                    },
                    basicFilters: [],
                }),
            });
        });

        it('starts from default value', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                ...MOCK_CONFIG,
                advancedFilter: {
                    ...MOCK_FILTER,
                    defaultValue: {
                        id: '123-123-123',
                        combinator: generated.RuleGroup.combinator.OR,
                        rules: [
                            {
                                id: '124-124-124',
                                columnId: 2001,
                                operator: 'SW',
                                value: 'prefix',
                            },
                            {
                                id: '125-125-125',
                                combinator: generated.RuleGroup.combinator.AND,
                                rules: [
                                    {
                                        id: '126-126-126',
                                        columnId: 2002,
                                        operator: 'GT',
                                        value: '01/01/2020',
                                    },
                                    {
                                        id: '127-127-127',
                                        columnId: 2003,
                                        operator: 'BW',
                                        value: '10,20',
                                    },
                                ],
                            },
                            {
                                id: '128-128-128',
                                columnId: 2005,
                                operator: 'NE',
                                value: 'Disease, terrible|Disease, not so bad',
                            },
                        ],
                    },
                    query: "([Column] STARTS WITH 'prefix')",
                },
            });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            vi.mocked(options.selectableResolver).mockResolvedValue([
                { value: '123', name: 'Disease, terrible' },
                { value: '456', name: 'Disease, not so bad' },
            ]);
            const { getByTestId, findAllByText, findByRole, queryByRole, findAllByLabelText } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findAllByText('Advanced filter')).toHaveLength(2);
            // no parse warning
            expect(queryByRole('alert')).toBeNull();

            const combinators = await findAllByLabelText('Combinator');
            expect(combinators).toHaveLength(2);
            expect(combinators[0]).toHaveValue('or');
            expect(combinators[1]).toHaveValue('and');

            const fields = await findAllByLabelText('Field');
            expect(fields).toHaveLength(4);
            expect(fields[0]).toHaveValue('FULL_NAME');
            expect(fields[1]).toHaveValue('DATE_OF_BIRTH');
            expect(fields[2]).toHaveValue('DAYS_OLD');
            expect(fields[3]).toHaveValue('CONDITION');

            const operators = await findAllByLabelText('Logic');
            expect(operators).toHaveLength(4);
            expect(operators[0]).toHaveValue('beginswith');
            expect(operators[1]).toHaveValue('>');
            expect(operators[2]).toHaveValue('between');
            expect(operators[3]).toHaveValue('notIn');

            const values = await findAllByLabelText('Value');
            expect(values).toHaveLength(3);
            expect(values[0]).toHaveValue('prefix');
            expect(values[1]).toHaveValue('01/01/2020');
            const numContainer = getByTestId('number-range-editor');
            const numLow = await within(numContainer).findByLabelText('From');
            const numHigh = await within(numContainer).findByLabelText('To');
            expect(numLow).toHaveValue(10);
            expect(numHigh).toHaveValue(20);
            expect(await findByRole('button', { name: 'Remove Disease, terrible' })).toBeVisible();
            expect(await findByRole('button', { name: 'Remove Disease, not so bad' })).toBeVisible();

            const user = userEvent.setup();
            await user.type(numHigh, '1');
            expect(numHigh).toHaveValue(201);

            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: '123-123-123',
                            combinator: generated.RuleGroup.combinator.OR,
                            rules: [
                                {
                                    id: '124-124-124',
                                    columnId: 2001,
                                    operator: 'SW',
                                    value: 'prefix',
                                },
                                {
                                    id: '125-125-125',
                                    combinator: generated.RuleGroup.combinator.AND,
                                    rules: [
                                        {
                                            id: '126-126-126',
                                            columnId: 2002,
                                            operator: 'GT',
                                            // format should be mm/dd/yyyy when we switch components
                                            value: '01/01/2020',
                                        },
                                        {
                                            id: '127-127-127',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,201',
                                        },
                                    ],
                                },
                                {
                                    id: '128-128-128',
                                    columnId: 2005,
                                    operator: 'NE',
                                    value: 'Disease, terrible|Disease, not so bad',
                                },
                            ],
                        },
                    },
                    basicFilters: [],
                }),
            });
        });

        it('renders the saved filter error when available', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                ...MOCK_CONFIG,
                advancedFilter: { ...MOCK_FILTER, exceptionMessage: 'Could not parse filter', query: '( AND OR )' },
            });
            const { findAllByText, findByRole } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findAllByText('Advanced filter')).toHaveLength(2);

            const warning = await findByRole('alert');
            expect(warning).toHaveAccessibleDescription(
                /The saved filter contains an error that prevents it from loading/
            );
            expect(warning).toHaveAccessibleDescription(/Error: Could not parse filter/);
            expect(warning).toHaveAccessibleDescription(/Saved filter query: \( AND OR \)/);
        });

        describe('keyboard drag and drop', () => {
            it('happy path', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    advancedFilter: {
                        ...MOCK_FILTER,
                        defaultValue: {
                            id: '123-123-123',
                            combinator: generated.RuleGroup.combinator.OR,
                            rules: [
                                {
                                    id: '124-124-124',
                                    columnId: 2001,
                                    operator: 'SW',
                                    value: 'prefix',
                                },
                                {
                                    id: '125-125-125',
                                    combinator: generated.RuleGroup.combinator.AND,
                                    rules: [
                                        {
                                            id: '127-127-127',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,20',
                                        },
                                    ],
                                },
                                {
                                    id: '128-128-128',
                                    combinator: generated.RuleGroup.combinator.OR,
                                    rules: [
                                        {
                                            id: '129-129-129',
                                            columnId: 2002,
                                            operator: 'GT',
                                            value: '01/01/2020',
                                        },
                                        {
                                            id: '130-130-130',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,20',
                                        },
                                    ],
                                },
                            ],
                        },
                    },
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);
                const { findAllByText, findByRole, findByTestId, findAllByTestId } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findAllByText('Advanced filter')).toHaveLength(2);

                const user = userEvent.setup();
                const ruleGroupHandle = async () => await findByTestId('drag-handle-128-128-128');
                const announcementEl = await findByTestId('announcement');

                let ruleGroups = await findAllByTestId('rule-group');
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).not.toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());
                expect(announcementEl).toHaveTextContent('');

                // activate handle and move up into above group
                await user.type(await ruleGroupHandle(), ' ');
                await waitFor(() => expect(announcementEl).toHaveTextContent('You have lifted a group at path 3'));
                await user.keyboard('{ArrowUp}');
                await waitFor(() =>
                    expect(announcementEl).toHaveTextContent('You have moved the group up to path 2-2')
                );

                ruleGroups = await findAllByTestId('rule-group');
                expect(await ruleGroupHandle()).toHaveFocus();
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());

                // move back down and make sure restores
                await user.keyboard('{ArrowDown}');
                await waitFor(() =>
                    expect(announcementEl).toHaveTextContent('You have moved the group down to path 3')
                );

                ruleGroups = await findAllByTestId('rule-group');
                expect(await ruleGroupHandle()).toHaveFocus();
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).not.toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());

                // move back up and above rule and deactivate
                await user.keyboard('{ArrowUp}{ArrowUp} ');
                await waitFor(() => expect(announcementEl).toHaveTextContent('You have dropped the group at path 2-1'));

                ruleGroups = await findAllByTestId('rule-group');
                expect(await ruleGroupHandle()).toHaveFocus();
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());

                // nothing should change, since not active
                await user.keyboard('{ArrowUp}');
                expect(announcementEl).toHaveTextContent('You have dropped the group at path 2-1');

                ruleGroups = await findAllByTestId('rule-group');
                expect(await ruleGroupHandle()).toHaveFocus();
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());

                // check escape handling works, should not change from previous
                await user.keyboard(' {ArrowUp}{Escape}{ArrowDown}');
                await waitFor(() =>
                    expect(announcementEl).toHaveTextContent('The group has returned to its starting position')
                );

                ruleGroups = await findAllByTestId('rule-group');
                await waitFor(async () => expect(await ruleGroupHandle()).toHaveFocus());
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: {
                            reportFilterUid: 1001,
                            value: {
                                id: '123-123-123',
                                combinator: generated.RuleGroup.combinator.OR,
                                rules: [
                                    {
                                        id: '124-124-124',
                                        columnId: 2001,
                                        operator: 'SW',
                                        value: 'prefix',
                                    },
                                    {
                                        id: '125-125-125',
                                        combinator: generated.RuleGroup.combinator.AND,
                                        rules: [
                                            {
                                                id: '128-128-128',
                                                combinator: generated.RuleGroup.combinator.OR,
                                                rules: [
                                                    {
                                                        id: '129-129-129',
                                                        columnId: 2002,
                                                        operator: 'GT',
                                                        // format should be mm/dd/yyyy when we switch components
                                                        value: '01/01/2020',
                                                    },
                                                    {
                                                        id: '130-130-130',
                                                        columnId: 2003,
                                                        operator: 'BW',
                                                        value: '10,20',
                                                    },
                                                ],
                                            },
                                            {
                                                id: '127-127-127',
                                                columnId: 2003,
                                                operator: 'BW',
                                                value: '10,20',
                                            },
                                        ],
                                    },
                                ],
                            },
                        },
                        basicFilters: [],
                    }),
                });
            });

            it('cancels on mouse interaction', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_CONFIG,
                    advancedFilter: {
                        ...MOCK_FILTER,
                        defaultValue: {
                            id: '123-123-123',
                            combinator: generated.RuleGroup.combinator.OR,
                            rules: [
                                {
                                    id: '124-124-124',
                                    columnId: 2001,
                                    operator: 'SW',
                                    value: 'prefix',
                                },
                                {
                                    id: '125-125-125',
                                    combinator: generated.RuleGroup.combinator.AND,
                                    rules: [
                                        {
                                            id: '127-127-127',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,20',
                                        },
                                    ],
                                },
                                {
                                    id: '128-128-128',
                                    combinator: generated.RuleGroup.combinator.OR,
                                    rules: [
                                        {
                                            id: '129-129-129',
                                            columnId: 2002,
                                            operator: 'GT',
                                            // format should be mm/dd/yyyy when we switch components
                                            value: '2020-01-01',
                                        },
                                        {
                                            id: '130-130-130',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,20',
                                        },
                                    ],
                                },
                            ],
                        },
                    },
                });
                const { findAllByText, findAllByRole, findByTestId, findAllByTestId } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findAllByText('Advanced filter')).toHaveLength(2);

                const user = userEvent.setup();
                const ruleGroupHandle = async () => await findByTestId('drag-handle-128-128-128');
                const announcementEl = await findByTestId('announcement');

                let ruleGroups = await findAllByTestId('rule-group');
                expect(ruleGroups[0]).toContainElement(await ruleGroupHandle());
                expect(ruleGroups[1]).not.toContainElement(await ruleGroupHandle());
                expect(ruleGroups[2]).toContainElement(await ruleGroupHandle());
                expect(announcementEl).toHaveTextContent('');

                // activate handle and move up into above group
                await user.type(await ruleGroupHandle(), ' ');
                await waitFor(() => expect(announcementEl).toHaveTextContent('You have lifted a group at path 3'));
                await user.keyboard('{ArrowUp}');
                await waitFor(() =>
                    expect(announcementEl).toHaveTextContent('You have moved the group up to path 2-2')
                );

                const addRuleBtn = (await findAllByRole('button', { name: 'Add rule' }))[0];
                await user.click(addRuleBtn);
                await waitFor(() =>
                    expect(announcementEl).toHaveTextContent('The group has returned to its starting position')
                );
                expect(addRuleBtn).toHaveFocus();
            });
        });
    });

    describe('column selection', () => {
        const MOCK_SELECTABLE_CONFIG: ReportConfiguration = {
            ...MOCK_CONFIG,
            library: { ...MOCK_CONFIG.library, allowColumnSelection: true },
        };

        it('happy path', async () => {
            const mockApi = vi.mocked(useLoaderData).mockReturnValue(MOCK_SELECTABLE_CONFIG);
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { container, findByText, findAllByText, queryByText, findByRole, findAllByRole, findByLabelText } =
                renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            expect(await findAllByText('Column selection')).toHaveLength(2);
            expect(queryByText('Basic filters')).toBeNull();
            expect(queryByText('Advanced filter')).toBeNull();

            // starts unselected
            expect(await findByText('Select a column from "Available columns"')).toBeVisible();
            expect(await findByRole('button', { name: 'Clear selections' })).toBeDisabled();

            const options = () => findAllByRole('checkbox');
            expect(await options()).toHaveLength(MOCK_CONFIG.columns.length - 1); // -2 for non-displayable +1 for select all
            (await options()).forEach((option) => expect(option).not.toBeChecked());

            const user = userEvent.setup();

            await user.click((await options())[0]); // select all
            (await options()).forEach((option, i) => {
                if (i == 0) {
                    expect(option).not.toBeChecked();
                    expect(option).toHaveAccessibleName('Deselect all');
                } else {
                    expect(option).toBeChecked();
                }
            });
            await user.click((await options())[0]); // de-select all
            (await options()).forEach((option) => expect(option).not.toBeChecked());

            const search = await findByLabelText('Search');
            await user.type(search, 'name');
            expect(await options()).toHaveLength(2); // Full Name + select all
            await user.click(await findByLabelText('Select search results'));
            await waitFor(async () =>
                (await options()).forEach((option, i) => {
                    if (i == 0) {
                        expect(option).not.toBeChecked();
                        expect(option).toHaveAccessibleName('Deselect search results');
                    } else {
                        expect(option).toBeChecked();
                    }
                })
            );
            await user.clear(search);
            expect((await options()).filter((o) => (o as HTMLInputElement).checked)).toHaveLength(1);
            expect(await findByRole('checkbox', { name: 'Full Name' })).toBeChecked();
            expect(await findByLabelText('Remove Full Name')).toBeVisible(); // in ordering panel
            await user.click(await findByLabelText('Remove Full Name'));
            await waitFor(async () => (await options()).forEach((option) => expect(option).not.toBeChecked()));

            // trigger validation
            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(await findByText('The column selection is required.')).toBeVisible();

            await user.click(await findByLabelText('Select all'));

            expect(queryByText('The column selection is required.')).toBeNull();

            await user.click(await findByRole('button', { name: 'Clear selections' }));

            expect(await findByText('The column selection is required.')).toBeVisible();

            // check drag and drop, the library uses keycodes (deprecated) instead of names,
            // so requires this kinda hacky workaround vs user events
            const fireDnDEvent = (keyCode: number) => {
                fireEvent.keyDown(dragHandle, {
                    keyCode,
                });
            };
            await user.click(await findByLabelText('Select all'));
            const dragHandle = await findByLabelText('Drag handle for Full Name');
            dragHandle.focus();
            fireDnDEvent(32); // space
            fireDnDEvent(40); // down
            fireDnDEvent(40); // down
            fireDnDEvent(32); // space
            expect(await findByLabelText('Drag handle for Full Name')).toHaveFocus();

            expect(
                await findByText(/You have dropped the item\. You have moved the item from position 1 to position 3/)
            ).toBeVisible();

            expect(await axe(container)).toHaveNoViolations();

            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: undefined,
                    basicFilters: [],
                    columnUids: [2002, 2003, 2001],
                }),
            });
        });

        it('starts from default values', async () => {
            const mockApi = vi
                .mocked(useLoaderData)
                .mockReturnValue({ ...MOCK_SELECTABLE_CONFIG, defaultColumnUids: [2003, 2002] });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { findByRole, findByLabelText } = renderWithRouter();

            expect(mockApi).toHaveBeenCalled();

            const user = userEvent.setup();

            // starts selected
            expect(await findByLabelText('Days Old')).toBeChecked();
            expect(await findByLabelText('Date of Birth')).toBeChecked();
            expect(await findByLabelText('Full Name')).not.toBeChecked();

            await user.click(await findByLabelText('Full Name'));
            await user.click(await findByLabelText('Remove Date of Birth'));
            await user.click(await findByLabelText('Date of Birth')); // make sure adds to the end

            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: undefined,
                    basicFilters: [],
                    columnUids: [2003, 2001, 2002],
                }),
            });
        });

        describe('column sorting', () => {
            it('is empty if no columns selected', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue(MOCK_SELECTABLE_CONFIG);
                const { findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Sort by')).not.toHaveValue();
            });

            it('is empty if no columns selected, even with default', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_SELECTABLE_CONFIG,
                    defaultSort: { columnUid: 2003, direction: generated.SortSpec.direction.DESC },
                });
                const { findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Sort by')).not.toHaveValue();
                expect(await findByLabelText('Sort order')).toHaveValue(generated.SortSpec.direction.DESC);
            });

            it('resets default if not in default columns', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_SELECTABLE_CONFIG,
                    defaultColumnUids: [2001, 2002],
                    defaultSort: { columnUid: 2003, direction: generated.SortSpec.direction.DESC },
                });
                const { findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Sort by')).not.toHaveValue();
            });

            it('allows sort selection, but clears if column un-selected', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_SELECTABLE_CONFIG,
                    defaultColumnUids: [2001, 2002],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);
                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Sort by')).not.toHaveValue();

                const user = userEvent.setup();

                await user.selectOptions(await findByLabelText('Sort by'), '2001');

                await user.click(await findByLabelText('Remove Full Name'));

                expect(await findByLabelText('Sort by')).not.toHaveValue();

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [],
                        columnUids: [2002],
                        sort: undefined,
                    }),
                });
            });

            it('allows sort selection', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_SELECTABLE_CONFIG,
                    defaultColumnUids: [2001, 2002],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);
                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Sort by')).not.toHaveValue();

                const user = userEvent.setup();

                await user.selectOptions(await findByLabelText('Sort by'), '2001');
                await user.selectOptions(await findByLabelText('Sort order'), generated.SortSpec.direction.DESC);

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [],
                        columnUids: [2001, 2002],
                        sort: { columnUid: 2001, direction: generated.SortSpec.direction.DESC },
                    }),
                });
            });

            it('allows sort selection, defaults to ascending', async () => {
                const mockApi = vi.mocked(useLoaderData).mockReturnValue({
                    ...MOCK_SELECTABLE_CONFIG,
                    defaultColumnUids: [2001, 2002],
                });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);
                const { findByRole, findByLabelText } = renderWithRouter();

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Sort by')).not.toHaveValue();

                const user = userEvent.setup();

                await user.selectOptions(await findByLabelText('Sort by'), '2001');

                const exportButton = await findByRole('button', { name: 'Export' });
                await user.click(exportButton);

                expect(mockResultApi).toHaveBeenCalledWith({
                    requestBody: expect.objectContaining({
                        isExport: true,
                        advancedFilter: undefined,
                        basicFilters: [],
                        columnUids: [2001, 2002],
                        sort: { columnUid: 2001, direction: generated.SortSpec.direction.ASC },
                    }),
                });
            });
        });
    });
});

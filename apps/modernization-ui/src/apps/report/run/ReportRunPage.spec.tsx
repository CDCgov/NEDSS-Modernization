import { render, waitFor } from '@testing-library/react';
import { ReportRunPage } from './ReportRunPage';
import * as generated from 'generated';
import userEvent from '@testing-library/user-event';
import { BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Layout } from 'layout';
import { createMemoryRouter, RouterProvider } from 'react-router';
import { ReactNode } from 'react';
import fileDownload from 'js-file-download';
import { axe } from 'jest-axe';
import * as options from 'options';
import { ConceptOptions, useConceptOptions } from 'options/concepts';

vi.mock('react-router', async () => {
    const actual = await vi.importActual<typeof import('react-router')>('react-router');
    return {
        ...actual,
        default: actual,
        useParams: vi.fn(() => ({ reportUid: '2', dataSourceUid: '1' })), // Mock useParams to return a default value
    };
});
vi.mock('js-file-download', { spy: true });

vi.mock('generated');
vi.mock('options');
vi.mock('options/concepts/useConceptOptions', () => ({
    useConceptOptions: vi.fn(),
}));

vi.mock('libs/permission', async () => {
    const actual = await vi.importActual<typeof import('libs/permission')>('libs/permission');
    return {
        ...actual,
        Permitted: vi.fn(({ children }: { children: ReactNode }) => <>{children}</>),
    };
});

vi.mock('configuration', () => {
    return {
        useConfiguration: () => ({ ready: true, properties: { entries: { NBS_STATE_CODE: '13' } } }),
    };
});

const MOCK_CONFIG: ReportConfiguration = {
    reportTitle: 'Test Report',
    dataSource: {
        name: 'nbs_ods.data_source',
    },
    reportLibrary: {
        runner: 'python',
        libraryName: 'nbs_sr_99',
        isBuiltin: true,
    },
    reportColumns: [
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

const MOCK_RESULT: generated.ReportResult = {
    content_type: generated.ReportResult.content_type.TABLE,
    header: 'Title',
    content: 'I am the result',
};

const renderWithRouter = () => {
    const routes = [
        {
            path: '/',
            element: <Layout />,
            children: [{ index: true, element: <ReportRunPage /> }],
        },
    ];

    const router = createMemoryRouter(routes, { initialEntries: ['/'] });
    return render(<RouterProvider router={router} />);
};
describe('report run page', () => {
    describe('when given valid params', () => {
        it('renders the config', async () => {
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue(MOCK_CONFIG);
            const { getByRole, findByText } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText(/Config/)).toBeVisible();
        });

        it('run button submits config and opens in new tab', async () => {
            const user = userEvent.setup();
            vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue(MOCK_CONFIG);
            const mockApi = vi.mocked(generated.ReportControllerService.runReport).mockResolvedValue(MOCK_RESULT);
            const windowOpen = vi.spyOn(window, 'open');

            const { findByRole, findByText } = renderWithRouter();

            const runButton = await findByRole('button', { name: 'Run' });
            await user.click(runButton);
            expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: false }) });

            expect(await findByText('Your report has run')).toBeVisible();
            expect(windowOpen).toHaveBeenCalled();
            expect(fileDownload).not.toHaveBeenCalled();
        });

        it('export button submits config and downloads', async () => {
            const user = userEvent.setup();
            vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue(MOCK_CONFIG);
            const mockApi = vi.mocked(generated.ReportControllerService.exportReport).mockResolvedValue(MOCK_RESULT);
            const windowOpen = vi.spyOn(window, 'open');

            const { findByRole, findByText } = renderWithRouter();

            const runButton = await findByRole('button', { name: 'Export' });
            await user.click(runButton);
            expect(mockApi).toHaveBeenCalledWith({ requestBody: expect.objectContaining({ isExport: true }) });

            expect(await findByText('Your report has run')).toBeVisible();
            expect(windowOpen).not.toHaveBeenCalled();
            expect(fileDownload).toHaveBeenCalled();
        });
    });

    describe('basic filters', () => {
        describe('BasicFilter', () => {
            const MOCK_FILTER = {
                reportFilterUid: 1001,
                filterType: {
                    id: 18,
                    codeTable: undefined,
                    descTxt: 'Basic Text Filter',
                    code: 'TXT_01',
                    filterCodeSetName: undefined,
                    filterType: 'BAS_TXT',
                    filterName: 'Basic Text Filter',
                },
                isRequired: false,
            };

            it('renders the column title when available', async () => {
                const mockApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_FILTER, reportColumnUid: 2001 }] });
                const { getByRole, findByText, findByLabelText, queryByText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                expect(await findByText('Basic Text Filter')).toBeVisible();
                expect(queryByText('Advanced Filter')).toBeNull();
            });

            it('renders the filter name when column unavailable', async () => {
                const mockApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_FILTER, reportColumnUid: 2004 }] });
                const { getByRole, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Basic Text Filter')).toBeVisible();
            });

            it('renders the filter name when no column', async () => {
                const mockApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_FILTER }] });
                const { getByRole, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Basic Text Filter')).toBeVisible();
            });

            it('renders the required indicator', async () => {
                const mockApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_FILTER, isRequired: true }] });
                const { getByRole, findByText, findByRole } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByText('Basic Text Filter')).toHaveClass('required');
                expect(await findByRole('textbox')).toBeRequired();
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
                    filterCodeSetName: undefined,
                    filterType: 'BAS_TXT',
                    filterName: 'Basic Text Filter',
                },
                isRequired: true,
                reportColumnUid: 2001,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockConfigApi).toHaveBeenCalled();

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
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

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

                const mockConfigApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValue: ['starter text'] }],
                    });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

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
                        filterCodeSetName: undefined,
                        filterType: filterType,
                        filterName: 'Time Range',
                    },
                    isRequired: true,
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);

                    const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

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
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);

                    const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValue: ['01/01/2024', '01/01/2025'] }],
                        });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);

                    const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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
                    filterCodeSetName: undefined,
                    filterType: 'BAS_TIM_RANGE_LIST',
                    filterName: 'Time Period',
                },
                isRequired: true,
                reportColumnUid: 2001,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockConfigApi).toHaveBeenCalled();

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
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

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

                const mockConfigApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValue: ['2024', '2025'] }],
                    });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

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
                    filterCodeSetName: undefined,
                    filterType: 'BAS_MM_YYYY_RANGE',
                    filterName: 'Month Year Range',
                },
                isRequired: true,
                reportColumnUid: 2001,
            };

            it('goes through happy path', async () => {
                const user = userEvent.setup();

                const mockConfigApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockConfigApi).toHaveBeenCalled();

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
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

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

                const mockConfigApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({
                        ...MOCK_CONFIG,
                        basicFilters: [{ ...MOCK_FILTER, defaultValue: ['01/2024', '01/2025'] }],
                    });
                const mockResultApi = vi
                    .mocked(generated.ReportControllerService.exportReport)
                    .mockResolvedValue(MOCK_RESULT);

                const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

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
                            filterCodeSetName: undefined,
                            filterType: 'BAS_JUR_LIST',
                            filterName: 'State',
                        },
                        isRequired: true,
                        maxValueCount: 1,
                        reportColumnUid: 2001,
                    };
                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

                        expect(mockConfigApi).toHaveBeenCalled();

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
                                basicFilters: [{ reportFilterUid: 1001, values: ['13'] }],
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

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

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({
                                ...MOCK_CONFIG,
                                basicFilters: [{ ...MOCK_FILTER, defaultValue: ['13'] }],
                            });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

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
                                basicFilters: [{ reportFilterUid: 1001, values: ['04'] }],
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
                            filterCodeSetName: undefined,
                            filterType: 'BAS_JUR_LIST',
                            filterName: 'County',
                        },
                        isRequired: true,
                        maxValueCount: -1,
                        reportColumnUid: 2001,
                    };

                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByRole, getByText, findByRole, findByLabelText, container } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Georgia'));
                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Arizona'));

                        expect(await findByRole('button', { name: 'Remove Georgia' })).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Arizona' })).toBeVisible();

                        expect(await axe(container)).toHaveNoViolations();

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: [{ reportFilterUid: 1001, values: ['13', '04'] }],
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

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

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({
                                ...MOCK_CONFIG,
                                basicFilters: [{ ...MOCK_FILTER, defaultValue: ['13'] }],
                            });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockResolvedValue([
                            { value: '13', name: 'Georgia' },
                            { value: '04', name: 'Arizona' },
                        ]);

                        const { getByRole, getByText, findByRole, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Georgia' })).toBeVisible();

                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Arizona'));

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: [{ reportFilterUid: 1001, values: ['13', '04'] }],
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
                        code: 'J_S01_N',
                        filterCodeSetName: undefined,
                        filterType: 'BAS_JUR_LIST',
                        filterName: 'State',
                    },
                    isRequired: true,
                    maxValueCount: 1,
                    defaultValue: ['13'],
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
                            filterCodeSetName: undefined,
                            filterType: 'BAS_JUR_LIST',
                            filterName: 'County',
                        },
                        isRequired: true,
                        maxValueCount: 1,
                        reportColumnUid: 2001,
                    };
                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

                        expect(mockConfigApi).toHaveBeenCalled();

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
                                    { reportFilterUid: 1001, values: ['04001'] },
                                    { reportFilterUid: 1002, values: ['04'] },
                                ]),
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

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

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({
                                ...MOCK_CONFIG,
                                basicFilters: [STATE_FILTER, { ...MOCK_FILTER, defaultValue: ['13001'] }],
                            });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

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
                                    { reportFilterUid: 1001, values: ['13002'] },
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
                            filterCodeSetName: undefined,
                            filterType: 'BAS_JUR_LIST',
                            filterName: 'County',
                        },
                        isRequired: true,
                        maxValueCount: -1,
                        reportColumnUid: 2001,
                    };

                    it('goes through happy path', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByRole, getByText, findByRole, findByLabelText, container } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        let dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Dekalb County'));
                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Clayton County'));

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
                        expect(await findByRole('alert')).toBeVisible(); // county is required

                        dropDown = await findByLabelText('Full Name');
                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Yuma County'));

                        expect(await findByRole('button', { name: 'Remove Yuma County' })).toBeVisible();

                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: expect.arrayContaining([
                                    { reportFilterUid: 1001, values: ['04001'] },
                                    { reportFilterUid: 1002, values: ['04'] },
                                ]),
                            }),
                        });
                    });

                    it('does not submit on required', async () => {
                        const user = userEvent.setup();

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [STATE_FILTER, MOCK_FILTER] });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

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

                        const mockConfigApi = vi
                            .mocked(generated.ReportControllerService.getReportConfiguration)
                            .mockResolvedValue({
                                ...MOCK_CONFIG,
                                basicFilters: [STATE_FILTER, { ...MOCK_FILTER, defaultValue: ['13001'] }],
                            });
                        const mockResultApi = vi
                            .mocked(generated.ReportControllerService.exportReport)
                            .mockResolvedValue(MOCK_RESULT);
                        vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                        const { getByRole, getByText, findByRole, findByLabelText } = renderWithRouter();

                        expect(getByRole('status')).toHaveTextContent('Loading');

                        expect(mockConfigApi).toHaveBeenCalled();

                        await user.click(await findByLabelText('Full Name'));
                        expect(await findByRole('option', { name: 'Georgia' })).toBeVisible();

                        // component refreshes when options populates, so can't do this earlier
                        const dropDown = await findByLabelText('Full Name');
                        expect(dropDown).toBeVisible();
                        expect(await findByRole('button', { name: 'Remove Dekalb County' })).toBeVisible();

                        await userEvent.click(dropDown);
                        await userEvent.click(getByText('Clayton County'));

                        const exportButton = await findByRole('button', { name: 'Export' });
                        await user.click(exportButton);
                        expect(mockResultApi).toHaveBeenCalledWith({
                            requestBody: expect.objectContaining({
                                isExport: true,
                                advancedFilter: undefined,
                                basicFilters: expect.arrayContaining([
                                    { reportFilterUid: 1001, values: ['13001', '13002'] },
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
                        filterCodeSetName: 'PHC_TYPE',
                        filterType: 'BAS_CON_LIST',
                        filterName: 'Diseases (Including NULLS)',
                    },
                    isRequired: true,
                    minValueCount: 1,
                    maxValueCount: 1,
                    defaultValue: [],
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

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
                            basicFilters: [{ reportFilterUid: 1001, values: ['11065'] }],
                        }),
                    });
                });

                it('does not submit on required', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValue: ['11065'] }],
                        });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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
                            basicFilters: [{ reportFilterUid: 1001, values: ['10560'] }],
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
                        filterCodeSetName: 'PHC_TYPE',
                        filterType: 'BAS_CON_LIST',
                        filterName: 'Diseases',
                    },
                    isRequired: true,
                    minValueCount: 1,
                    maxValueCount: -1,
                    defaultValue: [],
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByRole, getByText, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await userEvent.click(dropDown);
                    await userEvent.click(getByText('2019 Novel Coronavirus'));
                    await userEvent.click(dropDown);
                    await userEvent.click(getByText('AIDS'));

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
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValue: ['11065'] }],
                        });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(options.selectableResolver).mockImplementation(mockOptionApiImpl);

                    const { getByRole, getByText, findByRole, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '2019 Novel Coronavirus' })).toBeVisible();

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove 2019 Novel Coronavirus' })).toBeVisible();

                    await userEvent.click(dropDown);
                    await userEvent.click(getByText('AIDS'));

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
                        filterCodeSetName: 'CASE_DIAGNOSIS_STD',
                        filterType: 'BAS_CVG_LIST',
                        filterName: 'STD Case Diagnosis',
                    },
                    isRequired: true,
                    minValueCount: 1,
                    maxValueCount: 1,
                    defaultValue: [],
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByRole, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

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
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValue: ['100'] }],
                        });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByRole, findByRole, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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
                        filterCodeSetName: 'CASE_DIAGNOSIS_STD',
                        filterType: 'BAS_CVG_LIST',
                        filterName: 'STD Case Diagnosis',
                    },
                    isRequired: true,
                    minValueCount: 1,
                    maxValueCount: -1,
                    defaultValue: [],
                    reportColumnUid: 2001,
                };

                it('goes through happy path', async () => {
                    const user = userEvent.setup();

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByRole, getByText, findByRole, findByLabelText, container } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    let dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    await userEvent.click(dropDown);
                    await userEvent.click(getByText('100 - Chancroid'));
                    await userEvent.click(dropDown);
                    await userEvent.click(getByText('200 - Chlamydia'));

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
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [MOCK_FILTER] });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByRole, findByRole, findAllByText, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

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

                    const mockConfigApi = vi
                        .mocked(generated.ReportControllerService.getReportConfiguration)
                        .mockResolvedValue({
                            ...MOCK_CONFIG,
                            basicFilters: [{ ...MOCK_FILTER, defaultValue: ['200'] }],
                        });
                    const mockResultApi = vi
                        .mocked(generated.ReportControllerService.exportReport)
                        .mockResolvedValue(MOCK_RESULT);
                    vi.mocked(useConceptOptions).mockReturnValue(mockOptionApiImpl);

                    const { getByRole, getByText, findByRole, findByLabelText } = renderWithRouter();

                    expect(getByRole('status')).toHaveTextContent('Loading');

                    expect(mockConfigApi).toHaveBeenCalled();

                    await user.click(await findByLabelText('Full Name'));
                    expect(await findByRole('option', { name: '100 - Chancroid' })).toBeVisible();

                    expect(useConceptOptions).toHaveBeenCalledWith('CASE_DIAGNOSIS_STD', { lazy: false });

                    // component refreshes when options populates, so can't do this earlier
                    const dropDown = await findByLabelText('Full Name');
                    expect(dropDown).toBeVisible();
                    expect(await findByRole('button', { name: 'Remove 200 - Chlamydia' })).toBeVisible();

                    await userEvent.click(dropDown);
                    await userEvent.click(getByText('100 - Chancroid'));

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
    });

    describe('advanced filter', () => {
        const MOCK_FILTER: generated.AdvancedFilterConfiguration = {
            reportFilterUid: 1001,
            defaultValue: undefined,
        };

        it('renders the empty filter builder when no default value', async () => {
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, findByText, queryByText, findByRole } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();
            expect(queryByText('Basic Filters')).toBeNull();

            const fieldSelect = await findByRole('combobox', { name: 'Field' });
            expect(fieldSelect).toHaveValue('~');
            const user = userEvent.setup();
            await user.selectOptions(fieldSelect, 'Full Name');
            const opSelect = await findByRole('combobox', { name: 'Operator' });
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'contains');
            const valueBox = await findByRole('textbox', { name: 'Value' });
            expect(valueBox).toHaveValue('');
            await user.type(valueBox, 'hi');

            // currently not working, but should once we put in our own components
            // expect(await axe(container)).toHaveNoViolations();

            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: expect.stringMatching(/[0-9-]+/),
                            combinator: 'and',
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
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, findByText, findByRole } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();

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
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const codedValueGetter = vi.mocked(options.cachedSelectableResolver).mockReturnValue(() =>
                Promise.resolve([
                    { value: '123', name: 'Terrible disease' },
                    { value: '456', name: 'Not so awful disease' },
                ])
            );
            const {
                getByRole,
                queryByText,
                getByText,
                findByText,
                findByLabelText,
                findByRole,
                findAllByRole,
                findByTestId,
            } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();

            const fieldSelect = await findByRole('combobox', { name: 'Field' });
            expect(fieldSelect).toHaveValue('~');
            const user = userEvent.setup();
            await user.selectOptions(fieldSelect, 'Full Name');

            // trigger validation
            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(await findByText('Must select an operator and value')).toBeVisible();

            // generally filled in value
            const opSelect = await findByRole('combobox', { name: 'Operator' });
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'contains');

            expect(await findByText('Value cannot be empty')).toBeVisible();

            const valueBox = await findByRole('textbox', { name: 'Value' });
            expect(valueBox).toHaveValue('');
            await user.type(valueBox, 'hi');

            expect(queryByText('Value cannot be empty')).toBeNull();

            // generally filled in coded list
            await user.selectOptions(fieldSelect, 'Condition Code');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'in');

            expect(await findByText('Value cannot be empty')).toBeVisible();

            await waitFor(() =>
                expect(codedValueGetter).toHaveBeenCalledWith(`report.valueset.d.race_code`, `/nbs/api/options/races`)
            );

            const dropDown = await findByLabelText('Value');
            expect(dropDown).toBeVisible();
            await userEvent.click(dropDown);
            await userEvent.click(getByText('Terrible disease'));

            expect(queryByText('Value cannot be empty')).toBeNull();

            // dates between
            await user.selectOptions(fieldSelect, 'DATE_OF_BIRTH');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'between');

            expect(await findByText('Both low and high values required')).toBeVisible();

            // The date entry will likely need to change once we switch to NBS components
            const dtInputs = (await findByTestId('value-editor')).children;
            await user.type(dtInputs[0], '2022-10-18');

            expect(await findByText('Both low and high values required')).toBeVisible();

            await user.type(dtInputs[1], '2022-10-17');

            expect(await findByText('High value must be greater than or equal to low value')).toBeVisible();

            await user.type(dtInputs[1], '{backspace}9');

            expect(queryByText('High value must be greater than or equal to low value')).toBeNull();

            // numbers between
            await user.selectOptions(fieldSelect, 'DAYS_OLD');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'between');

            expect(await findByText('Both low and high values required')).toBeVisible();

            const numInputs = await findAllByRole('spinbutton');
            await user.type(numInputs[0], '10');

            expect(await findByText('Both low and high values required')).toBeVisible();

            await user.type(numInputs[1], '2');

            expect(await findByText('High value must be greater than or equal to low value')).toBeVisible();

            await user.type(numInputs[1], '0');

            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: expect.stringMatching(/[0-9-]+/),
                            combinator: 'and',
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
            const mockApi = vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
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
                                        value: '2020-01-01', // format should be mm/dd/yyyy when we switch components
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
            });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, findByText, findByRole, findAllByRole, findAllByTitle } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();

            const combinators = await findAllByRole('combobox', { name: 'Combinator' });
            expect(combinators).toHaveLength(2);
            expect(combinators[0]).toHaveValue('or');
            expect(combinators[1]).toHaveValue('and');

            const fields = await findAllByRole('combobox', { name: 'Field' });
            expect(fields).toHaveLength(3);
            expect(fields[0]).toHaveValue('FULL_NAME');
            expect(fields[1]).toHaveValue('DATE_OF_BIRTH');
            expect(fields[2]).toHaveValue('DAYS_OLD');

            const operators = await findAllByRole('combobox', { name: 'Operator' });
            expect(operators).toHaveLength(3);
            expect(operators[0]).toHaveValue('beginswith');
            expect(operators[1]).toHaveValue('>');
            expect(operators[2]).toHaveValue('between');

            const values = await findAllByTitle('Value');
            expect(values).toHaveLength(3);
            expect(values[0]).toHaveValue('prefix');
            expect(values[1]).toHaveValue('2020-01-01');
            const [low, high] = values[2].children;
            expect(low).toHaveValue(10);
            expect(high).toHaveValue(20);

            const user = userEvent.setup();
            await user.type(high, '1');
            expect(high).toHaveValue(201);

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
                                            value: '2020-01-01',
                                        },
                                        {
                                            id: '127-127-127',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,201',
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
    });

    describe('advanced filter', () => {
        const MOCK_FILTER: generated.AdvancedFilterConfiguration = {
            reportFilterUid: 1001,
            defaultValue: undefined,
        };

        it('renders the empty filter builder when no default value', async () => {
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, findByText, queryByText, findByRole } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();
            expect(queryByText('Basic Filters')).toBeNull();

            const fieldSelect = await findByRole('combobox', { name: 'Field' });
            expect(fieldSelect).toHaveValue('~');
            const user = userEvent.setup();
            await user.selectOptions(fieldSelect, 'Full Name');
            const opSelect = await findByRole('combobox', { name: 'Operator' });
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'contains');
            const valueBox = await findByRole('textbox', { name: 'Value' });
            expect(valueBox).toHaveValue('');
            await user.type(valueBox, 'hi');

            // currently not working, but should once we put in our own components
            // expect(await axe(container)).toHaveNoViolations();

            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: expect.stringMatching(/[0-9-]+/),
                            combinator: 'and',
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
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, findByText, findByRole } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();

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
            const mockApi = vi
                .mocked(generated.ReportControllerService.getReportConfiguration)
                .mockResolvedValue({ ...MOCK_CONFIG, advancedFilter: MOCK_FILTER });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, queryByText, findByText, findByRole, findAllByRole, findByTestId } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();

            const fieldSelect = await findByRole('combobox', { name: 'Field' });
            expect(fieldSelect).toHaveValue('~');
            const user = userEvent.setup();
            await user.selectOptions(fieldSelect, 'Full Name');

            // trigger validation
            const exportButton = await findByRole('button', { name: 'Export' });
            await user.click(exportButton);

            expect(await findByText('Must select an operator and value')).toBeVisible();

            // generally filled in
            const opSelect = await findByRole('combobox', { name: 'Operator' });
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'contains');

            expect(await findByText('Value cannot be empty')).toBeVisible();

            const valueBox = await findByRole('textbox', { name: 'Value' });
            expect(valueBox).toHaveValue('');
            await user.type(valueBox, 'hi');

            expect(queryByText('Value cannot be empty')).toBeNull();

            // dates between
            await user.selectOptions(fieldSelect, 'DATE_OF_BIRTH');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'between');

            expect(await findByText('Both low and high values required')).toBeVisible();

            // The date entry will likely need to change once we switch to NBS components
            const dtInputs = (await findByTestId('value-editor')).children;
            await user.type(dtInputs[0], '2022-10-18');

            expect(await findByText('Both low and high values required')).toBeVisible();

            await user.type(dtInputs[1], '2022-10-17');

            expect(await findByText('High value must be greater than or equal to low value')).toBeVisible();

            await user.type(dtInputs[1], '{backspace}9');

            expect(queryByText('High value must be greater than or equal to low value')).toBeNull();

            // numbers between
            await user.selectOptions(fieldSelect, 'DAYS_OLD');
            expect(opSelect).toHaveValue('~');
            await user.selectOptions(opSelect, 'between');

            expect(await findByText('Both low and high values required')).toBeVisible();

            const numInputs = await findAllByRole('spinbutton');
            await user.type(numInputs[0], '10');

            expect(await findByText('Both low and high values required')).toBeVisible();

            await user.type(numInputs[1], '2');

            expect(await findByText('High value must be greater than or equal to low value')).toBeVisible();

            await user.type(numInputs[1], '0');

            await user.click(exportButton);

            expect(mockResultApi).toHaveBeenCalledWith({
                requestBody: expect.objectContaining({
                    isExport: true,
                    advancedFilter: {
                        reportFilterUid: 1001,
                        value: {
                            id: expect.stringMatching(/[0-9-]+/),
                            combinator: 'and',
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
            const mockApi = vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
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
                                        value: '2020-01-01', // format should be mm/dd/yyyy when we switch components
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
            });
            const mockResultApi = vi
                .mocked(generated.ReportControllerService.exportReport)
                .mockResolvedValue(MOCK_RESULT);
            const { getByRole, findByText, findByRole, findAllByRole, findAllByTitle } = renderWithRouter();

            expect(getByRole('status')).toHaveTextContent('Loading');

            expect(mockApi).toHaveBeenCalled();

            expect(await findByText('Advanced Filter')).toBeVisible();

            const combinators = await findAllByRole('combobox', { name: 'Combinator' });
            expect(combinators).toHaveLength(2);
            expect(combinators[0]).toHaveValue('or');
            expect(combinators[1]).toHaveValue('and');

            const fields = await findAllByRole('combobox', { name: 'Field' });
            expect(fields).toHaveLength(3);
            expect(fields[0]).toHaveValue('FULL_NAME');
            expect(fields[1]).toHaveValue('DATE_OF_BIRTH');
            expect(fields[2]).toHaveValue('DAYS_OLD');

            const operators = await findAllByRole('combobox', { name: 'Operator' });
            expect(operators).toHaveLength(3);
            expect(operators[0]).toHaveValue('beginswith');
            expect(operators[1]).toHaveValue('>');
            expect(operators[2]).toHaveValue('between');

            const values = await findAllByTitle('Value');
            expect(values).toHaveLength(3);
            expect(values[0]).toHaveValue('prefix');
            expect(values[1]).toHaveValue('2020-01-01');
            const [low, high] = values[2].children;
            expect(low).toHaveValue(10);
            expect(high).toHaveValue(20);

            const user = userEvent.setup();
            await user.type(high, '1');
            expect(high).toHaveValue(201);

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
                                            value: '2020-01-01',
                                        },
                                        {
                                            id: '127-127-127',
                                            columnId: 2003,
                                            operator: 'BW',
                                            value: '10,201',
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

        describe('keyboard drag and drop', () => {
            it('happy path', async () => {
                const mockApi = vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
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
                                            value: '2020-01-01', // format should be mm/dd/yyyy when we switch components
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
                const { getByRole, findByText, findByRole, findByTestId, findAllByTestId } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByText('Advanced Filter')).toBeVisible();

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
                const mockApi = vi.mocked(generated.ReportControllerService.getReportConfiguration).mockResolvedValue({
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
                                            value: '2020-01-01', // format should be mm/dd/yyyy when we switch components
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
                const { getByRole, findByText, findAllByRole, findByTestId, findAllByTestId } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByText('Advanced Filter')).toBeVisible();

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

                const addRuleBtn = (await findAllByRole('button', { name: '+ Rule' }))[0];
                await user.click(addRuleBtn);
                await waitFor(() =>
                    expect(announcementEl).toHaveTextContent('The group has returned to its starting position')
                );
                expect(addRuleBtn).toHaveFocus();
            });
        });
    });
});

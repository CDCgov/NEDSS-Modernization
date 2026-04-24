import { act, findAllByRole, render } from '@testing-library/react';
import { ReportRunPage } from './ReportRunPage';
import * as generated from 'generated';
import userEvent from '@testing-library/user-event';
import { BasicFilterConfiguration, ReportConfiguration } from 'generated';
import { Layout } from 'layout';
import { createMemoryRouter, RouterProvider } from 'react-router';
import { ReactNode } from 'react';
import fileDownload from 'js-file-download';
import { axe } from 'jest-axe';
import * as options from 'options/selectableResolver';

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
vi.mock('options/selectableResolver');

vi.mock('libs/permission', async () => {
    const actual = await vi.importActual<typeof import('libs/permission')>('libs/permission');
    return {
        ...actual,
        Permitted: vi.fn(({ children }: { children: ReactNode }) => <>{children}</>),
    };
});

const MOCK_CONFIG: ReportConfiguration = {
    runner: 'python',
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
            columnName: 'FULL_NAME',
            columnTitle: 'Full Name',
            columnSourceTypeCode: 'STRING',
            displayable: 'Y',
            filterable: 'Y',
        },
        {
            id: 2002,
            columnName: 'FIRST_NAME',
            // no title
            columnSourceTypeCode: 'STRING',
            displayable: 'Y',
            filterable: 'Y',
        },
        {
            id: 2003,
            // no name
            // no title
            columnSourceTypeCode: 'STRING',
            displayable: 'Y',
            filterable: 'Y',
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
                const { getByRole, findByText, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('Full Name')).toBeVisible();
                expect(await findByText('Basic Text Filter')).toBeVisible();
            });

            it('renders the column name when title unavailable', async () => {
                const mockApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_FILTER, reportColumnUid: 2002 }] });
                const { getByRole, findByText, findByLabelText } = renderWithRouter();

                expect(getByRole('status')).toHaveTextContent('Loading');

                expect(mockApi).toHaveBeenCalled();

                expect(await findByLabelText('FIRST_NAME')).toBeVisible();
                expect(await findByText('Basic Text Filter')).toBeVisible();
            });

            it('renders the filter name when name and title unavailable', async () => {
                const mockApi = vi
                    .mocked(generated.ReportControllerService.getReportConfiguration)
                    .mockResolvedValue({ ...MOCK_CONFIG, basicFilters: [{ ...MOCK_FILTER, reportColumnUid: 2003 }] });
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
                        const stateDropDown = await findByLabelText('FIRST_NAME');
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
                                basicFilters: [
                                    { reportFilterUid: 1001, values: ['04001'] },
                                    { reportFilterUid: 1002, values: ['04'] },
                                ],
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
                                basicFilters: [
                                    { reportFilterUid: 1001, values: ['13002'] },
                                    { reportFilterUid: 1002, values: ['13'] },
                                ],
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
                        const stateDropDown = await findByLabelText('FIRST_NAME');
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
                                basicFilters: [
                                    { reportFilterUid: 1001, values: ['04001'] },
                                    { reportFilterUid: 1002, values: ['04'] },
                                ],
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
                                basicFilters: [
                                    { reportFilterUid: 1001, values: ['13001', '13002'] },
                                    { reportFilterUid: 1002, values: ['13'] },
                                ],
                            }),
                        });
                    });
                });
            });
        });
    });
});

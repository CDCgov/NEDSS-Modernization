import { BrowserRouter } from 'react-router-dom';
import { LabReportResults } from './LabReportResults';
import { render } from '@testing-library/react';
import { LabReport } from 'generated/graphql/schema';
import userEvent from '@testing-library/user-event';

const labReports: [LabReport] = [
    {
        relevance: 0,
        id: '10056325',
        jurisdictionCd: 130006,
        localId: 'OBS10001008GA01',
        addTime: '2023-07-27',
        personParticipations: [
            {
                typeCd: 'PATSBJ',
                firstName: 'John',
                lastName: 'Doe',
                birthTime: '1990-01-01',
                currSexCd: 'M',
                personCd: 'PAT',
                personParentUid: 10000001,
                shortId: 63000
            }
        ],
        organizationParticipations: [
            {
                typeCd: 'AUT',
                name: 'Piedmont Hospital'
            }
        ],
        observations: [
            {
                cdDescTxt: 'Acid-Fast Stain',
                altCd: '11545-1',
                displayName: 'abnormal'
            }
        ],
        associatedInvestigations: [],
        labTestSummaries: []
    }
];

const mockHandlePagination = jest.fn();
const totalResults = 50;

describe('LabReportResults component tests', () => {
    it('should render lab report details', () => {
        const { getByText } = render(
            <BrowserRouter>
                <LabReportResults data={labReports} totalResults={1} handlePagination={() => {}} currentPage={0} />
            </BrowserRouter>
        );
        const timeDiff = Date.now() - new Date(labReports[0].personParticipations![0]?.birthTime).getTime();
        const age = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
        expect(getByText('Doe, John')).toBeInTheDocument();
        expect(getByText('Acid-Fast Stain = abnormal')).toBeInTheDocument();
        expect(getByText('OBS10001008GA01')).toBeInTheDocument();
        expect(getByText('01/01/1990')).toBeInTheDocument();
        expect(getByText(`(${age} years)`)).toBeInTheDocument();
        expect(getByText('63000')).toBeInTheDocument();
        expect(getByText('07/27/2023')).toBeInTheDocument();
        expect(getByText('Piedmont Hospital')).toBeInTheDocument();
        expect(getByText('Lab Report').closest('a')).toHaveAttribute('href', '#');
    });

    it('renders lab report results and handles next button pagination', () => {
        const currentPage = 1;
        const { getByText } = render(
            <BrowserRouter>
                <LabReportResults
                    data={labReports}
                    totalResults={totalResults}
                    handlePagination={mockHandlePagination}
                    currentPage={currentPage}
                />
            </BrowserRouter>
        );

        expect(getByText('Next')).toBeInTheDocument();

        const nextPageButton = getByText('Next');
        userEvent.click(nextPageButton);
        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage + 1);
    });

    it('renders lab report results and handles pagination', () => {
        const currentPage = 1;
        const { getByText } = render(
            <BrowserRouter>
                <LabReportResults
                    data={labReports}
                    totalResults={totalResults}
                    handlePagination={mockHandlePagination}
                    currentPage={currentPage}
                />
            </BrowserRouter>
        );

        const currentPageButton = getByText('1');
        userEvent.click(currentPageButton);
        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage);
    });

    it('renders lab report results and handles next button pagination', () => {
        const currentPage = 2;
        const { getByText } = render(
            <BrowserRouter>
                <LabReportResults
                    data={labReports}
                    totalResults={totalResults}
                    handlePagination={mockHandlePagination}
                    currentPage={currentPage}
                />
            </BrowserRouter>
        );

        expect(getByText('Previous')).toBeInTheDocument();

        const prevPageButton = getByText('Previous');
        userEvent.click(prevPageButton);
        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage - 1);
    });
});

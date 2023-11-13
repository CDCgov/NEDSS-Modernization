import { Pagination } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router';
import { PatientSearchResult } from 'generated/graphql/schema';
import { PatientResult } from 'pages/patient/search';

type SearchItemsProps = {
    data: PatientSearchResult[];
    totalResults: number;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const PatientResults = ({ data, totalResults, handlePagination, currentPage }: SearchItemsProps) => {
    const navigate = useNavigate();
    const redirectPatientProfile = (item: PatientSearchResult) => {
        navigate(`/patient-profile/${item.shortId}`);
    };

    return (
        <div className="margin-x-4">
            <div>
                {data.map((result: PatientSearchResult, index: number) => (
                    <div
                        key={index}
                        className="padding-x-3 padding-top-3 padding-bottom-2 margin-bottom-3 bg-white border border-base-light radius-md">
                        <PatientResult result={result} onSelected={redirectPatientProfile} />
                    </div>
                ))}
            </div>
            {totalResults && data.length > 0 && (
                <Pagination
                    style={{ justifyContent: 'flex-end' }}
                    totalPages={Math.ceil(totalResults / 25)}
                    currentPage={currentPage}
                    pathname={'/advanced-search'}
                    onClickNext={() => handlePagination(currentPage + 1)}
                    onClickPrevious={() => handlePagination(currentPage - 1)}
                    onClickPageNumber={(_, page) => handlePagination(page)}
                />
            )}
        </div>
    );
};

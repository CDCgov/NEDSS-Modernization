import { Pagination } from '@trussworks/react-uswds';
import { Investigation } from 'generated/graphql/schema';
import 'apps/search/advancedSearch/AdvancedSearch.scss';
import { InvestigationSearchResult } from './InvestigationSearchResult';

type InvestigationResultsProps = {
    data: [Investigation];
    totalResults: number;
    handlePagination: (page: number) => void;
    currentPage: number;
};

export const InvestigationResults = ({
    data,
    totalResults,
    handlePagination,
    currentPage
}: InvestigationResultsProps) => {
    const handleNext = (page: number) => {
        handlePagination(page);
    };

    return (
        <div className="margin-x-4">
            <div>
                {data?.map((item: Investigation, index: number) => (
                    <InvestigationSearchResult key={index} investigation={item} />
                ))}
            </div>
            {Boolean(totalResults && data?.length > 0) && (
                <Pagination
                    style={{ justifyContent: 'flex-end' }}
                    totalPages={Math.ceil(totalResults / 25)}
                    currentPage={currentPage}
                    pathname={'/advanced-search'}
                    onClickNext={() => handleNext(currentPage + 1)}
                    onClickPrevious={() => handleNext(currentPage - 1)}
                    onClickPageNumber={(_, page) => handleNext(page)}
                />
            )}
        </div>
    );
};

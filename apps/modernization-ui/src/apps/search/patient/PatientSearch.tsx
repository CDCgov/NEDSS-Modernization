import { useForm } from 'react-hook-form';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { SearchLayout, SearchNavigation } from 'apps/search/layout';
import { useSearch } from 'apps/search';

const PatientSearch = () => {
    //  this will be typed as the Patient Search Criteria
    const { handleSubmit, reset: resetForm } = useForm({ mode: 'onBlur' });

    const { reset: resetSearch, complete, search } = useSearch();

    const handleSearch = () => {
        //  initiate search
        search();

        //  simulate a wait time
        setTimeout(() => complete([], 0), 500);
    };

    const handleClear = () => {
        resetForm();
        resetSearch();
    };

    return (
        <SearchLayout
            navigation={(results) => (
                <SearchNavigation
                    actions={() => (
                        <ButtonActionMenu
                            label="Add new"
                            items={[
                                { label: 'Add new patient', action: () => {} },
                                { label: 'Add new lab report', action: () => {} }
                            ]}
                            disabled={results.total === 0}
                        />
                    )}
                />
            )}
            criteria={() => <div>criteria</div>}
            renderAsList={() => <div>result list</div>}
            renderAsTable={() => <div>result table</div>}
            onSearch={() => {
                handleSearch();
                handleSubmit(handleSearch);
            }}
            onClear={handleClear}
        />
    );
};

export { PatientSearch };

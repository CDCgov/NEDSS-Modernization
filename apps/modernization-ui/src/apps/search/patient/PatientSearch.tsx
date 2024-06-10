import { useForm } from 'react-hook-form';
import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { SearchLayout, SearchNavigation, SearchResults } from 'apps/search/layout';
import { useSearch } from 'apps/search';

const PatientSearch = () => {
    //  this will be typed with the Patient Search Criteria
    const { handleSubmit, reset: resetForm } = useForm({ mode: 'onBlur' });

    const { reset: resetSearch, complete } = useSearch();

    const handleSearch = () => {
        complete([], 0);
    };

    const handleClear = () => {
        resetForm();
        resetSearch();
    };

    return (
        <SearchLayout
            navigation={(results) => (
                <SearchNavigation
                    action={() => (
                        <ButtonActionMenu
                            label="Add new"
                            items={[
                                { label: 'Add new patient', action: () => {} },
                                { label: 'Add new lab report', action: () => {} }
                            ]}
                            disabled={results == null}
                        />
                    )}
                />
            )}
            criteria={() => <div>criteria</div>}
            results={() => <SearchResults renderAsList={() => <div>result list</div>} />}
            onSearch={() => {
                handleSearch();
                handleSubmit(handleSearch);
            }}
            onClear={handleClear}
        />
    );
};

export { PatientSearch };

import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { SearchLayout } from 'apps/search/layout';
import { SearchNavigation } from '../layout/navigation/SearchNavigation';

const PatientSearch = () => {
    return (
        <SearchLayout
            navigation={() => (
                <SearchNavigation
                    action={() => (
                        <ButtonActionMenu
                            label="Add new"
                            items={[
                                { label: 'Add new patient', action: () => {} },
                                { label: 'Add new lab report', action: () => {} }
                            ]}
                            disabled
                        />
                    )}
                />
            )}
            criteria={() => <div>criteria</div>}
            results={() => <div>results</div>}></SearchLayout>
    );
};

export { PatientSearch };

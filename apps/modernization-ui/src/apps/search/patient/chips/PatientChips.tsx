import { PersonFilter } from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import Chip from 'apps/search/advancedSearch/components/chips/Chip';
import { ChipItem, patientChipItems } from './patientChipItems';
import { focusedTarget } from 'utils';

type PatientChipsProps = {
    filter: PersonFilter;
    onChange: (personFilter: PersonFilter) => void;
};
export const PatientChips = ({ filter, onChange }: PatientChipsProps) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => {
                    const chipItems: ChipItem[] = patientChipItems(filter, searchCriteria);
                    return (
                        <div className="display-flex" onClick={() => focusedTarget(chipItems[0].source)} accessKey="c">
                            {chipItems.map((item, index) => (
                                <Chip
                                    key={index}
                                    name={item.name}
                                    value={item.value}
                                    handleClose={() =>
                                        onChange({
                                            ...filter,
                                            [item.source.includes('identification') ? 'identification' : item.source]:
                                                undefined
                                        })
                                    }
                                />
                            ))}
                        </div>
                    );
                }}
            </SearchCriteriaContext.Consumer>
        </>
    );
};

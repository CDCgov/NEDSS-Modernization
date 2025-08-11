import { SortHandler, SortingProvider } from 'libs/sorting';
import { columnSortResolver } from 'design-system/table';
import {
    columns,
    NameDemographicRepeatingBlock,
    NameDemographicRepeatingBlockProps
} from './NameDemographicRepeatingBlock';

const sortResolver = columnSortResolver(columns);

type NameDemographicCardProps = Omit<
    NameDemographicRepeatingBlockProps,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues'
>;

const NameDemographicCard = ({ collapsible = true, data = [], ...remaining }: NameDemographicCardProps) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <NameDemographicRepeatingBlock
                            {...remaining}
                            data={sorted}
                            features={{ sorting }}
                            collapsible={collapsible}
                            editable={false}
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { NameDemographicCard };
export type { NameDemographicCardProps };

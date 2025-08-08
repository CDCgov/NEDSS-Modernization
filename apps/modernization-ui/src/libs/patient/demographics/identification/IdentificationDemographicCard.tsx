import { SortHandler, SortingProvider } from 'libs/sorting';
import { columnSortResolver } from 'design-system/table';
import {
    columns,
    IdentificationDemographicRepeatingBlock,
    IdentificationDemographicRepeatingBlockProps
} from './IdentificationDemographicRepeatingBlock';

const sortResolver = columnSortResolver(columns);

type IdentificationDemographicCardProps = Omit<
    IdentificationDemographicRepeatingBlockProps,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues'
>;

const IdentificationDemographicCard = ({
    collapsible = true,
    data = [],
    ...remaining
}: IdentificationDemographicCardProps) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <IdentificationDemographicRepeatingBlock
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

export { IdentificationDemographicCard };
export type { IdentificationDemographicCardProps };

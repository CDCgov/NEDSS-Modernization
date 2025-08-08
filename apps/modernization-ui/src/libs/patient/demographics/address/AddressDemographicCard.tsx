import { SortHandler, SortingProvider } from 'libs/sorting';
import { columnSortResolver } from 'design-system/table';

import {
    AddressDemographicRepeatingBlock,
    AddressDemographicRepeatingBlockProps,
    columns
} from './AddressDemographicRepeatingBlock';

const sortResolver = columnSortResolver(columns);

type AddressDemographicCardProps = Omit<
    AddressDemographicRepeatingBlockProps,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues'
>;

const AddressDemographicCard = ({ collapsible = true, data = [], ...remaining }: AddressDemographicCardProps) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <AddressDemographicRepeatingBlock
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

export { AddressDemographicCard };
export type { AddressDemographicCardProps };

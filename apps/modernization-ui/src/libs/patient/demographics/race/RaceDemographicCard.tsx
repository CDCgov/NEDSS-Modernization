import { SortHandler, SortingProvider } from 'libs/sorting';
import { columnSortResolver } from 'design-system/table';
import {
    RaceDemographicRepeatingBlock,
    RaceDemographicRepeatingBlockProps,
    columns
} from './RaceDemographicRepeatingBlock';

type RaceDemographicCardProps = { title?: string } & Omit<
    RaceDemographicRepeatingBlockProps,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues'
>;

const sortResolver = columnSortResolver(columns);

const RaceDemographicCard = ({ sizing, collapsible = true, data = [], ...remaining }: RaceDemographicCardProps) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RaceDemographicRepeatingBlock
                            {...remaining}
                            sizing={sizing}
                            data={sorted}
                            features={{ sorting }}
                            collapsible={collapsible}
                            viewable={false}
                            editable={false}
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { RaceDemographicCard };

export type { RaceDemographicCardProps };

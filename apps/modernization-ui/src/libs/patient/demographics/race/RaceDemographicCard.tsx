import { internalizeDate } from 'date';
import { SortHandler, SortingProvider } from 'libs/sorting';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { Column, columnSortResolver } from 'design-system/table';
import { RaceDemographic } from './race';
import { RaceDemographicView } from './RaceDemographicView';

import styles from './race-demographic-card.module.scss';

const columns: Column<RaceDemographic>[] = [
    {
        id: 'race-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'race-category',
        name: 'Race',
        className: styles['category-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.race?.name
    },
    {
        id: 'race-detailed',
        name: 'Detailed race',
        sortable: true,
        value: (v) => v.detailed.map((detail) => detail.name).join(', ')
    }
];

type RaceDemographicCardProps = {
    title?: string;
} & Omit<RepeatingBlockProps<RaceDemographic>, 'columns' | 'viewRenderer' | 'title'>;

const sortResolver = columnSortResolver(columns);

const RaceDemographicCard = ({
    title = 'Race',
    sizing,
    collapsible = true,
    data = [],
    ...remaining
}: RaceDemographicCardProps) => {
    const renderView = (entry: RaceDemographic) => <RaceDemographicView entry={entry} />;

    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RepeatingBlock<RaceDemographic>
                            {...remaining}
                            title={title}
                            sizing={sizing}
                            columns={columns}
                            data={sorted}
                            features={{ sorting }}
                            viewRenderer={renderView}
                            collapsible={collapsible}
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { RaceDemographicCard };

export type { RaceDemographicCardProps };

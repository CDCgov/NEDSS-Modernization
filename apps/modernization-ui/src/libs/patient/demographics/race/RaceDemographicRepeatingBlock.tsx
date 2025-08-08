import { internalizeDate } from 'date';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { Column } from 'design-system/table';
import { RaceDemographic, labels } from './race';
import { RaceDemographicView } from './RaceDemographicView';

import styles from './race-repeating-block.module.scss';

const columns: Column<RaceDemographic>[] = [
    {
        id: 'race-as-of',
        name: labels.asOf,
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'race-category',
        name: labels.race,
        className: styles['category-header'],
        sortable: true,
        value: (v) => v.race?.name
    },
    {
        id: 'race-detailed',
        name: labels.detailed,
        sortable: true,
        value: (v) => v.detailed.map((detail) => detail.name).join(', ')
    }
];

type RaceDemographicRepeatingBlockProps = {
    title?: string;
} & Omit<RepeatingBlockProps<RaceDemographic>, 'columns' | 'viewRenderer' | 'title'>;

const RaceDemographicRepeatingBlock = ({
    title = 'Race',
    sizing,
    collapsible = true,
    data = [],
    ...remaining
}: RaceDemographicRepeatingBlockProps) => {
    const renderView = (entry: RaceDemographic) => <RaceDemographicView entry={entry} />;

    return (
        <RepeatingBlock<RaceDemographic>
            {...remaining}
            title={title}
            sizing={sizing}
            columns={columns}
            data={data}
            viewRenderer={renderView}
            collapsible={collapsible}
        />
    );
};

export { RaceDemographicRepeatingBlock, columns };
export type { RaceDemographicRepeatingBlockProps };

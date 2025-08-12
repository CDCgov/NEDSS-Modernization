import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { NameDemographic, labels } from './names';
import { NameDemographicView } from './NameDemographicView';

import styles from './name-demographic-repeating-block.module.scss';

const columns: Column<NameDemographic>[] = [
    {
        id: 'name-as-of',
        name: labels.asOf,
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    { id: 'name-type', name: 'Type', className: styles['coded-header'], sortable: true, value: (v) => v.type?.name },
    {
        id: 'name-prefix',
        name: labels.asOf,
        className: styles['coded-header'],
        sortable: true,
        value: (v) => v.prefix?.name
    },
    { id: 'name-last', name: labels.last, sortable: true, value: (v) => v.last },
    { id: 'name-first', name: labels.first, sortable: true, value: (v) => v.first },
    { id: 'name-middle', name: labels.middle, sortable: true, value: (v) => v.middle },
    {
        id: 'name-suffix',
        name: labels.suffix,
        className: styles['coded-header'],
        sortable: true,
        value: (v) => v.suffix?.name
    },
    {
        id: 'name-degree',
        name: labels.degree,
        className: styles['coded-header'],
        sortable: true,
        value: (v) => v.degree?.name
    }
];

type NameDemographicRepeatingBlockProps = {
    title?: string;
} & Omit<RepeatingBlockProps<NameDemographic>, 'columns' | 'viewRenderer' | 'title'>;

const NameDemographicRepeatingBlock = ({
    title = 'Name',
    collapsible = true,
    sizing,
    data = [],
    ...remaining
}: NameDemographicRepeatingBlockProps) => {
    const renderView = (value: NameDemographic) => <NameDemographicView entry={value} />;

    return (
        <RepeatingBlock<NameDemographic>
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

export { NameDemographicRepeatingBlock, columns };
export type { NameDemographicRepeatingBlockProps };

import { internalizeDate } from 'date';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { Column } from 'design-system/table';
import { IdentificationDemographic, labels } from './identifications';
import { IdentificationDemographicView } from './IdentificationDemographicView';

import styles from './identifications-demographic-repeating-block.module.scss';

const columns: Column<IdentificationDemographic>[] = [
    {
        id: 'identification-as-of',
        name: labels.asOf,
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'identification-type',
        name: labels.type,
        className: styles.typeWidth,
        sortable: true,
        value: (v) => v.type?.name
    },
    {
        id: 'identification-issuer',
        name: labels.issuer,
        className: styles['text-header'],
        sortable: true,
        value: (v) => v.issuer?.name
    },
    {
        id: 'identification-value',
        name: labels.value,
        className: styles.valueWidth,
        sortable: true,
        value: (v) => v.value
    }
];

type IdentificationDemographicRepeatingBlockProps = {
    title?: string;
} & Omit<RepeatingBlockProps<IdentificationDemographic>, 'columns' | 'viewRenderer' | 'title'>;

const IdentificationDemographicRepeatingBlock = ({
    title = 'Identification',
    sizing,
    collapsible = true,
    data = [],
    ...remaining
}: IdentificationDemographicRepeatingBlockProps) => {
    const renderView = (entry: IdentificationDemographic) => <IdentificationDemographicView entry={entry} />;

    return (
        <RepeatingBlock<IdentificationDemographic>
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

export { IdentificationDemographicRepeatingBlock, columns };
export type { IdentificationDemographicRepeatingBlockProps };

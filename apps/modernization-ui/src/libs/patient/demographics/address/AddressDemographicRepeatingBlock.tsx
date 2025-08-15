import { internalizeDate } from 'date';
import { exists } from 'utils/exists';
import { orNoData } from 'design-system/data';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { Column } from 'design-system/table';
import { AddressDemographic, labels } from './address';
import { AddressDemographicView } from './AddressDemographicView';

import styles from './address-repeating-block.module.scss';

const displayAddress = (value: AddressDemographic) =>
    [orNoData(value.address1), value.address2].filter(exists).join(', ');

const columns: Column<AddressDemographic>[] = [
    {
        id: 'address-as-of',
        name: labels.asOf,
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'address-type',
        name: labels.type,
        className: styles.typeWidth,
        sortable: true,
        value: (v) => `${v.type.name} / ${v.use.name}`
    },
    {
        id: 'address-address',
        name: 'Address',
        sortable: true,
        value: displayAddress
    },
    {
        id: 'address-city',
        name: labels.city,
        className: styles['text-header'],
        sortable: true,
        value: (v) => v.city
    },
    {
        id: 'address-state',
        name: labels.state,
        className: styles['text-header'],
        sortable: true,
        value: (v) => v.state?.name
    },
    {
        id: 'address-zip',
        name: labels.zip,
        className: styles.zipWidth,
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.zipcode
    }
];

type AddressDemographicRepeatingBlockProps = {
    title?: string;
} & Omit<RepeatingBlockProps<AddressDemographic>, 'columns' | 'viewRenderer' | 'title'>;

const AddressDemographicRepeatingBlock = ({
    title = 'Address',
    sizing,
    collapsible = true,
    data = [],
    ...remaining
}: AddressDemographicRepeatingBlockProps) => {
    const renderView = (value: AddressDemographic) => <AddressDemographicView entry={value} />;

    return (
        <RepeatingBlock<AddressDemographic>
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

export { AddressDemographicRepeatingBlock, columns };
export type { AddressDemographicRepeatingBlockProps };

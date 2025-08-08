import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { internalizeDate } from 'date';
import { Column } from 'design-system/table';
import { PhoneEmailDemographic } from './phoneEmails';
import { PhoneEmailDemographicView } from './PhoneEmailDemographicView';

import styles from './phone-email-demographic-repeating-block.module.scss';

const columns: Column<PhoneEmailDemographic>[] = [
    {
        id: 'phone-email-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'phone-email-type',
        name: 'Type',
        className: styles.typeWidth,
        sortable: true,
        value: (v) => `${v.type.name} / ${v.use.name}`
    },
    {
        id: 'phone-email-number',
        name: 'Phone number',
        className: styles.numberWidth,
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.phoneNumber
    },
    {
        id: 'phone-email-email-address',
        name: 'Email address',
        className: styles['text-header'],
        sortable: true,
        value: (v) => v.email
    },
    {
        id: 'phone-email-comments',
        name: 'Comments',
        className: styles['text-header'],
        sortable: true,
        value: (v) => v.comment
    }
];

type PhoneEmailDemographicRepeatingBlockProps = {
    title?: string;
} & Omit<RepeatingBlockProps<PhoneEmailDemographic>, 'columns' | 'viewRenderer' | 'title'>;

const PhoneEmailDemographicRepeatingBlock = ({
    title = 'Phone & email',
    sizing,
    data = [],
    collapsible = true,
    ...remaining
}: PhoneEmailDemographicRepeatingBlockProps) => {
    const renderView = (entry: PhoneEmailDemographic) => <PhoneEmailDemographicView entry={entry} />;

    return (
        <RepeatingBlock<PhoneEmailDemographic>
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

export { PhoneEmailDemographicRepeatingBlock, columns };

export type { PhoneEmailDemographicRepeatingBlockProps };

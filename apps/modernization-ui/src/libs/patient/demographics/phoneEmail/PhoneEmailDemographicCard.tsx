import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { initial, PhoneEmailDemographic } from './phoneEmails';
import { SortHandler, SortingProvider } from 'libs/sorting';
import { Column, columnSortResolver } from 'design-system/table';
import { internalizeDate } from 'date';
import styles from './phone-email-demographic-card.module.scss';
import { PhoneEmailDemographicView } from './PhoneEmailDemographicView';
import { PhoneEmailDemographicFields } from './PhoneEmailDemographicFields';

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
        sortIconType: 'alpha',
        value: (v) => v.type?.name,
        render: (v) => `${v.type.name} / ${v.use.name}`
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
        sortIconType: 'alpha',
        value: (v) => v.email
    },
    {
        id: 'phone-email-comments',
        name: 'Comments',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.comment
    }
];

type PhoneEmailDemographicCardProps = {
    title?: string;
} & Omit<
    RepeatingBlockProps<PhoneEmailDemographic>,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues' | 'title'
>;

const defaultValue = initial();

const sortResolver = columnSortResolver(columns);

const PhoneEmailDemographicCard = ({
    title = 'Phone & email',
    sizing,
    data = [],
    ...remaining
}: PhoneEmailDemographicCardProps) => {
    const renderView = (entry: PhoneEmailDemographic) => <PhoneEmailDemographicView entry={entry} />;
    const renderForm = () => <PhoneEmailDemographicFields />;

    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RepeatingBlock<PhoneEmailDemographic>
                            {...remaining}
                            title={title}
                            sizing={sizing}
                            columns={columns}
                            data={sorted}
                            features={{ sorting }}
                            defaultValues={defaultValue}
                            editable={false}
                            viewRenderer={renderView}
                            formRenderer={renderForm}
                            collapsible
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { PhoneEmailDemographicCard };

export type { PhoneEmailDemographicCardProps };

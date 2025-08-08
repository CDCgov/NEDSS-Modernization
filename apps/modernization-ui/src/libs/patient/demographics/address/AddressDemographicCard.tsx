import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { AddressDemographic, initial } from './address';
import { SortHandler, SortingProvider } from 'libs/sorting';
import { Column, columnSortResolver } from 'design-system/table';
import styles from './address-demographics-card.module.scss';
import { internalizeDate } from 'date';
import { AddressDemographicView } from './AddressDemographicView';
import { AddressDemographicFields } from './AddressDemographicFields';
import { exists } from 'utils/exists';
import { displayNoData } from 'design-system/data';

const maybeRenderAddress = (value: AddressDemographic) =>
    [orNoData(value.address1), value.address2].filter(exists).join(', ');

const orNoData = (value?: string) => (value ? value : displayNoData());

const columns: Column<AddressDemographic>[] = [
    {
        id: 'address-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'address-type',
        name: 'Type',
        className: styles.typeWidth,
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => `${v.type.name} / ${v.use.name}`
    },
    {
        id: 'address-address',
        name: 'Address',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => (v.address1 ? v.address1 : v.address2),
        render: (v) => maybeRenderAddress(v)
    },
    {
        id: 'address-city',
        name: 'City',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.city
    },
    {
        id: 'address-state',
        name: 'State',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.state?.name
    },
    {
        id: 'address-zip',
        name: 'Zip',
        className: styles.zipWidth,
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.zipcode
    }
];

type AddressDemographicCardProps = {
    title?: string;
} & Omit<
    RepeatingBlockProps<AddressDemographic>,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues' | 'title'
>;

const sortResolver = columnSortResolver(columns);

const defaultValue = initial();

const AddressDemographicCard = ({
    title = 'Address',
    sizing,
    collapsible = true,
    data = [],
    ...remaining
}: AddressDemographicCardProps) => {
    const renderForm = () => <AddressDemographicFields sizing={sizing} />;
    const renderView = (value: AddressDemographic) => <AddressDemographicView entry={value} />;

    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RepeatingBlock<AddressDemographic>
                            {...remaining}
                            title={title}
                            sizing={sizing}
                            columns={columns}
                            data={sorted}
                            features={{ sorting }}
                            viewRenderer={renderView}
                            formRenderer={renderForm}
                            defaultValues={defaultValue}
                            collapsible={collapsible}
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { AddressDemographicCard };
export type { AddressDemographicCardProps };

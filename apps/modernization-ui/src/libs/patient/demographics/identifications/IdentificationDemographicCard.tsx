import { Column, columnSortResolver } from 'design-system/table';
import { IdentificationDemographic, initial } from './identifications';
import { internalizeDate } from 'date';
import styles from './identifications-demographic-card.module.scss';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { SortHandler, SortingProvider } from 'libs/sorting';

const defaultValue: Partial<IdentificationDemographic> = initial();

const columns: Column<IdentificationDemographic>[] = [
    {
        id: 'identification-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'default',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'identification-type',
        name: 'Type',
        className: styles['coded-header'],
        sortable: true,
        sortIconType: 'default',
        value: (v) => v.type?.name
    },
    {
        id: 'identification-issuer',
        name: 'Assigning authority',
        className: styles['coded-header'],
        sortable: true,
        sortIconType: 'default',
        value: (v) => v.issuer?.name
    },
    {
        id: 'identification-value',
        name: 'ID value',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'default',
        value: (v) => v.value
    }
];

const sortResolver = columnSortResolver(columns);

type IdentificationDemographicCardProps = {
    title?: string;
} & Omit<
    RepeatingBlockProps<IdentificationDemographic>,
    'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues' | 'title'
>;

const IdentificationDemographicCard = ({
    title = 'Identification',
    sizing,
    data = [],
    ...remaining
}: IdentificationDemographicCardProps) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RepeatingBlock<IdentificationDemographic>
                            {...remaining}
                            title={title}
                            sizing={sizing}
                            columns={columns}
                            data={sorted}
                            features={{ sorting }}
                            defaultValues={defaultValue}
                            editable={false}
                            viewable={false}
                            collapsible
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { IdentificationDemographicCard };

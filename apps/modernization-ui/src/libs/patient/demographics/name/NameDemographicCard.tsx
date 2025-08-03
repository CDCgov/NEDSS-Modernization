import { internalizeDate } from 'date';
import { initial, NameDemographic } from './names';
import { Column, columnSortResolver } from 'design-system/table';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { NameDemographicFields } from './NameDemographicFields';
import { NameDemographicView } from './NameDemographicView';
import { SortHandler, SortingProvider } from 'libs/sorting';

import styles from './name-demographic-card.module.scss';

const defaultValue: Partial<NameDemographic> = initial();

const columns: Column<NameDemographic>[] = [
    {
        id: 'name-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    { id: 'name-type', name: 'Type', className: styles['coded-header'], sortable: true, value: (v) => v.type?.name },
    {
        id: 'name-prefix',
        name: 'Prefix',
        className: styles['coded-header'],
        sortable: true,
        value: (v) => v.prefix?.name
    },
    { id: 'name-last', name: 'Last', sortable: true, value: (v) => v.last },
    { id: 'name-first', name: 'First', sortable: true, value: (v) => v.first },
    { id: 'name-middle', name: 'Middle', sortable: true, value: (v) => v.middle },
    {
        id: 'name-suffix',
        name: 'Suffix',
        className: styles['coded-header'],
        sortable: true,
        value: (v) => v.suffix?.name
    },
    {
        id: 'name-degree',
        name: 'Degree',
        className: styles['coded-header'],
        sortable: true,
        value: (v) => v.degree?.name
    }
];

const sortResolver = columnSortResolver(columns);

type NameDemographicCardProps = {
    title?: string;
} & Omit<RepeatingBlockProps<NameDemographic>, 'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues' | 'title'>;

const NameDemographicCard = ({
    title = 'Name',
    collapsible = true,
    sizing,
    data = [],
    ...remaining
}: NameDemographicCardProps) => {
    const renderForm = () => <NameDemographicFields sizing={sizing} />;
    const renderView = (value: NameDemographic) => <NameDemographicView entry={value} />;

    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RepeatingBlock<NameDemographic>
                            {...remaining}
                            title={title}
                            sizing={sizing}
                            columns={columns}
                            data={sorted}
                            features={{ sorting }}
                            viewRenderer={renderView}
                            defaultValues={defaultValue}
                            formRenderer={renderForm}
                            collapsible={collapsible}
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { NameDemographicCard };
export type { NameDemographicCardProps };

import { Column, columnSortResolver } from 'design-system/table';
import { IdentificationDemographic, initial } from './identifications';
import { internalizeDate } from 'date';
import styles from './identifications-demographic-card.module.scss';
import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { SortHandler, SortingProvider } from 'libs/sorting';
import { IdentificationDemographicView } from './IdentificationDemographicView';
import { IdentificationDemographicFields } from './IdentificationDemographicFields';

const defaultValue: Partial<IdentificationDemographic> = initial();

const columns: Column<IdentificationDemographic>[] = [
    {
        id: 'identification-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'identification-type',
        name: 'Type',
        className: styles.typeWidth,
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.type?.name
    },
    {
        id: 'identification-issuer',
        name: 'Assigning authority',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.issuer?.name
    },
    {
        id: 'identification-value',
        name: 'ID value',
        className: styles.valueWidth,
        sortable: true,
        sortIconType: 'alpha',
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
    const renderView = (entry: IdentificationDemographic) => <IdentificationDemographicView entry={entry} />;
    const renderForm = () => <IdentificationDemographicFields />;

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

export { IdentificationDemographicCard };

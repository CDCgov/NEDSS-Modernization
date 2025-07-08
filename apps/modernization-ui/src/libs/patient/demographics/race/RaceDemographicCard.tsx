import { RepeatingBlock, RepeatingBlockProps } from 'design-system/entry/multi-value';
import { initial, RaceDemographic } from './race';
import { SortHandler, SortingProvider } from 'libs/sorting';
import { Column, columnSortResolver } from 'design-system/table';
import { internalizeDate } from 'date';
import styles from './race-demographic-card.module.scss';
import { RaceDemographicView } from './RaceDemographicView';
import { RaceDemographicFields } from './RaceDemographicFields';

const columns: Column<RaceDemographic>[] = [
    {
        id: 'race-as-of',
        name: 'As of',
        className: styles['date-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.asOf,
        render: (v) => internalizeDate(v.asOf)
    },
    {
        id: 'race-race',
        name: 'Race',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'alpha',
        value: (v) => v.race?.name
    },
    {
        id: 'race-detailed',
        name: 'Detailed race',
        className: styles['text-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (v) => v.detailed[0].name,
        render: (v) => v.detailed.map((detail) => detail.name).join(', ')
    }
];

type RaceDemographicCardProps = {
    title?: string;
} & Omit<RepeatingBlockProps<RaceDemographic>, 'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues' | 'title'>;

const defaultValue = initial();

const sortResolver = columnSortResolver(columns);

const RaceDemographicCard = ({ title = 'Race', sizing, data = [], ...remaining }: RaceDemographicCardProps) => {
    const renderView = (entry: RaceDemographic) => <RaceDemographicView entry={entry} />;
    const renderForm = () => <RaceDemographicFields />;

    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <RepeatingBlock<RaceDemographic>
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
                            viewable={false}
                            collapsible
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { RaceDemographicCard };

export type { RaceDemographicCardProps };

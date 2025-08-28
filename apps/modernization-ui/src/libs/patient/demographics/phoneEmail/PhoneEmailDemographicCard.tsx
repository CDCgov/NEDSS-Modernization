import { columnSortResolver } from 'design-system/table';
import { SortHandler, SortingProvider } from 'libs/sorting';
import {
    PhoneEmailDemographicRepeatingBlock,
    PhoneEmailDemographicRepeatingBlockProps,
    columns
} from './PhoneEmailDemographicRepeatingBlock';

type PhoneEmailDemographicCardProps = {
    title?: string;
} & Omit<PhoneEmailDemographicRepeatingBlockProps, 'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues'>;

const sortResolver = columnSortResolver(columns);

const PhoneEmailDemographicCard = ({ data = [], collapsible = true, ...remaining }: PhoneEmailDemographicCardProps) => {
    return (
        <SortingProvider appendToUrl={false}>
            {(sorting) => (
                <SortHandler sorting={sorting} resolver={sortResolver} data={data}>
                    {({ sorting, sorted }) => (
                        <PhoneEmailDemographicRepeatingBlock
                            {...remaining}
                            data={sorted}
                            features={{ sorting }}
                            collapsible={collapsible}
                            editable={false}
                        />
                    )}
                </SortHandler>
            )}
        </SortingProvider>
    );
};

export { PhoneEmailDemographicCard };

export type { PhoneEmailDemographicCardProps };

import { DateBetweenCriteria } from 'design-system/date/entry';

type DateRangeEntryProps = {
    id: string;
    value: DateBetweenCriteria | undefined;
    onChange: (value: DateBetweenCriteria) => void;
};

export const DateRangeEntry = ({ id }: DateRangeEntryProps) => {
    return <div id={id}>Date range entry</div>;
};

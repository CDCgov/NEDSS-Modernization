import { DateBetweenCriteria } from 'design-system/date/entry';

type DateRangeEntryProps = {
    id: string;
    value: DateBetweenCriteria | undefined;
    onChange: (value: DateBetweenCriteria) => void;
};

export const DateRangeEntry = ({ id }: DateRangeEntryProps) => {
    return (
        <div id={id} aria-label="patient-search-date-range">
            Date range entry
        </div>
    );
};

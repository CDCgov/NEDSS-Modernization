import { DateRangePicker } from '@trussworks/react-uswds';
import { DateBetweenCriteria } from 'design-system/date/entry';
import styles from '../date-criteria.module.scss';

type DateRangeEntryProps = {
    id: string;
    value: DateBetweenCriteria | undefined;
    onChange: (value: DateBetweenCriteria) => void;
};

const interalize = (value?: string | null) => {
    if (value) {
        const [month, day, year] = value.split('/');
        return `${year}-${month}-${day}`;
    } else return undefined;
};

export const DateRangeEntry = ({ id, value, onChange }: DateRangeEntryProps) => {
    const today = new Date().toISOString().split('T')[0];

    const handleOnChange = (field: 'from' | 'to') => (val: string | undefined) => {
        const newValue: DateBetweenCriteria = { between: { ...value?.between, [field]: val } };
        onChange(newValue);
    };

    return (
        <div id={id}>
            <DateRangePicker
                className={styles['date-range-wrapper']}
                startDateLabel="Event start date"
                startDatePickerProps={{
                    id: 'event-date-start',
                    name: 'event-date-start',
                    minDate: '1875-01-01',
                    maxDate: today,
                    onChange: (value) => {
                        handleOnChange('from')(value);
                    },
                    defaultValue: interalize(value?.between?.from)
                }}
                endDateLabel="Event end date"
                endDatePickerProps={{
                    id: 'event-date-end',
                    name: 'event-date-end',
                    disabled: !value?.between?.from,
                    minDate: value?.between?.from,
                    maxDate: today,
                    onChange: (value) => {
                        handleOnChange('to')(value);
                    },
                    defaultValue: interalize(value?.between?.to)
                }}
            />
        </div>
    );
};

import { validateMonth, validateDay, validateYear } from '../validateDateEntry';
import { DateCriteria, DateEqualsCriteria } from './dateCriteria';

const NAME = 'Date of birth';

type AdditionalDateCriteriaErrorsProps = {
    value: DateCriteria | null | undefined;
    error: string | undefined;
};

const AdditionalDateCriteriaErrors = (props: AdditionalDateCriteriaErrorsProps) => {
    const { value, error } = props;
    const isDateEqualsCriteria = (x: DateCriteria): x is DateEqualsCriteria => {
        return Object.keys(x).includes('equals');
    };

    // If no value or error is present, there are no additional errors to display.
    // Date must be in the DateEqualsCriteria format at this time to display additional errrors.
    if (!value || !error || !isDateEqualsCriteria(value)) {
        return undefined;
    }

    const monthValidation = validateMonth(NAME)(value.equals);
    const dayValidation = validateDay(NAME)(value.equals);
    const yearValidation = validateYear(NAME)(value.equals);
    console.log(JSON.stringify({ monthValidation, dayValidation, yearValidation }));

    const errorArr = [monthValidation]
        .concat(dayValidation)
        .concat(yearValidation)
        .filter((v) => typeof v !== 'boolean');

    return <div style={{ color: '#b51d09', fontWeight: 'bold' }}>{errorArr}</div>;
};
export default AdditionalDateCriteriaErrors;

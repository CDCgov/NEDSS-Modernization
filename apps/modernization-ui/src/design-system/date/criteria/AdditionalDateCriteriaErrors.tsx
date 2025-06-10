import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { validateMonth, validateDay, validateYear } from '../validateDateEntry';
import { DateCriteria, DateEqualsCriteria } from './dateCriteria';
import styles from './additional-date-criteria-errors.module.scss';

const NAME = 'Date of birth';

type AdditionalDateCriteriaErrorsProps = {
    value: DateCriteria | null | undefined;
    fieldId: string;
    error: string | undefined;
};

const isDateEqualsCriteria = (x: DateCriteria): x is DateEqualsCriteria => {
    return Object.keys(x).includes('equals');
};

const AdditionalDateCriteriaErrors = (props: AdditionalDateCriteriaErrorsProps) => {
    const { value, fieldId, error } = props;

    // If no value or error is present, there are no additional errors to display.
    // Date must be in the DateEqualsCriteria format at this time to display additional errrors.
    if (!value || !error || !isDateEqualsCriteria(value)) {
        return undefined;
    }

    // Use the same validation functions as the primary.
    const yearValidation = validateYear(NAME)(value.equals);
    const dayValidation = validateDay(NAME)(value.equals);
    const monthValidation = validateMonth(NAME)(value.equals);

    // Combine potential errors into an error array.
    const errorArr = [monthValidation]
        .concat(dayValidation)
        .concat(yearValidation)
        .filter((v) => typeof v !== 'boolean');

    return (
        <div>
            {errorArr.map((v) => (
                <InlineErrorMessage key={v} id={`${fieldId}-error`} className={styles.inlineErrorMessage}>
                    {v}
                </InlineErrorMessage>
            ))}
        </div>
    );
};
export default AdditionalDateCriteriaErrors;

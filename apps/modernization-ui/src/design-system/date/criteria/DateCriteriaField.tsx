import { DateCriteria, isDateBetweenCriteria, isDateEqualsCriteria } from './dateCriteria';
import { ExactDateField } from './exact';
import { DateRangeField } from './range';
import { Radio } from 'design-system/radio';
import { Field, FieldProps } from 'design-system/field';
import { Shown } from 'conditional-render';

import styles from './date-criteria.module.scss';
import AdditionalDateCriteriaErrors from './AdditionalDateCriteriaErrors';

type CriteriaType = 'equals' | 'between';

const resolveCriteriaType = (value: DateCriteria): CriteriaType =>
    isDateBetweenCriteria(value) ? 'between' : 'equals';

const resolveInitialCriteriaType = (value?: DateCriteria | null): CriteriaType =>
    value ? resolveCriteriaType(value) : 'equals';

const asDateEqualsCriteria = (value?: DateCriteria | null) =>
    value && isDateEqualsCriteria(value) ? value : undefined;

const asDateRangeCriteria = (value?: DateCriteria | null) =>
    value && isDateBetweenCriteria(value) ? value : undefined;

type DateCriteriaFieldProps = {
    id: string;
    value?: DateCriteria | null;
    onChange: (value?: DateCriteria) => void;
    onBlur?: () => void;
    clearErrors?: () => void;
    showAdditionalDateCriteriaErrors?: boolean;
} & FieldProps;

const DateCriteriaField = ({
    orientation,
    label,
    id,
    sizing,
    value,
    error,
    onChange,
    onBlur,
    clearErrors,
    showAdditionalDateCriteriaErrors
}: DateCriteriaFieldProps) => {
    const type = resolveInitialCriteriaType(value);

    return (
        <Field
            error={error}
            displayErrorsExternally={true}
            orientation={orientation}
            label={label}
            htmlFor={id}
            sizing={sizing}>
            {showAdditionalDateCriteriaErrors && (
                <AdditionalDateCriteriaErrors fieldId={id} error={error} value={value} />
            )}
            <div className={styles.content}>
                <div className={styles.operators} data-range-operator={type}>
                    <Radio
                        id={'equals'}
                        name="dateOperation"
                        label={'Exact Date'}
                        value={'equals'}
                        onChange={() => {
                            onChange({ equals: {} });
                            clearErrors?.();
                        }}
                        checked={type === 'equals'}
                        sizing={sizing}
                        className={styles.radio}
                    />
                    <Radio
                        id={'between'}
                        name="dateOperation"
                        label={'Date Range'}
                        value={'between'}
                        onChange={() => {
                            onChange({ between: {} });
                            clearErrors?.();
                        }}
                        checked={type === 'between'}
                        sizing={sizing}
                        className={styles.radio}
                    />
                </div>
                <div key={type}>
                    <Shown when={type === 'equals'}>
                        <ExactDateField
                            id={`${id}-exact-date`}
                            value={asDateEqualsCriteria(value)}
                            onChange={onChange}
                            onBlur={onBlur}
                        />
                    </Shown>
                    <Shown when={type === 'between'}>
                        <DateRangeField
                            sizing={sizing}
                            id={`${id}-range-entry`}
                            value={asDateRangeCriteria(value)}
                            onChange={onChange}
                            onBlur={onBlur}
                        />
                    </Shown>
                </div>
            </div>
        </Field>
    );
};

export { DateCriteriaField };
export type { DateCriteriaFieldProps };

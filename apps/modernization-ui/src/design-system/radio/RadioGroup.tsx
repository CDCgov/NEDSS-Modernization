import { EntryWrapper } from 'components/Entry';
import { Selectable } from 'options';
import { Radio } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { FieldProps } from '../field';

type Props = {
    id: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value: Selectable) => void;
    className?: string;
    disabled?: boolean;
} & FieldProps;

const RadioGroup = ({ id, options, value, onChange, className, label, disabled, ...remaining }: Props) => {
    return (
        <EntryWrapper htmlFor={id} label={label} {...remaining}>
            <div id={id} className={classNames(className, 'display-flex')} aria-label={label} role="radiogroup">
                {options.map((option, index) => (
                    <Radio
                        className="margin-right-2 bg-transparent"
                        key={`${id}-${index}`}
                        id={`${id}-${index}`}
                        label={option.label ? option.label : option.name}
                        name={option.name}
                        checked={value?.value === option.value}
                        onChange={() => onChange?.(option)}
                        value={option.value}
                        disabled={disabled}
                    />
                ))}
            </div>
        </EntryWrapper>
    );
};

export { RadioGroup };

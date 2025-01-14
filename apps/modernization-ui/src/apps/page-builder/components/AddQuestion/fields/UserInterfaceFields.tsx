import { ErrorMessage, Label, Textarea } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import { SelectInput } from 'components/FormInputs/SelectInput';
import styles from '../question-form.module.scss';

type SelectOption = { name: string; value: string };
const textDisplayOptions: SelectOption[] = [
    {
        value: '1008',
        name: 'User entered text, number or date'
    },
    {
        value: '1009',
        name: 'Multi-line user-entered text'
    },
    {
        value: '1019',
        name: 'Multi-line Notes with User/Date Stamp'
    },
    {
        value: '1026',
        name: 'Readonly User entered text, number, or date'
    },
    {
        value: '1029',
        name: 'Readonly User text, number, or date no save'
    }
];

const dateOrNumericDisplayOptions: SelectOption[] = [
    {
        value: '1008',
        name: 'User entered text, number or date'
    },
    {
        value: '1026',
        name: 'Readonly User entered text, number, or date'
    },
    {
        value: '1029',
        name: 'Readonly User  text, number, or date no save'
    }
];

const codedDisplayOptions: SelectOption[] = [
    {
        value: '1007',
        name: 'Single-Select (Drop down)'
    },
    {
        value: '1013',
        name: 'Multi-Select (List Box)'
    },
    {
        value: '1024',
        name: 'Single-select save (readonly)'
    },
    {
        value: '1025',
        name: 'Multi-select save (readonly)'
    },
    {
        value: '1027',
        name: 'Single-select no save (readonly)'
    },
    {
        value: '1028',
        name: 'Multi-select no save (readonly)'
    },
    {
        value: '1031',
        name: 'Code Lookup'
    }
];

type Props = {
    published?: boolean;
};
export const UserInterfaceFields = ({ published = false }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const questionType = useWatch({ control: form.control, name: 'questionType', exact: true });

    const getDisplayTypeOptions = (): SelectOption[] => {
        switch (questionType) {
            case 'CODED':
                return codedDisplayOptions;
            case 'DATE':
            case 'NUMERIC':
                return dateOrNumericDisplayOptions;
            case 'TEXT':
                return textDisplayOptions;
            default:
                return [];
        }
    };

    return (
        <>
            <h4>User interface</h4>
            <Controller
                control={form.control}
                name="label"
                rules={{
                    required: { value: true, message: 'Question label is required' },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                    <Input
                        label="Question label"
                        id={name}
                        name={name}
                        htmlFor={name}
                        type="text"
                        defaultValue={value}
                        error={error?.message}
                        onChange={onChange}
                        onBlur={onBlur}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="tooltip"
                rules={{ required: { value: true, message: 'Tooltip is required' }, ...maxLengthRule(2000) }}
                render={({ field: { onChange, name, value, onBlur }, fieldState: { error } }) => (
                    <>
                        <Label className="required" htmlFor={name}>
                            Tooltip
                        </Label>
                        {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        <Textarea
                            onChange={onChange}
                            onBlur={onBlur}
                            defaultValue={value}
                            name={name}
                            id={name}
                            rows={1}
                            className={styles.textaArea}
                            required
                        />
                    </>
                )}
            />
            <Controller
                control={form.control}
                name="displayControl"
                rules={{ required: { value: !published, message: 'Display Type required' } }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Display Type"
                        data-testid="displayType"
                        required={!published}
                        defaultValue={value}
                        onChange={(e) => {
                            onChange(e);
                            onBlur();
                        }}
                        error={error?.message}
                        options={getDisplayTypeOptions()}
                        disabled={published}
                    />
                )}
            />
        </>
    );
};

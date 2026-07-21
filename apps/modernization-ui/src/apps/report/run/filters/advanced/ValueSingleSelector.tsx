import { useId } from 'react';
import { FullOption, ValueSelectorProps } from 'react-querybuilder';
import { SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { SIZING } from 'apps/report/constants';

const ValueSingleSelector = (props: ValueSelectorProps<FullOption>) => {
    const id = useId();
    const title = props.title ?? '';
    const options: FullOption[] = (props.options as FullOption[]) ?? [];

    // adjust the name to the label so it displays the easy-to-read name
    // (e.g. "Investigation ID" instead of "public_health_case_uid" in dropdown)
    const availableOptions: Selectable[] = options.map((opt) => ({
        ...opt,
        name: opt.label,
    }));

    const handleOnChange = (value: Selectable | null) => {
        if (value === null) return;
        props.handleOnChange(value.value ?? '');
    };

    const currentSelection: Selectable | null = availableOptions.find((opt) => opt.value === props.value) || null;

    return (
        <span className={props.className}>
            <SingleSelect
                id={id}
                label={title}
                value={currentSelection}
                onChange={handleOnChange}
                orientation="vertical"
                required
                placeholder=""
                name={title}
                options={availableOptions}
                sizing={SIZING}
            />
        </span>
    );
};

export { ValueSingleSelector };

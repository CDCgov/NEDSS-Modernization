import React, { useId } from 'react';
import { FullField, ValueEditorProps } from 'react-querybuilder';
import { SingleSelect } from '../../../../../design-system/select';
import { Selectable } from '../../../../../options';

const ValueSingleSelector = (props: ValueEditorProps<FullField>) => {
    const id = useId();
    const title = props.title ?? '';
    const options = props.options ?? [];

    // adjust the name to the label so it displays the easy-to-read name
    // (e.g. "Investigation ID" instead of "public_health_case_uid" in dropdown)
    const availableOptions = options.map((opt) => ({
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
                orientation={'vertical'}
                required
                placeholder={''}
                name={title}
                options={availableOptions}
            />
        </span>
    );
};

export { ValueSingleSelector };

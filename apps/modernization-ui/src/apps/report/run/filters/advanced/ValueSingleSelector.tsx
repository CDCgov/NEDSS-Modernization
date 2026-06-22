import React, { useId } from 'react';
import { FullField, ValueEditorProps } from 'react-querybuilder';
import { SingleSelect } from '../../../../../design-system/select';
import { Selectable } from '../../../../../options';

const ValueSingleSelector = (props: ValueEditorProps<FullField>) => {
    const id = useId();
    const title = props.title ?? '';
    const options = props.options ?? [];
    let availableOptions;

    const handleOnChange = (value: Selectable | null) => {
        if (value === null) return;
        props.handleOnChange(value.value ?? '');
    };

    const currentSelection: Selectable | null = options.find((opt) => opt.value === props.value) || null;

    if (props.className === 'rule-operators') {
        const availableOperators = props.fieldData.operators;
        availableOptions = options.filter(
            // retain placeholder and operator options available on field selection
            (opt) => !!availableOperators.find((operator) => operator.name == opt.name || opt.name === '~')
        );
    }

    // adjust the name to the label so it displays the easy-to-read name
    // (e.g. "Investigation ID" instead of "public_health_case_uid" in dropdown)
    availableOptions = options.map((opt) => ({
        ...opt,
        name: opt.label,
    }));

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

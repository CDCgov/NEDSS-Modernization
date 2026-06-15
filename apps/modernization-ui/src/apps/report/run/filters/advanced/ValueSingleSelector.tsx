import React, { useId } from 'react';
import { FullField, ValueEditorProps } from 'react-querybuilder';
import { SingleSelect } from '../../../../../design-system/select';
import { Selectable } from '../../../../../options';

const getLabel = (props) => {
    if (props.className === 'rule-operators') return 'Logic';
    return props.title || '';
};

const getPlaceholder = (props) => {
    if (props.className === 'ruleGroup-combinators') return ''; // use no placeholder
    return undefined; // use default placeholder
};

const ValueSingleSelector = (props: ValueEditorProps<FullField>) => {
    const id = useId();

    const handleOnChange = (value: Selectable | null) => {
        if (value === null) return;
        props.handleOnChange(value.value ?? '');
    };

    let currentSelection: Selectable | null;

    if (typeof props.value === 'string') {
        currentSelection = props.options.find((opt) => opt.value === props.value) || null;
    } else {
        currentSelection = props.value;
    }

    let options;
    switch (props.className) {
        case 'rule-operators':
            options = props.fieldData.operators
                .map((operator) => {
                    return props.options.find((opt) => opt.name === operator.name);
                })
                .filter(Boolean);
            break;
        case 'rule-fields':
            options = props.options.filter((option) => option.name !== '~');
            break;
        default:
            options = props.options;
            break;
    }

    return (
        <span className={props.className}>
            <SingleSelect
                id={id}
                label={getLabel(props)}
                value={currentSelection}
                onChange={handleOnChange}
                orientation={'vertical'}
                required
                placeholder={getPlaceholder(props)}
                name={getLabel(props)}
                useLabel={true}
                options={options}
            />
        </span>
    );
};

export { ValueSingleSelector };

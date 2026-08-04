import { FullField, FullOperator, ValueEditorProps } from 'react-querybuilder';

import { ValueSetMetadata } from './AdvancedFilter';
import { ValueInput } from './ValueInput';
import { ValueSetSelector } from './ValueSetSelector';

const ValueEditorSwitch = (props: ValueEditorProps<ValueSetMetadata & FullField & FullOperator>) => {
    switch (props.type) {
        case 'multiselect':
            return <ValueSetSelector {...props} />;
        default:
            return <ValueInput {...props as any} />;
    }
};

export { ValueEditorSwitch };

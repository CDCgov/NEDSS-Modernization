import { FullField, FullOperator, ValueEditorProps } from 'react-querybuilder';
import { ValueSetSelector } from './ValueSetSelector';
import { ValueSetMetadata } from './AdvancedFilter';
import { ValueInput } from './ValueInput';

const ValueEditorSwitch = (props: ValueEditorProps<ValueSetMetadata & FullField & FullOperator>) => {
    switch (props.type) {
        case 'multiselect':
            return <ValueSetSelector {...props} />;
        default:
            return <ValueInput {...props} />;
    }
};

export { ValueEditorSwitch };

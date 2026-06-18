import { ValueEditor as DefaultValueEditor, FullField, FullOperator, ValueEditorProps } from 'react-querybuilder';
import { ValueSetSelector } from './ValueSetSelector';
import { ValueSetMetadata } from './AdvancedFilter';
import { ValueInput } from './ValueInput.tsx';

const ValueEditor = (props: ValueEditorProps<ValueSetMetadata & FullField & FullOperator>) => {
    switch (props.type) {
        case 'text':
            return <ValueInput {...props} />;
        case 'multiselect':
            return <ValueSetSelector {...props} />;
        default: // we should never reach this default
            return <DefaultValueEditor {...props} />;
    }
};

export { ValueEditor };

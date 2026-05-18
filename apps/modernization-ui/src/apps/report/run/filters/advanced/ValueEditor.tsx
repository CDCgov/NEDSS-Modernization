import { ValueEditor as DefaultValueEditor, FullField, ValueEditorProps } from 'react-querybuilder';
import { ValueSetSelector } from './ValueSetSelector';
import { ValueSetMetadata } from './AdvancedFilter';

const ValueEditor = (props: ValueEditorProps<ValueSetMetadata & FullField>) => {
    switch (props.type) {
        case 'select':
        case 'multiselect':
            return <ValueSetSelector {...props} />;
        default:
            return <DefaultValueEditor {...props} />;
    }
};

export { ValueEditor };

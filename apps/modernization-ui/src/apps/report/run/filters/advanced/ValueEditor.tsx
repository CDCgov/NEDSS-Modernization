import { ValueEditor as DefaultValueEditor, ValueEditorProps } from 'react-querybuilder';
import { ValueSetSelector } from './ValueSetSelector';

const ValueEditor = (props: ValueEditorProps) => {
    switch (props.type) {
        case 'select':
        case 'multiselect':
            return <ValueSetSelector {...props} />;
        default:
            return <DefaultValueEditor {...props} />;
    }
};

export { ValueEditor };

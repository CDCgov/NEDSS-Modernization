import { MultiSelect } from 'design-system/select';
import { Selectable } from 'options/selectable';
import { useConceptOptions } from './useConceptOptions';

type Props = {
    valueSet: string;
    onChange: ((value?: Selectable[]) => void) | undefined;
    name: string;
    value: Selectable[] | undefined;
    label: string;
};

export const ConceptMultiSelect = ({ valueSet, onChange, name, value, label }: Props) => {
    const { options } = useConceptOptions(valueSet, { lazy: false });

    return <MultiSelect label={label} onChange={onChange} name={name} value={value} options={options} id={name} />;
};

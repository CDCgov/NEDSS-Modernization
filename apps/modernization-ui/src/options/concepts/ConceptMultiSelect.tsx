import { MultiSelect, MultiSelectProps } from 'design-system/select';
import { useConceptOptions } from './useConceptOptions';

type Props = {
    valueSet: string;
} & Omit<MultiSelectProps, 'options'>;

export const ConceptMultiSelect = ({ valueSet, ...rest }: Props) => {
    const { options } = useConceptOptions(valueSet, { lazy: false });

    return <MultiSelect options={options} {...rest} />;
};

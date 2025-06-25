import { RepeatingBlockProps } from 'design-system/entry/multi-value';

type AddressDemographicCardProps = {
    title?: string;
} & Omit<RepeatingBlockProps<>, 'columns' | 'formRenderer' | 'viewRenderer' | 'defaultValues' | 'title'>;

const AddressDemographicCard = () => {};

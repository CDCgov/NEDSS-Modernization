import { Sizing, ValueField } from 'design-system/field';

type Props = {
    title: string;
    required?: boolean;
    value?: string | null;
    sizing?: Sizing;
    centered?: boolean;
};

export const ValueView = ({ title, value, sizing, centered }: Props) => (
    <ValueField title={title} sizing={sizing} centered={centered}>
        {value}
    </ValueField>
);

import { ReactNode } from 'react';
import { ValueField, ValueFieldProps } from 'design-system/field';
import { isAllowed, Sensitive } from './sensitive';

type SensitiveValueFieldProps = {
    children?: Sensitive<ReactNode>;
} & Omit<ValueFieldProps, 'children'>;

const SensitiveValueField = ({ children, ...remaining }: SensitiveValueFieldProps) => {
    if (isAllowed(children)) {
        return <ValueField {...remaining}>{children.value}</ValueField>;
    }
};

export { SensitiveValueField };

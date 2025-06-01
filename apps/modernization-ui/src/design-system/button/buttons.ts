import { ReactNode } from 'react';
import { Sizing } from 'design-system/field';
import { Icons } from 'design-system/icon';

type Labeled = {
    children: ReactNode;
    icon?: Icons;
    labelPosition?: 'left' | 'right';
};

type IconOnly = {
    icon: Icons;
    'aria-label': string;
};

type StandardButtonProps = {
    className?: string;
    active?: boolean;
    disabled?: boolean;
    sizing?: Sizing;
    destructive?: boolean;
    secondary?: boolean;
    tertiary?: boolean;
} & (Labeled | IconOnly);

export type { StandardButtonProps, IconOnly, Labeled };

const isLabeled = (props: StandardButtonProps): props is Labeled => 'children' in props;

export { isLabeled };

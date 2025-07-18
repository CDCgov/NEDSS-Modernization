import { ReactNode } from 'react';
import { Sizing } from 'design-system/field';
import { Icons } from 'design-system/icon';
import { exists } from 'utils/exists';

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

const isLabeled = (props: StandardButtonProps): props is Labeled => 'children' in props && exists(props.children);

export { isLabeled };

const withoutStandardButtonProperties = (props: StandardButtonProps) => {
    /* eslint-disable @typescript-eslint/no-unused-vars*/
    const { className, active, sizing, destructive, secondary, tertiary, icon, ...remaining } = props;

    if ('children' in remaining) {
        delete remaining.children;
    }

    if ('labelPosition' in remaining) {
        delete remaining.labelPosition;
    }

    return remaining;
};

export { withoutStandardButtonProperties };

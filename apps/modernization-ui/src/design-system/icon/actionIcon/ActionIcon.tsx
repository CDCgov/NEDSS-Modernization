import React from 'react';
import { Icon, IconProps } from '..';

type ActionIconProps = {
    onAction?: () => void;
} & IconProps;

export const ActionIcon = ({ onAction, ...props }: ActionIconProps) => {
    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.code === 'Enter' || event.code === 'Space') {
            event.stopPropagation();
            event.preventDefault();
            onAction?.();
        }
    };

    return <Icon role="button" onClick={onAction} onKeyDown={handleKeyDown} {...props} />;
};

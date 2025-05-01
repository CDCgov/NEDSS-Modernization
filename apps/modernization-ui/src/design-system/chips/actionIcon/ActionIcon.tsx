import React from 'react';
import { Icon, IconProps } from '../../icon/Icon';

type ActionIconProps = {
    onKeyAction?: () => void;
} & IconProps;

export const ActionIcon = ({ onKeyAction, ...props }: ActionIconProps) => {
    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.code === 'Enter' || event.code === 'Space') {
            event.stopPropagation();
            event.preventDefault();
            onKeyAction?.();
        }
    };

    return <Icon onKeyDown={handleKeyDown} {...props} />;
};

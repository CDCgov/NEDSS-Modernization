import { Meta, StoryObj } from '@storybook/react';
import { Icon } from './Icon';
import { IconsArray } from './types';

const meta = {
    title: 'Design System/Icon',
    component: Icon,
    argTypes: {
        name: { control: 'select', options: IconsArray },
        color: { control: 'color' }
    }
} satisfies Meta<typeof Icon>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        name: 'check'
    }
};

/*

Double check
aria-hidden={props['aria-hidden'] || !props['aria-label'] || !props['aria-labelledby']}

*/

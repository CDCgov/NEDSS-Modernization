import { Meta, StoryObj } from '@storybook/react';
import { SingleSelect } from './SingleSelect';
import { asSelectable, Selectable } from 'options';

const meta = {
    title: 'Design System/Select/Single',
    component: SingleSelect
} satisfies Meta<typeof SingleSelect>;

export default meta;

type Story = StoryObj<typeof meta>;

const options: Selectable[] = [
    asSelectable('Apple'),
    asSelectable('Banana'),
    asSelectable('Mango'),
    asSelectable('Orange'),
    asSelectable('Watermelon')
];

export const Default: Story = {
    args: {
        id: 'single-select',
        label: 'Single Select',
        options: options,
        onChange: () => {}
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        orientation: 'horizontal'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        orientation: 'vertical'
    }
};

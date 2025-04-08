import { Meta, StoryObj } from '@storybook/react';
import { SingleSelect } from './SingleSelect';
import { asSelectable, Selectable } from 'options';

const meta = {
    title: 'Design System/Select/SingleSelect',
    component: SingleSelect
} satisfies Meta<typeof SingleSelect>;

export default meta;

type Story = StoryObj<typeof meta>;

const options: Selectable[] = [
    asSelectable('apple', 'Apple'),
    asSelectable('banana', 'Banana'),
    asSelectable('mango', 'Mango'),
    asSelectable('orange', 'Orange'),
    asSelectable('watermelon', 'Watermelon')
];

export const Default: Story = {
    args: {
        id: 'single-select',
        label: 'Single Select',
        options: options,
        onChange: (selected) => {
            console.log('Selected options:', selected);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'horizontal-single-select',
        name: 'HorizontalSingleSelect',
        label: 'Single Select',
        orientation: 'horizontal'
    }
};

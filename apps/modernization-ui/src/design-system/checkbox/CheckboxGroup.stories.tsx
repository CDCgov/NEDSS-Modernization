import { Meta, StoryObj } from '@storybook/react';
import { CheckboxGroup } from './CheckboxGroup';
import { asSelectable, Selectable } from 'options';

const meta = {
    title: 'Design System/Checkbox/CheckboxGroup',
    component: CheckboxGroup
} satisfies Meta<typeof CheckboxGroup>;

export default meta;

type Story = StoryObj<typeof meta>;

const options: Selectable[] = [
    asSelectable('apple', 'Apple'),
    asSelectable('banana', 'Banana'),
    asSelectable('mango', 'Mango'),
    asSelectable('orange', 'Orange'),
    asSelectable('watermelon', 'Watermelon')
];
const [, banana, mango] = options;

export const Default: Story = {
    args: {
        name: 'Fruits',
        label: 'Fruits',
        options: [...options]
    }
};

export const Compact: Story = {
    args: {
        name: 'Fruits',
        label: 'Fruits',
        options: [...options],
        sizing: 'compact'
    }
};

export const PreselectedOptions: Story = {
    args: {
        name: 'Fruits',
        label: 'Fruits',
        options: [...options],
        value: [banana, mango]
    }
};

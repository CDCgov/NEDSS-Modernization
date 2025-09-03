import { Meta, StoryObj } from '@storybook/react-vite';
import { MultiSelect } from './MultiSelect';
import { asSelectable, Selectable } from 'options';

const meta = {
    title: 'Design System/Select/MultiSelect',
    component: MultiSelect
} satisfies Meta<typeof MultiSelect>;

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
        id: 'multi-select',
        name: 'MultiSelect',
        label: 'Multi Select',
        options: options,
        value: [options[0]],
        onChange: (selected) => {
            console.log('Selected options:', selected);
        }
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        id: 'horizontal-multi-select',
        name: 'HorizontalMultiSelect',
        label: 'Horizontal Multi Select',
        options: options,
        orientation: 'horizontal'
    }
};

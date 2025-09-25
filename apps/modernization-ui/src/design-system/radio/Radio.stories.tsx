import { Meta, StoryObj } from '@storybook/react-vite';
import { Radio } from './Radio';

const meta = {
    title: 'Design System/Radio',
    component: Radio
} as Meta;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        label: 'Default Radio',
        name: 'defaultRadio',
        value: 'default'
    }
};

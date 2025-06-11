import { Meta, StoryObj } from '@storybook/react-vite';
import { Toggle } from './Toggle';

const meta = {
    title: 'Design System/Toggle',
    component: Toggle
} satisfies Meta<typeof Toggle>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        name: 'toggle',
        label: 'Toggle',
        onChange: (checked) => {
            console.log('Toggle changed:', checked);
        }
    }
};

export const Toggled: Story = {
    args: {
        name: 'toggle',
        label: 'Toggle',
        value: true,
        onChange: (checked) => {
            console.log('Toggle changed:', checked);
        }
    }
};

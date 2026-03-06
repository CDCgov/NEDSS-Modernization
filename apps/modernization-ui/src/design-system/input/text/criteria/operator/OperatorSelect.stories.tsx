import { Meta, StoryObj } from '@storybook/react-vite';
import { OperatorSelect } from './OperatorSelect';

const meta = {
    title: 'Design System/Input/OperatorSelect',
    component: OperatorSelect
} satisfies Meta<typeof OperatorSelect>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'operator-select',
        onChange: (selected) => {
            console.log('Selected options:', selected);
        }
    }
};

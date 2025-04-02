import { Meta, StoryObj } from '@storybook/react';
import { OperatorSelect } from './OperatorSelect';

const meta = {
    title: 'Design System/Select/OperatorSelect',
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

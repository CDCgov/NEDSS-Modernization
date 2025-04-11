import { Meta, StoryObj } from '@storybook/react';
import { AlertMessage } from './AlertMessage';

const meta = {
    title: 'Design System/AlertMessage',
    component: AlertMessage,
    tags: ['autodocs']
} satisfies Meta<typeof AlertMessage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        type: 'information',
        title: 'Info status',
        children: 'This is a succinct, helpful message.'
    }
};

export const SuccessMessage: Story = {
    args: {
        ...Default.args,
        type: 'success',
        title: 'Success status'
    }
};

export const WarningMessage: Story = {
    args: {
        ...Default.args,
        type: 'warning',
        title: 'Warning status'
    }
};

export const ErrorMessage: Story = {
    args: {
        ...Default.args,
        type: 'error',
        title: 'Error status'
    }
};

export const InfoSlim: Story = {
    args: {
        ...Default.args,
        type: 'information',
        title: undefined,
        children: "You'll need to change your password by April 25, 2020.",
        slim: true
    }
};

export const SuccessSlim: Story = {
    args: {
        ...Default.args,
        type: 'success',
        title: undefined,
        children: 'You successfully changed your password',
        slim: true
    }
};

export const WarningSlim: Story = {
    args: {
        ...Default.args,
        type: 'warning',
        title: undefined,
        children: "You'll need to change your password in the next 48 hours.",
        slim: true
    }
};

export const ErrorSlim: Story = {
    args: {
        ...Default.args,
        type: 'error',
        title: undefined,
        children: 'Sorry, a password needs more than four characters.',
        slim: true
    }
};

export const InfoIconless: Story = {
    args: {
        ...Default.args,
        type: 'information',
        title: undefined,
        iconless: true
    }
};

export const SuccessIconless: Story = {
    args: {
        ...Default.args,
        type: 'success',
        title: undefined,
        iconless: true
    }
};

export const WarningIconless: Story = {
    args: {
        ...Default.args,
        type: 'warning',
        title: undefined,
        iconless: true
    }
};

export const ErrorIconless: Story = {
    args: {
        ...Default.args,
        type: 'error',
        title: undefined,
        iconless: true
    }
};

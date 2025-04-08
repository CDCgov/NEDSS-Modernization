import { Meta, StoryObj } from '@storybook/react';
import { Message } from './Message';

const messageTypes = ['information', 'success', 'warning', 'error'] as const;

const meta = {
    title: 'Design System/Message',
    component: Message,
    argTypes: {
        type: { control: 'select', options: messageTypes }
    }
} satisfies Meta<typeof Message>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        type: 'information',
        children: <div>This is a message</div>
    }
};

export const ErrorStory: Story = {
    name: 'Error',
    args: {
        type: 'error',
        children: <div>This is an error message</div>
    }
};

export const Information: Story = {
    args: {
        type: 'information',
        children: <div>This is an information message</div>
    }
};

export const Success: Story = {
    args: {
        type: 'success',
        children: <div>This is a success message</div>
    }
};

export const Warning: Story = {
    args: {
        type: 'warning',
        children: <div>This is a warning message</div>
    }
};

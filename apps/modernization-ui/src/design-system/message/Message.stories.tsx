import { Meta, StoryObj } from '@storybook/react-vite';
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
        children: (
            <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu quam libero. Suspendisse quis dui
                eros. Suspendisse porta lacus ligula, quis sollicitudin lectus consequat non. Maecenas iaculis hendrerit
                cursus. In varius lacinia odio non cursus. Fusce quis tellus quis elit sollicitudin placerat. In
                efficitur, massa eget pharetra pretium, ante eros venenatis ipsum, eu finibus dui diam sed massa.{' '}
            </p>
        )
    }
};

export const ErrorStory: Story = {
    name: 'Error',
    args: {
        type: 'error',
        children: 'This is an error message'
    }
};

export const Information: Story = {
    args: {
        type: 'information',
        children: 'This is an information message'
    }
};

export const Success: Story = {
    args: {
        type: 'success',
        children: 'This is a success message'
    }
};

export const Warning: Story = {
    args: {
        type: 'warning',
        children: 'This is a warning message'
    }
};

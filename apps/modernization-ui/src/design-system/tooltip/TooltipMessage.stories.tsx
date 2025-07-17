import { Meta, StoryObj } from '@storybook/react';
import { TooltipMessage, TooltipMessageProps } from './TooltipMessage';

const meta = {
    title: 'Design System/Tooltip/Message',
    component: TooltipMessage,
    decorators: [
        (Story) => (
            <div style={{ margin: '3em', display: 'flex', justifyContent: 'center' }}>
                <Story />
            </div>
        )
    ]
} satisfies Meta<typeof TooltipMessage>;

export default meta;

type Story = StoryObj<typeof meta>;

const renderPositions = (props: TooltipMessageProps) => (
    <div>
        <TooltipMessage position="top" {...props} />
        <TooltipMessage position="right" {...props} />
        <TooltipMessage position="bottom" {...props} />
        <TooltipMessage position="left" {...props} />
    </div>
);

const renderOffsets = (props: TooltipMessageProps) => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '5rem' }}>
        <TooltipMessage offset="left" {...props} />
        <TooltipMessage offset="right" {...props} />
        <TooltipMessage offset="center" {...props} />
    </div>
);

export const Default: Story = {
    args: {
        children: 'Tooltip message'
    },
    render: renderPositions
};

export const Top: Story = {
    args: {
        ...Default.args,
        position: 'top'
    },
    render: renderOffsets
};

export const Right: Story = {
    args: {
        ...Default.args,
        position: 'right'
    }
};

export const Bottom: Story = {
    args: {
        ...Default.args,
        position: 'bottom'
    },
    render: renderOffsets
};

export const Left: Story = {
    args: {
        ...Default.args,
        position: 'left'
    }
};

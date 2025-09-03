import { Meta, StoryObj } from '@storybook/react';
import { TooltipMessage, TooltipMessageProps } from './TooltipMessage';

const meta = {
    title: 'Design System/Tooltip/Message',
    component: TooltipMessage,
    decorators: [
        (Story) => (
            <div style={{ margin: '3rem', display: 'flex', justifyContent: 'center', gap: '5rem' }}>
                <Story />
            </div>
        )
    ]
} satisfies Meta<typeof TooltipMessage>;

export default meta;

type Story = StoryObj<typeof meta>;

const renderPositions = (props: TooltipMessageProps) => {
    const { position } = props;

    return (
        <div
            style={{
                position: 'relative',
                width: '2.5rem',
                height: '2.5rem',
                margin: '0 auto',
                border: '1px transparent'
            }}>
            {(!position || position === 'top') && (
                <div
                    style={{
                        position: 'absolute',
                        top: '0.5rem',
                        left: 0,
                        right: 0,
                        display: 'flex',
                        justifyContent: 'center'
                    }}>
                    <TooltipMessage position="top" {...props} />
                </div>
            )}

            {(!position || position === 'right') && (
                <div
                    style={{
                        position: 'absolute',
                        top: 0,
                        bottom: 0,
                        right: '0.5rem',
                        display: 'flex',
                        alignItems: 'center'
                    }}>
                    <TooltipMessage position="right" {...props} />
                </div>
            )}

            {(!position || position === 'bottom') && (
                <div
                    style={{
                        position: 'absolute',
                        bottom: '0.5rem',
                        left: 0,
                        right: 0,
                        display: 'flex',
                        justifyContent: 'center'
                    }}>
                    <TooltipMessage position="bottom" {...props} />
                </div>
            )}

            {(!position || position === 'left') && (
                <div
                    style={{
                        position: 'absolute',
                        top: 0,
                        bottom: 0,
                        left: '0.5rem',
                        display: 'flex',
                        alignItems: 'center'
                    }}>
                    <TooltipMessage position="left" {...props} />
                </div>
            )}
        </div>
    );
};

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

import { Meta, StoryObj } from '@storybook/react-vite';
import { Button, ButtonProps } from './Button';

const meta = {
    title: 'Design System/Button',
    component: Button
} satisfies Meta<typeof Button>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        children: 'Button'
    }
};

const render = (props: ButtonProps) => {
    const content = 'children' in props ? props.children : 'Button';
    return (
        <div
            style={{
                display: 'flex',
                flexDirection: 'column',
                gap: '1rem',
                padding: '1rem',
                backgroundColor: '#f0f7fd'
            }}>
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center', backgroundColor: '' }}>
                <Button {...props}>{content}</Button>
                <Button {...props} icon="check_circle" labelPosition="right">
                    {content}
                </Button>
                <Button {...props} icon="check_circle" labelPosition="left">
                    {content}
                </Button>
                <Button {...props} icon="check_circle" aria-label={content as string}>
                    {undefined}
                </Button>
            </div>
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
                <Button {...props} active={true}>
                    {content}
                </Button>
                <Button {...props} icon="check_circle" labelPosition="right" active={true}>
                    {content}
                </Button>
                <Button {...props} icon="check_circle" labelPosition="left" active={true}>
                    {content}
                </Button>
                <Button {...props} icon="check_circle" active={true} aria-label={content as string}>
                    {undefined}
                </Button>
            </div>
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
                <Button {...props} disabled={true}>
                    {content}
                </Button>
                <Button {...props} icon="check_circle" labelPosition="right" disabled={true}>
                    {content}
                </Button>
                <Button {...props} icon="check_circle" labelPosition="left" disabled={true}>
                    {content}
                </Button>
                <Button {...props} icon="check_circle" disabled={true} aria-label={content as string}>
                    {undefined}
                </Button>
            </div>
        </div>
    );
};

export const Primary: Story = {
    args: {
        ...Default.args
    },
    render
};

export const PrimarySmall: Story = {
    args: {
        ...Default.args,
        sizing: 'small'
    },
    render
};

export const Secondary: Story = {
    args: {
        ...Default.args,
        secondary: true
    },
    render
};

export const SecondarySmall: Story = {
    args: {
        ...Secondary.args,
        sizing: 'small'
    },
    render
};

export const PrimaryDestructive: Story = {
    args: {
        ...Default.args,
        destructive: true
    },
    render
};

export const PrimaryDestructiveSmall: Story = {
    args: {
        ...PrimaryDestructive.args,
        sizing: 'small'
    },
    render
};

export const SecondaryDestructive: Story = {
    args: {
        ...Default.args,
        secondary: true,
        destructive: true
    },
    render
};

export const SecondaryDestructiveSmall: Story = {
    args: {
        ...SecondaryDestructive.args,
        sizing: 'small'
    },
    render
};

export const Tertiary: Story = {
    args: {
        ...Default.args,
        tertiary: true
    },
    render
};

export const TertiarySmall: Story = {
    args: {
        ...Tertiary.args,
        sizing: 'small'
    },
    render
};

export const TertiaryDestructive: Story = {
    args: {
        ...Tertiary.args,
        destructive: true
    },
    render
};

export const TertiaryDestructiveSmall: Story = {
    args: {
        ...TertiaryDestructive.args,
        sizing: 'small'
    },
    render
};

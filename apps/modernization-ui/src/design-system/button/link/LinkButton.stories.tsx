import { Meta, StoryObj } from '@storybook/react';
import { Icon } from 'design-system/icon';
import { LinkButton, LinkButtonProps } from './LinkButton';

const meta = {
    title: 'Design System/Button/Link',
    component: LinkButton
} satisfies Meta<typeof LinkButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        href: '#',
        children: 'LinkButton'
    }
};

const render = ({ children, ...remaining }: LinkButtonProps) => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', padding: '1rem', backgroundColor: '#f0f7fd' }}>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'center', backgroundColor: '' }}>
            <LinkButton {...remaining}>{children}</LinkButton>
            <LinkButton {...remaining} icon={<Icon name="check_circle" />} labelPosition="left">
                {children}
            </LinkButton>
            <LinkButton {...remaining} icon={<Icon name="check_circle" />} labelPosition="right">
                {children}
            </LinkButton>
            <LinkButton {...remaining} icon={<Icon name="check_circle" />} />
        </div>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
            <LinkButton {...remaining} disabled={true}>
                {children}
            </LinkButton>
            <LinkButton {...remaining} icon={<Icon name="check_circle" />} labelPosition="left" disabled={true}>
                {children}
            </LinkButton>
            <LinkButton {...remaining} icon={<Icon name="check_circle" />} labelPosition="right" disabled={true}>
                {children}
            </LinkButton>
            <LinkButton {...remaining} icon={<Icon name="check_circle" />} disabled={true} />
        </div>
    </div>
);

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

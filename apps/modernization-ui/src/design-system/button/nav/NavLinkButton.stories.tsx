import { Meta, StoryObj } from '@storybook/react';
import { MemoryRouter } from 'react-router';
import { NavLinkButton, NavLinkButtonProps } from './NavLinkButton';

const meta = {
    title: 'Design System/Button/Link',
    component: NavLinkButton
} satisfies Meta<typeof NavLinkButton>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        to: '/',
        children: 'NavLinkButton'
    },
    decorators: [
        (Story) => (
            <MemoryRouter>
                <Story />
            </MemoryRouter>
        )
    ]
};

const render = (remaining: NavLinkButtonProps) => {
    const content = 'children' in remaining ? remaining.children : 'Button';
    return (
        <div
            style={{
                display: 'flex',
                flexDirection: 'column',
                gap: '1rem',
                padding: '1rem',
                backgroundColor: '#f0f7fd'
            }}>
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
                <NavLinkButton {...remaining}>{content}</NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" labelPosition="right">
                    {content}
                </NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" labelPosition="left">
                    {content}
                </NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" aria-label={content as string}>
                    {undefined}
                </NavLinkButton>
            </div>
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
                <NavLinkButton {...remaining} active={true} />
                <NavLinkButton {...remaining} icon="check_circle" labelPosition="right" active={true}>
                    {content}
                </NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" labelPosition="left" active={true}>
                    {content}
                </NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" active={true} aria-label={content as string}>
                    {undefined}
                </NavLinkButton>
            </div>
            <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
                <NavLinkButton {...remaining} disabled={true} />
                <NavLinkButton {...remaining} icon="check_circle" labelPosition="right" disabled={true}>
                    {content}
                </NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" labelPosition="left" disabled={true}>
                    {content}
                </NavLinkButton>
                <NavLinkButton {...remaining} icon="check_circle" disabled={true} aria-label={content as string}>
                    {undefined}
                </NavLinkButton>
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
    ...Default,
    args: {
        ...Default.args,
        sizing: 'small'
    }
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

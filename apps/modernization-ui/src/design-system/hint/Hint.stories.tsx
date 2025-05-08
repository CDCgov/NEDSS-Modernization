import { Meta, StoryObj } from '@storybook/react';
import { Hint } from './Hint';
import { ComponentProps, ReactNode } from 'react';

const meta = {
    title: 'Design System/Hint',
    component: Hint
} satisfies Meta<typeof Hint>;

export default meta;

type Story = StoryObj<typeof meta>;

const Container = ({ children }: { children: ReactNode }) => (
    <div
        style={{
            height: 150,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
        }}>
        {children}
    </div>
);

export const Default: Story = {
    args: {
        id: 'storybook-hint',
        position: 'right',
        marginTop: 0,
        marginLeft: 0,
        children: 'This is some additional information that is available in a tooltip.'
    },
    render: (args: ComponentProps<typeof Hint>) => {
        return (
            <Container>
                <Hint {...args}>{args.children}</Hint>
            </Container>
        );
    }
};

export const CustomTarget: Story = {
    args: {
        ...Default.args,
        target: <span>Hover over me</span>
    },
    render: Default.render
};

export const PositionLeft: Story = {
    args: {
        ...Default.args,
        position: 'left'
    },
    render: Default.render
};

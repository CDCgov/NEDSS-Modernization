import { Meta, StoryObj } from '@storybook/react-vite';
import { Hint, HintProps } from './Hint';

const meta = {
    title: 'Design System/Hint',
    component: Hint,
    decorators: [
        (Story) => (
            <div style={{ margin: '3em', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <Story />
            </div>
        )
    ]
} satisfies Meta<typeof Hint>;

export default meta;

type Story = StoryObj<typeof meta>;

const renderPositions = (props: HintProps) => (
    <>
        <Hint position="right" {...props} />
        <Hint position="left" {...props} />
        <Hint position="center" {...props} />
    </>
);

export const Default: Story = {
    args: {
        id: 'storybook-hint',
        children: 'This is some additional information that is available in a tooltip.'
    },
    render: renderPositions
};

export const CustomTarget: Story = {
    args: {
        ...Default.args,
        target: <span>Hover over me</span>
    }
};

export const Left: Story = {
    args: {
        ...Default.args,
        position: 'left'
    }
};

export const Center: Story = {
    args: {
        ...Default.args,
        position: 'center'
    }
};

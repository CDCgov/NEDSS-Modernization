import { Meta, StoryObj } from '@storybook/react-vite';
import { Checkbox, CheckboxProps } from './Checkbox';

const meta = {
    title: 'Design System/Checkbox',
    component: Checkbox
} satisfies Meta<typeof Checkbox>;

export default meta;

type Story = StoryObj<typeof meta>;

const render = (args: CheckboxProps) => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', padding: '1rem', backgroundColor: '#f0f7fd' }}>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
            <Checkbox sizing="small" {...args} />
            <Checkbox sizing="medium" {...args} />
            <Checkbox sizing="large" {...args} />
        </div>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
            <Checkbox sizing="small" {...args} selected />
            <Checkbox sizing="medium" {...args} selected />
            <Checkbox sizing="large" {...args} selected />
        </div>
    </div>
);

export const Default: Story = {
    args: {
        id: 'checkbox-default',
        label: 'Label'
    },
    render
};

export const Disabled: Story = {
    args: {
        ...Default.args,
        disabled: true
    },
    render
};

export const Unlabeled: Story = {
    args: {
        ...Default.args,
        label: undefined,
        'aria-label': 'Label'
    },
    render
};

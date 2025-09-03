import { Meta, StoryObj } from '@storybook/react-vite';
import { Icon, IconProps } from './Icon';
import { available } from './types';

const meta = {
    title: 'Design System/Icon',
    component: Icon,
    argTypes: {
        name: { control: 'select', options: available },
        color: { control: 'color' }
    }
} satisfies Meta<typeof Icon>;

export default meta;

type Story = StoryObj<typeof meta>;

const sizes = (args: IconProps) => (
    <div style={{ display: 'flex', gap: '1rem', alignItems: 'center' }}>
        <Icon sizing="small" {...args} />
        <Icon sizing="medium" {...args} />
        <Icon sizing="large" {...args} />
    </div>
);

export const Default: Story = {
    args: {
        name: 'check'
    },
    render: sizes
};

//  Double check
//  aria-hidden={props['aria-hidden'] || !props['aria-label'] || !props['aria-labelledby']}

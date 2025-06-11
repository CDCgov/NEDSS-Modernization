import { Meta, StoryObj } from '@storybook/react-vite';
import { DatePickerInput } from './DatePickerInput';

const meta = {
    title: 'Design System/Date/Picker/Field',
    component: DatePickerInput
} satisfies Meta<typeof DatePickerInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'date-picker-field',
        label: 'Date Picker Field',
        onChange: () => {}
    }
};

export const Horizontal: Story = {
    args: {
        ...Default.args,
        orientation: 'horizontal'
    }
};

export const HorizontalSmall: Story = {
    args: {
        ...Horizontal.args,
        sizing: 'small'
    }
};

export const HorizontalMedium: Story = {
    args: {
        ...Horizontal.args,
        sizing: 'medium'
    }
};

export const HorizontalLarge: Story = {
    args: {
        ...Horizontal.args,
        sizing: 'large'
    }
};

export const Vertical: Story = {
    args: {
        ...Default.args,
        orientation: 'vertical'
    }
};

export const VerticalSmall: Story = {
    args: {
        ...Vertical.args,
        sizing: 'small'
    }
};

export const VerticalMedium: Story = {
    args: {
        ...Vertical.args,
        sizing: 'medium'
    }
};

export const VerticalLarge: Story = {
    args: {
        ...Vertical.args,
        sizing: 'large'
    }
};

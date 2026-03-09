import { Meta, StoryObj } from '@storybook/react-vite';
import { ClosablePanel } from './ClosablePanel';

const meta = {
    title: 'Design System/Panel/ClosablePanel',
    component: ClosablePanel,
    argTypes: {
        headingLevel: {
            control: {
                type: 'select',
                options: [2, 3, 4, 5]
            }
        }
    }
} satisfies Meta<typeof ClosablePanel>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        title: 'Panel Title',
        children: <div>This is a panel message</div>,
        onClose: () => {
            console.log('Panel closed');
        }
    }
};

export const WithHeading: Story = {
    args: {
        title: 'Panel Title',
        headingLevel: 2,
        children: <div>This is a panel message</div>,
        onClose: () => {
            console.log('Panel closed');
        }
    }
};

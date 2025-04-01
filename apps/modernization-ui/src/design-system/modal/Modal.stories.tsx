import { Meta, StoryObj } from '@storybook/react';
import { Modal } from './Modal';
import { Confirmation } from './Confirmation';
import { Success } from './Success';
import { Warning } from './Warning';

const meta = {
    title: 'Design System/Modal',
    component: Modal,
    subcomponents: { Confirmation, Success, Warning }
} satisfies Meta<typeof Modal>;

export default meta;

type Story = StoryObj<typeof meta>;

const onConfirm = () => {
    console.log('Confirmed');
};
const onCancel = () => {
    console.log('Cancelled');
};

export const Default: Story = {
    args: {
        id: 'modal',
        title: 'Modal Title',
        children: <div>This is a modal message</div>,
        footer: (close) => (
            <button type="button" className="usa-button usa-button--outline" onClick={close} data-close-modal>
                Close
            </button>
        ),
        onClose: () => {
            console.log('Modal closed');
        }
    }
};

export const ConfirmationStory: Story = {
    name: 'Confirmation',
    args: {
        id: 'confirmation-modal',
        title: 'Confirmation',
        children: <div>This is a confirmation message</div>,
        forceAction: true,
        onClose: onCancel
    },
    render: (args) => <Confirmation {...args} onConfirm={onConfirm} onCancel={onCancel} />
};

export const SuccessStory: Story = {
    name: 'Success',
    args: {
        id: 'success-modal',
        title: 'Success',
        children: <div>This is a success message</div>,
        forceAction: true,
        onClose: onCancel
    },
    render: (args) => <Success {...args} />
};

export const WarningStory: Story = {
    name: 'Warning',
    args: {
        id: 'warning-modal',
        title: 'Warning',
        children: <div>This is a warning message</div>,
        forceAction: true,
        onClose: onCancel
    },
    render: (args) => <Warning {...args} />
};

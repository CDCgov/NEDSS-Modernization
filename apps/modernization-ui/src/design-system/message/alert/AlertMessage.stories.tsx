import { useState } from 'react';
import { Meta, StoryObj } from '@storybook/react';
import { AlertMessage } from './AlertMessage';

const meta = {
    title: 'Design System/AlertMessage',
    component: AlertMessage,
    tags: ['autodocs']
} satisfies Meta<typeof AlertMessage>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        type: 'information',
        title: 'Info status',
        children: 'This is a succinct, helpful message.'
    }
};

const InfoMessage: Story = {
    args: {
        ...Default.args,
        type: 'information',
        title: 'Info status'
    }
};

const SuccessMessage: Story = {
    args: {
        ...Default.args,
        type: 'success',
        title: 'Success status'
    }
};

const WarningMessage: Story = {
    args: {
        ...Default.args,
        type: 'warning',
        title: 'Warning status'
    }
};

const ErrorMessage: Story = {
    args: {
        ...Default.args,
        type: 'error',
        title: 'Error status'
    }
};

const InfoSlim: Story = {
    args: {
        ...Default.args,
        type: 'information',
        title: undefined,
        children: "You'll need to change your password by April 25, 2020.",
        slim: true
    }
};

const SuccessSlim: Story = {
    args: {
        ...Default.args,
        type: 'success',
        title: undefined,
        children: 'You successfully changed your password',
        slim: true
    }
};

const WarningSlim: Story = {
    args: {
        ...Default.args,
        type: 'warning',
        title: undefined,
        children: "You'll need to change your password in the next 48 hours.",
        slim: true
    }
};

const ErrorSlim: Story = {
    args: {
        ...Default.args,
        type: 'error',
        title: undefined,
        children: 'Sorry, a password needs more than four characters.',
        slim: true
    }
};

const InfoIconless: Story = {
    args: {
        ...Default.args,
        type: 'information',
        title: undefined,
        iconless: true
    }
};

const SuccessIconless: Story = {
    args: {
        ...Default.args,
        type: 'success',
        title: undefined,
        iconless: true
    }
};

const WarningIconless: Story = {
    args: {
        ...Default.args,
        type: 'warning',
        title: undefined,
        iconless: true
    }
};

const ErrorIconless: Story = {
    args: {
        ...Default.args,
        type: 'error',
        title: undefined,
        iconless: true
    }
};
const standard = [InfoMessage, SuccessMessage, WarningMessage, ErrorMessage];
export const Standard = () => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
        {standard.map((s, k) => (
            <AlertMessage {...s.args} key={`standard-${k}`} />
        ))}
    </div>
);

const slim = [InfoSlim, SuccessSlim, WarningSlim, ErrorSlim];
export const Slim = () => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
        {slim.map((s, k) => (
            <AlertMessage {...s.args} key={`slim-${k}`} />
        ))}
    </div>
);

const iconless = [InfoIconless, SuccessIconless, WarningIconless, ErrorIconless];
export const Iconless = () => (
    <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
        {iconless.map((s, k) => (
            <AlertMessage {...s.args} key={`iconless-${k}`} />
        ))}
    </div>
);

export const Closable = () => {
    const [show, setShow] = useState(true);

    if (!show) return null;

    return (
        <AlertMessage type="information" slim onClose={() => setShow(false)}>
            You can dismiss this message.
        </AlertMessage>
    );
};

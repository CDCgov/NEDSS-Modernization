import { Meta } from '@storybook/react';
import { Hint } from './Hint';
import { ComponentProps } from 'react';

const meta = {
    title: 'Design System/Hint',
    args: {
        id: 'storybook-hint',
        position: 'right',
        marginTop: 0,
        marginLeft: 0,
        children: 'This is some additional information that is available in a tooltip.'
    },
    component: Hint
} satisfies Meta<typeof Hint>;

export default meta;

export const Default = (args: ComponentProps<typeof Hint>) => {
    return (
        <div
            style={{
                height: 150,
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center'
            }}>
            <Hint {...args}>{args.children}</Hint>
        </div>
    );
};

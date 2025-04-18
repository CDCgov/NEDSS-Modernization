import { Meta } from '@storybook/react';
import { Hint } from './Hint';

const meta = {
    title: 'Design System/Hint',
    args: {
        position: 'right',
        marginTop: 0,
        marginLeft: 0,
        children: 'This is some additional information that is available in a tooltip.'
    },
    component: Hint
} satisfies Meta<typeof Hint>;

export default meta;

export const Default = (args: any) => {
    return (
        <div
            style={{
                height: 100
            }}>
            <Hint {...args}>{args.children}</Hint>
        </div>
    );
};

import { Meta, StoryObj } from '@storybook/react';
import RichTooltip from './RichTooltip';
import { createRef, useRef } from 'react';
import { Icon } from 'design-system/icon';

const meta = {
    title: 'Design System/RichTooltip',
    component: RichTooltip
} satisfies Meta<typeof RichTooltip>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        anchorRef: createRef(),
        marginTop: 0,
        marginLeft: 1200,
        children: (
            <span>
                This is some informational text for the <strong>tooltip</strong>
            </span>
        )
    },
    render: (args) => {
        const divRef = useRef(null);
        return (
            <>
                <div ref={divRef} style={{ position: 'relative' }}>
                    <span>
                        <Icon name="info_outline" sizing="large" />
                    </span>
                </div>
                <RichTooltip {...args} anchorRef={divRef}>
                    {args.children}
                </RichTooltip>
            </>
        );
    }
};

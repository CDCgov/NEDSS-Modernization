import { Meta, StoryObj } from '@storybook/react-vite';
import { Card } from './Card';
import { Button } from 'design-system/button';

const meta = {
    title: 'Design System/Cards/Card',
    component: Card
} satisfies Meta<typeof Card>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
    args: {
        id: 'card',
        title: 'Header text',
        level: 2,
        children: (
            <div style={{ padding: '1rem' }}>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris id blandit leo. Fusce cursus, lacus
                    eu elementum luctus, nisl libero ullamcorper arcu, sit amet congue eros arcu sit amet sem. Praesent
                    vestibulum efficitur sodales. Nam sit amet nunc quis nisi hendrerit congue. Donec vitae dui quis
                    dolor accumsan fermentum. Phasellus imperdiet quis augue in sagittis. Morbi tempor non risus nec
                    varius. Sed non quam sit amet metus lacinia accumsan in dapibus enim. Sed elementum sem at sem
                    aliquet, eu ultrices mi semper. Phasellus mi quam, mollis non interdum ac, porta et eros. Nunc lorem
                    mi, suscipit at eleifend interdum, vestibulum quis libero. Nunc gravida euismod ex. Mauris aliquet
                    sapien vel nibh fermentum, non vulputate libero vehicula. Nullam pretium pharetra ex, quis aliquam
                    enim auctor quis. Suspendisse volutpat, nisl maximus fermentum blandit, lacus arcu hendrerit leo,
                    sit amet ultrices eros mauris a ipsum.
                </p>
                <p>
                    Fusce viverra pulvinar gravida. Phasellus ipsum nibh, mollis in feugiat eget, commodo vel nunc.
                    Aenean elementum felis vel vulputate tincidunt. Curabitur hendrerit diam et fermentum condimentum.
                    Phasellus non urna auctor, finibus sem ac, blandit nulla. Nunc blandit iaculis lorem ut elementum.
                    Phasellus ac fringilla lacus, eu maximus ligula. Sed dignissim risus ut molestie luctus. Nulla
                    facilisi. Aliquam a purus vulputate, pretium augue vitae, ultrices purus.
                </p>
            </div>
        )
    }
};

export const Subtext: Story = {
    args: {
        ...Default.args,
        subtext: 'subtext'
    }
};

export const Info: Story = {
    args: {
        ...Default.args,
        info: 'Info'
    }
};

export const Collapsible: Story = {
    args: {
        ...Default.args,
        subtext: 'subtext',
        collapsible: true
    }
};

export const CollapsibleWithActions: Story = {
    args: {
        ...Collapsible.args,
        actions: (
            <>
                <Button tertiary sizing="small">
                    Tertiary
                </Button>
                <Button secondary sizing="small">
                    Secondary
                </Button>
                <Button sizing="small">Primary</Button>
            </>
        )
    }
};

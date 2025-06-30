import { Meta, StoryObj } from '@storybook/react';
import { LoadingOverlay } from './LoadingOverlay';

const meta = {
    title: 'Loading/Overlay',
    component: LoadingOverlay
} satisfies Meta<typeof LoadingOverlay>;

export default meta;

type Story = StoryObj<typeof meta>;

const Lorem = () => (
    <div style={{ padding: '1rem' }}>
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Varius mollis dapibus platea phasellus tempor dapibus tincidunt hendrerit vivamus
            condimentum dapibus placerat nec. Mauris porta cursus nisi ligula posuere convallis. A conubia nunc suscipit
            porta lacinia aliquam elementum. Magna curae elementum integer accumsan et turpis rutrum gravida nulla
            pharetra fames ipsum.
        </p>
        <p>
            Ornare erat suscipit amet orci in. Himenaeos lacus feugiat suspendisse hac class eget mattis ultricies
            dictumst himenaeos laoreet fusce nulla himenaeos habitant. Maecenas sociosqu adipiscing odio bibendum
            habitant eros ultrices a posuere augue potenti rhoncus.
        </p>
        <p>
            Luctus habitant suspendisse faucibus aliquet id felis. Erat ligula fames sit habitasse etiam hac dolor
            habitant habitant cursus. Dictum ipsum dui vulputate ultrices pellentesque dictumst facilisis hendrerit.
            Nunc habitant vivamus primis nibh lobortis posuere molestie.
        </p>
    </div>
);

export const Default: Story = {
    args: {
        children: <Lorem />
    }
};

import { MediaBlockBody } from '../components/MediaBlockBody/MediaBlockBody';
import { ComponentStory, ComponentMeta } from '@storybook/react';

export default {
    title: 'Components/MediaBlockBody',
    component: MediaBlockBody
} as ComponentMeta<typeof MediaBlockBody>;

const Template: ComponentStory<typeof MediaBlockBody> = (args) => (
    <MediaBlockBody {...args}>
        <p>Sample content</p>
    </MediaBlockBody>
);

export const SampleMediaBlockBody = Template.bind({});
SampleMediaBlockBody.args = {
    className: 'custom-class'
};

import { ComponentStory, ComponentMeta } from '@storybook/react';
import { Banner } from '../components/TopBanner/Banner/banner';

export default {
    title: 'Components/TopBanner/Banner',
    component: Banner
} as ComponentMeta<typeof Banner>;

const Template: ComponentStory<typeof Banner> = (args) => <Banner {...args}>Sample Banner</Banner>;

export const SampleBanner = Template.bind({});
SampleBanner.args = {
    className: 'custom-class'
};

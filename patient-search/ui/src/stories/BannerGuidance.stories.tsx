import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerGuidance } from '../components/TopBanner/BannerGuidance/BannerGuidance';

export default {
    title: 'Components/TopBanner/BannerGuidance',
    component: BannerGuidance
} as ComponentMeta<typeof BannerGuidance>;

const Template: ComponentStory<typeof BannerGuidance> = (args) => <BannerGuidance {...args}>Sample Banner Guidance</BannerGuidance>;

export const SampleBannerGuidance = Template.bind({});
SampleBannerGuidance.args = {
    className: 'custom-class'
};

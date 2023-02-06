import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerContent } from '../components/TopBanner/BannerContent/BannerContent';

export default {
    title: 'Components/TopBanner/BannerContent',
    component: BannerContent
} as ComponentMeta<typeof BannerContent>;

const Template: ComponentStory<typeof BannerContent> = (args) => <BannerContent {...args}>Official websites use .gov</BannerContent>;

export const SampleBannerContent = Template.bind({});
SampleBannerContent.args = {
    className: 'custom-class',
    isOpen: true
};

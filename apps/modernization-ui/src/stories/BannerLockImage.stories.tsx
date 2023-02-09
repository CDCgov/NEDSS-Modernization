import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerLockImage } from '../components/TopBanner/BannerLockImage/BannerLockImage';

export default {
    title: 'Components/TopBanner/BannerLockImage',
    component: BannerLockImage
} as ComponentMeta<typeof BannerLockImage>;

const Template: ComponentStory<typeof BannerLockImage> = (args) => <BannerLockImage {...args}></BannerLockImage>;

export const SampleBannerLockImage = Template.bind({});
SampleBannerLockImage.args = {
    title: 'Lock',
    description: 'A locked padlock'
};

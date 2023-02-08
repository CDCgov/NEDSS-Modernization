import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerFlag } from '../components/TopBanner/BannerFlag/BannerFlag';

export default {
    title: 'Components/TopBanner/BannerFlag',
    component: BannerFlag
} as ComponentMeta<typeof BannerFlag>;

const Template: ComponentStory<typeof BannerFlag> = (args) => <BannerFlag {...args}></BannerFlag>;

export const SampleBannerFlag = Template.bind({});
SampleBannerFlag.args = {
    className: 'custom-class',
    alt: 'img',
    src: '/us_flag_small.png'
};

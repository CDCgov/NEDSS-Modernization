import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerIcon } from '../components/TopBanner/BannerIcon/BannerIcon';

export default {
    title: 'Components/TopBanner/BannerIcon',
    component: BannerIcon
} as ComponentMeta<typeof BannerIcon>;

const Template: ComponentStory<typeof BannerIcon> = (args) => <BannerIcon {...args}></BannerIcon>;

export const SampleBannerIcon = Template.bind({});
SampleBannerIcon.args = {
    className: 'custom-class',
    alt: 'img',
    src: '/icons/icon-dot-gov.svg'
};

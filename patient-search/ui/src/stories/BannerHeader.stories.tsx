import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerHeader } from '../components/TopBanner/BannerHeader/BannerHeader';

export default {
    title: 'Components/TopBanner/BannerHeader',
    component: BannerHeader
} as ComponentMeta<typeof BannerHeader>;

const Template: ComponentStory<typeof BannerHeader> = (args) => <BannerHeader {...args}></BannerHeader>;

export const SampleBannerHeader = Template.bind({});
SampleBannerHeader.args = {
    isOpen: true,
    flagImg: <img src= '/us_flag_small.png' alt='U.S. flag'></img>,
    headerText: 'An official website of the United States government',
    headerActionText: 'Here"s how you know'
};

import { ComponentStory, ComponentMeta } from '@storybook/react';
import { BannerButton } from '../components/TopBanner/BannerButton/BannerButton';

export default {
    title: 'Components/TopBanner/BannerButton',
    component: BannerButton
} as ComponentMeta<typeof BannerButton>;

const Template: ComponentStory<typeof BannerButton> = (args) => <BannerButton {...args}>Click Me</BannerButton>;

export const SampleBannerButton = Template.bind({});
SampleBannerButton.args = {
    className: 'usa-accordion__button usa-banner__button',
    isOpen: false,
    type: 'button',
    "aria-controls": 'custom-controls'
};

import React, { ReactElement, useState } from 'react';
import { Banner } from './Banner/banner';
import { BannerLockImage } from './BannerLockImage/BannerLockImage';
import { BannerHeader } from './BannerHeader/BannerHeader';
import { BannerFlag } from './BannerFlag/BannerFlag';
import { BannerButton } from './BannerButton/BannerButton';
import { BannerContent } from './BannerContent/BannerContent';
import { BannerGuidance } from './BannerGuidance/BannerGuidance';
import { BannerIcon } from './BannerIcon/BannerIcon';
import { MediaBlockBody } from '../MediaBlockBody/MediaBlockBody';
import flagImage from './Assets/us_flag_small.png';
import dotIcon from './Assets/icon-dot-gov.svg';
import httpIcon from './Assets/icon-https.svg';

export const TopBanner = (): ReactElement => {
    const [isOpen, setIsOpen] = useState(false);

    const lock = <BannerLockImage title="Lock" description="A locked padlock" />;

    return (
        <Banner>
            <BannerHeader
                isOpen={isOpen}
                flagImg={<BannerFlag src={flagImage} alt="U.S. flag" />}
                headerText="An official website of the United States government"
                headerActionText="Here's how you know">
                <BannerButton
                    isOpen={isOpen}
                    aria-controls="custom-banner"
                    onClick={(): void => {
                        setIsOpen((previousIsOpen) => !previousIsOpen);
                    }}>
                    Here&apos;s how you know
                </BannerButton>
            </BannerHeader>
            <BannerContent id="custom-banner" isOpen={isOpen}>
                <div className="grid-row grid-gap-lg">
                    <BannerGuidance className="tablet:grid-col-6">
                        <BannerIcon src={dotIcon} alt="" />
                        <MediaBlockBody>
                            <p>
                                <strong>Official websites use .gov</strong>
                                <br />A <strong>.gov</strong> website belongs to an official government organization in
                                the United States.
                            </p>
                        </MediaBlockBody>
                    </BannerGuidance>
                    <BannerGuidance className="tablet:grid-col-6">
                        <BannerIcon src={httpIcon} alt="" />
                        <MediaBlockBody>
                            <p>
                                <strong>Secure .gov websites use HTTPS</strong>
                                <br />A <strong>lock ( {lock} )</strong> or <strong>https://</strong> means you&apos;ve
                                safely connected to the .gov website. Share sensitive information only on official,
                                secure websites.
                            </p>
                        </MediaBlockBody>
                    </BannerGuidance>
                </div>
            </BannerContent>
        </Banner>
    );
};

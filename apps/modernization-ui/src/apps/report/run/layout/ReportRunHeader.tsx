import { ReactNode } from 'react';

import { ReportConfiguration } from 'generated';
import { Heading } from 'components/heading';

type ReportRunHeaderProps = {
    config: ReportConfiguration;
    actions: ReactNode;
};

export const ReportRunHeader = ({ config, actions }: ReportRunHeaderProps) => {
    return (
        <header>
            <nav>
                <Heading level={1}>Run Report: {config.title}</Heading>
                <div>{actions}</div>
            </nav>
        </header>
    );
};

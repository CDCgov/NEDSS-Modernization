import { ReactNode } from 'react';

import { ReportConfiguration } from 'generated';
import { Heading } from 'components/heading';

type ReportRunHeaderProps = {
    config: ReportConfiguration;
    actions: ReactNode;
};

export const ReportRunHeader = ({ config, actions }: ReportRunHeaderProps) => {
    return (
        <div>
            <Heading level={1}>Run Report: {config.reportTitle}</Heading>
            <div>{actions}</div>
        </div>
    );
};

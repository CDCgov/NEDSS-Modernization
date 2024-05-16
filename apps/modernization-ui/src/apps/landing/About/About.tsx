import style from './about.module.scss';

export const About = () => {
    return (
        <>
            <div className={style.first}>
                <p>
                    We are committed to maintaining the demo site with the most recent iteration of the software. The
                    current iteration reflects the latest available integration.
                </p>
                <h2>Documentation</h2>
                <p>
                    Please see documentation associated with releases to see the updated features and content. You can
                    find the documentation (Release Notes, User Guide, System Admin Guide, etc) on Github and NBS
                    Central:
                </p>
                <strong>GitHub</strong>
                <p>
                    <a href="https://github.com/CDCgov/NEDSS-Modernization/releases">
                        https://github.com/CDCgov/NEDSS-Modernization/releases
                    </a>
                </p>
                <strong>NBS Central (Latest release documentation)</strong>
                <p>
                    <a href="https://cdcnbscentral.com/projects/71release-materials/documents">
                        https://cdcnbscentral.com/projects/71release-materials/documents
                    </a>
                </p>
                <strong>Demo features guide (How to use the demo)</strong>
                <p>
                    <a href="https://cdcnbscentral.com/attachments/29901">
                        https://cdcnbscentral.com/attachments/29901
                    </a>
                    <br />
                    <br />
                    All the data that currently exists is synthetic data. We are working on improving our synthetic
                    database to be more representative of a production environment.
                </p>
            </div>
            <div>
                <h2>Feedback</h2>
                <p>
                    Please provide feedback here:
                    <br />
                    <a href="https://cdcprime.gov1.qualtrics.com/jfe/form/SV_3NNZwwdTOlnIIxU">
                        https://cdcprime.gov1.qualtrics.com/jfe/form/SV_3NNZwwdTOlnIIxU
                    </a>
                </p>
                <p>
                    If you cannot access the link, please reach out to us at{' '}
                    <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a>, and we will work with you on an alternate method for
                    feedback.
                </p>
                <h2>Technical Support</h2>
                <p>
                    Our technical support can be reached at <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a>. You can also
                    submit a ticket on NBS Central:
                    <br />
                    <a href="https://cdcnbscentral.com/projects/70collaboration/issues">
                        https://cdcnbscentral.com/projects/70collaboration/issues
                    </a>
                </p>
            </div>
        </>
    );
};

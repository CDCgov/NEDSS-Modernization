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
                <strong>NBS Central</strong>
                <p>
                    <a href="https://cdcnbscentral.com/projects/nbs7x">https://cdcnbscentral.com/projects/nbs7x</a>
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
                    Please provide feedback by reaching out to us with your suggestions and experiences using the NBS 7
                    demo site at <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a>.
                </p>
                <p>
                    In the future we will be adding an on-page feedback tool to collect your input on the development of
                    the modernized NBS application. Please look for that feature in coming updates.
                </p>
                <h2>Technical Support</h2>
                <p>
                    Our technical support can be reached at <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a>.
                </p>
                <p>
                    You can also submit a ticket on NBS Central:
                    <br />
                    <a href="https://cdcnbscentral.com/projects/70collaboration/issues">
                        https://cdcnbscentral.com/projects/70collaboration/issues
                    </a>
                </p>
            </div>
        </>
    );
};

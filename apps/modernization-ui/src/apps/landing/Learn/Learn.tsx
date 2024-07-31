import style from './learn.module.scss';

export const Learn = () => {
    return (
        <>
            <div className={style.first}>
                <p>There are many ways to learn more about NBS.</p>
                <h2>Website</h2>
                <p>
                    <a href="https://www.cdc.gov/nbs/modernization/index.html">
                        https://www.cdc.gov/nbs/modernization/index.html
                    </a>
                    <br />
                    Find the updates about NBS modernization and discover the latest features on the NBS demo site.
                </p>

                <h2>Newsletter</h2>
                <p>
                    <a href="https://cste.us6.list-manage.com/subscribe?u=a74794e707a0d58b86a809758&id=e18a85073c">
                        https://cste.us6.list-manage.com/subscribe?u=a74794e707a0d58b86a809758&id=e18a85073c
                    </a>
                    <br />
                    Use the link above to subscribe, or read the current Case surveillance news here:{' '}
                    <a href="https://www.cdc.gov/nndss/trc/news/index.html">
                        https://www.cdc.gov/nndss/trc/news/index.html
                    </a>
                    .
                </p>

                <h2>NBS community activities</h2>
                <p>
                    <a href="https://www.cdc.gov/nbs/resources/community.html">
                        https://www.cdc.gov/nbs/resources/community.html
                    </a>
                    <br />
                    The NBS User Group (NUG) and the NBS Subject Matter Expert (SME) Calls meet every week, and include
                    local/state/territorial users and CDC programs. Please email us at{' '}
                    <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a> if you are interested in joining the groups.
                </p>
            </div>
            <div>
                <strong>The NBS User Group (NUG)</strong>
                <p>
                    NUG calls provide a forum for participants to discuss the latest NBS software development, current
                    topics, and best practices. The user group also provides a platform for jurisdictions to
                    collaborate, discuss common issues, and share lessons learned.
                </p>
                <strong>The NBS Subject Matter Expert (SME) calls</strong>
                <p>
                    They allow participants to discuss detailed NBS software release requirements and collaborate on NBS
                    software development and best practices.
                </p>
                <h2>More ways to get involved</h2>
                <p>
                    If you are interested in becoming a pilot partner, or participating in the co-creation sessions,
                    please send us an email to <a href="mailto:nbs@cdc.gov">nbs@cdc.gov</a>.
                </p>
            </div>
        </>
    );
};

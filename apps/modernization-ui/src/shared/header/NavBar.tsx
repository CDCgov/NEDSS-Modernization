import { useContext } from 'react';
import { useLocation } from 'react-router-dom';
import { Config } from 'config';
import { UserContext } from 'providers/UserContext';
import { useNavigationBarPermissions } from 'shared/header/permissions';
import styles from './NavBar.module.scss';

// eslint-disable-next-line no-undef
const NBS_URL = Config.nbsUrl;

export default function NavBar() {
    const {
        state: { user },
        logout
    } = useContext(UserContext);
    const location = useLocation();
    const logoutClick = () => {
        logout();
    };
    const { systemManagementAccess } = useNavigationBarPermissions();

    return (
        <div className={styles.navbar}>
            <table role="presentation" className={styles.nedssNavTable}>
                <tbody>
                    <tr>
                        <td>
                            <table role="presentation">
                                <tbody>
                                    <tr>
                                        <td className={styles.navLink}>
                                            <a href={`${NBS_URL}/HomePage.do?method=loadHomePage`}>Home</a>
                                        </td>
                                        <td>
                                            {' '}
                                            <span> | </span>{' '}
                                        </td>

                                        <td className={styles.navLink}>
                                            <a href={`${NBS_URL}/LoadNavbar.do?ContextAction=DataEntry`}>Data Entry</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a href={`${NBS_URL}/LoadNavbar1.do?ContextAction=MergePerson`}>
                                                Merge Patients
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a
                                                href={`${NBS_URL}/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true`}>
                                                Open Investigations
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a href={`${NBS_URL}/nfc?ObjectType=7&amp;OperationType=116`}>Reports</a>
                                        </td>

                                        {systemManagementAccess && (
                                            <td className={styles.navLink}>
                                                <span> | </span>
                                                <a href={`${NBS_URL}/SystemAdmin.do`}>System Management</a>
                                            </td>
                                        )}
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table role="presentation" align="right">
                                <tbody>
                                    <tr>
                                        <td className={styles.navLink}>
                                            <a
                                                href={`${NBS_URL}/UserGuide.do?method=open`}
                                                target="_blank"
                                                rel="noreferrer">
                                                {' '}
                                                Help{' '}
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>
                                        <td className={styles.navLink}>
                                            <a onClick={logoutClick}>Logout</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td style={{ width: '105px' }}>&nbsp;</td>
                    </tr>
                </tbody>
            </table>
            <h1
                className={styles.pageHeader}
                style={{ padding: '0px', margin: '0px', fontSize: '13px', color: '#185394' }}>
                <table role="presentation" className={styles.nedssPageHeaderAndLogoTable}>
                    <tbody>
                        <tr>
                            <td className={styles.pageHeader} style={{ padding: '5px', marginBottom: '0px' }}>
                                <a style={{ textTransform: 'capitalize' }}>
                                    {location?.pathname?.split('/')[1]?.split('-').join(' ')}
                                </a>
                            </td>

                            <td className={styles.currentUser} style={{ paddingBottom: '0px', marginBottom: '0px' }}>
                                User : {user?.name.display}
                            </td>

                            <td
                                className={`${styles.currentUser} ${styles.logo}`}
                                style={{ paddingBottom: '0px', marginBottom: '0px' }}>
                                <img
                                    style={{ background: '#DCDCDC', border: 0 }}
                                    title="Logo"
                                    alt="NBS Logo"
                                    height="32"
                                    width="80"
                                    src="/nedssLogo.jpeg"
                                />
                            </td>
                        </tr>
                        <tr>
                            <td
                                colSpan={3}
                                style={{
                                    padding: '0px',
                                    margin: '0px',
                                    height: '9px',
                                    backgroundImage: 'url(dropshadow.gif)',
                                    backgroundRepeat: 'repeat-x'
                                }}></td>
                        </tr>
                    </tbody>
                </table>
            </h1>
        </div>
    );
}

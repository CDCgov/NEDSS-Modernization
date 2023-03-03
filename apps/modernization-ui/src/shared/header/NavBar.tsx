import { useContext } from 'react';
import { Config } from '../../config';
import { UserContext } from '../../providers/UserContext';
import './NavBar.scss';
import { useLocation } from 'react-router-dom';

// eslint-disable-next-line no-undef
const NBS_URL = Config.nbsUrl;

export default function NavBar() {
    const { state, logout } = useContext(UserContext);
    const location = useLocation();
    return (
        <div className="nav-bar">
            <table role="presentation" className="nedssNavTable">
                <tbody>
                    <tr>
                        <td>
                            <table role="presentation" align="left">
                                <tbody>
                                    <tr>
                                        <td className="navLink">
                                            <a href={`${NBS_URL}/HomePage.do?method=loadHomePage`}>Home</a>
                                        </td>
                                        <td>
                                            {' '}
                                            <span> | </span>{' '}
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/LoadNavbar.do?ContextAction=DataEntry`}>Data Entry</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/LoadNavbar1.do?ContextAction=MergePerson`}>
                                                Merge Patients
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a
                                                href={`${NBS_URL}/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true`}>
                                                Open Investigations
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/nfc?ObjectType=7&amp;OperationType=116`}>Reports</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/SystemAdmin.do`}>System Management</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table role="presentation" align="right">
                                <tbody>
                                    <tr>
                                        <td className="navLink">
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
                                        <td className="navLink">
                                            <a onClick={logout} href={`${NBS_URL}/logout`}>
                                                Logout
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td style={{ width: '105px' }}>&nbsp;</td>
                    </tr>
                </tbody>
            </table>
            <h1 className="pageHeader" style={{ padding: '0px', margin: '0px', fontSize: '13px', color: '#185394' }}>
                <table role="presentation" className="nedssPageHeaderAndLogoTable">
                    <tbody>
                        <tr>
                            <td className="pageHeader" style={{ padding: '5px', marginBottom: '0px' }}>
                                <a style={{ textTransform: 'capitalize' }}>
                                    {location?.pathname?.split('/')[1]?.split('-').join(' ')}
                                </a>
                            </td>

                            <td className="currentUser" style={{ paddingBottom: '0px', marginBottom: '0px' }}>
                                User : {state.displayName}
                            </td>

                            <td className="currentUser logo" style={{ paddingBottom: '0px', marginBottom: '0px' }}>
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

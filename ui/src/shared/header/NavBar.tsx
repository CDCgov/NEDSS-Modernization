import './NavBar.scss';

// eslint-disable-next-line no-undef
const NBS_URL = process.env.REACT_APP_NBS_URL;

export default function NavBar() {
    return (
        <>
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
                                                href={`${NBS_URL}/LoadNavbar.do?ContextAction=GlobalInvestigations&amp;initLoad=true`}>
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
                                            <a href={`${NBS_URL}/logout`}>Logout</a>
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
                                <a> Release 6.0.11-BETA Dashboard </a>
                            </td>

                            <td className="currentUser" style={{ paddingBottom: '0px', marginBottom: '0px' }}>
                                User : Henry Clark
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
                    </tbody>
                </table>
            </h1>
        </>
    );
}

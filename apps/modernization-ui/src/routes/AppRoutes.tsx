import PageBuilderContextProvider from 'apps/page-builder/context/PageBuilderContext';
import { PageLibrary } from 'apps/page-builder/page/library/PageLibrary';
import { Edit } from 'apps/page-builder/page/management/edit/Edit';
import { PreviewPage } from 'apps/page-builder/page/management/preview';
import { AddNewPage } from 'apps/page-builder/pages/AddNewPage/AddNewPage';
import { BusinessRulesLibrary } from 'apps/page-builder/pages/BusinessRulesLibrary/BusinessRulesLibrary';
import EditBusinessRules from 'apps/page-builder/pages/BusinessRulesLibrary/Edit/EditBusinessRules';
import { AddPatient } from 'apps/patient/add/AddPatient';
import { AddedPatient } from 'apps/patient/add/SuccessForm/AddedPatient';
import { PatientProfile } from 'apps/patient/profile';
import { Spinner } from 'components/Spinner';
import { Config } from 'config';
import { useConfiguration } from 'configuration';
import { Library, Management } from 'generated';
import { Layout } from 'layout';
import { AdvancedSearch } from 'pages/advancedSearch/AdvancedSearch';
import { Login } from 'pages/login/Login';
import { UserContext } from 'providers/UserContext';
import { ReactNode, useContext, useEffect, useState } from 'react';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';

const ScrollToTop = ({ children }: { children: ReactNode }) => {
    const location = useLocation();
    useEffect(() => {
        window.scrollTo(0, 0);
    }, [location]);

    return <>{children}</>;
};

export const AppRoutes = () => {
    const { state } = useContext(UserContext);
    const location = useLocation();
    const [loading, setLoading] = useState(location.pathname !== '/dev/login'); // allow login page to load immediately
    const [initializing, setInitializing] = useState(true);
    const config = useConfiguration();

    useEffect(() => {
        if (state) {
            if (state.isLoggedIn && !config.loading) {
                setLoading(false);
            }
        }
    }, [state, config]);

    useEffect(() => {
        // After initialization timeout, if the login isn't at least pending, send to the login page
        if (!initializing && !state.isLoggedIn && !state.isLoginPending) {
            setLoading(false);
        }
    }, [initializing, state.isLoggedIn, state.isLoginPending]);

    // allow 1 second to initialize and send a login request
    setTimeout(() => {
        setInitializing(false);
    }, 1000);

    const pageLibraryRoutes = (libraryConfig: Library) => {
        return libraryConfig.enabled && <Route index element={<PageLibrary />} />;
    };

    const pageManagementRoutes = (managementConfig: Management) => {
        return (
            <>
                {managementConfig.create?.enabled && <Route path="add" element={<AddNewPage />} />}
                {managementConfig.edit?.enabled && (
                    <Route path=":pageId">
                        <Route index element={<PreviewPage />} />
                        <Route path="edit" element={<Edit />} />
                        <Route path="business-rules-library" element={<BusinessRulesLibrary />} />
                        <Route path=":ruleId">
                            <Route index element={<EditBusinessRules />} />
                        </Route>
                    </Route>
                )}
            </>
        );
    };

    return (
        <>
            {loading && <Spinner />}
            <ScrollToTop>
                <Routes>
                    <Route element={<Layout />}>
                        {state.isLoggedIn && !loading && (
                            <>
                                <Route path="/advanced-search/:searchType?" element={<AdvancedSearch />} />
                                <Route path="/patient-profile/:id" element={<PatientProfile />} />
                                <Route path="/add-patient" element={<AddPatient />} />
                                <Route path="/add-patient/patient-added" element={<AddedPatient />} />

                                {config.features.pageBuilder.enabled && (
                                    <Route path="/page-builder" element={<PageBuilderContextProvider />}>
                                        <Route path="pages">
                                            {pageLibraryRoutes(config.features.pageBuilder.page.library)}
                                            {pageManagementRoutes(config.features.pageBuilder.page.management)}
                                        </Route>
                                    </Route>
                                )}
                                {!config.loading && (
                                    <>
                                        <Route path="*" element={<Navigate to="/advanced-search" />} />
                                        <Route path="/" element={<Navigate to="/advanced-search" />} />
                                    </>
                                )}
                            </>
                        )}

                        {Config.enableLogin && (
                            <>
                                {!state.isLoggedIn && !state.isLoginPending && !loading && (
                                    <>
                                        <Route path="/dev/login" element={<Login />} />
                                        <Route path="*" element={<Navigate to="/dev/login" />} />
                                    </>
                                )}
                            </>
                        )}
                        {!Config.enableLogin && (
                            <>
                                {!state.isLoggedIn && !state.isLoginPending && !loading && (
                                    <Route
                                        path="*"
                                        element={<>{(window.location.href = `${Config.nbsUrl}/login`)}</>}
                                    />
                                )}
                            </>
                        )}
                    </Route>
                </Routes>
            </ScrollToTop>
        </>
    );
};

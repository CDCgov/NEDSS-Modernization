import PageBuilderContextProvider from 'apps/page-builder/context/PageBuilderContext';
import { PageLibrary } from 'apps/page-builder/page/library/PageLibrary';
import { Edit } from 'apps/page-builder/page/management/edit/Edit';
import { PreviewPage } from 'apps/page-builder/page/management/preview';
import { AddNewPage } from 'apps/page-builder/pages/AddNewPage/AddNewPage';
import { Spinner } from 'components/Spinner';
import { Config } from 'config';
import { useConfiguration } from 'configuration';
import { Layout } from 'layout';
import { CompareInvestigations } from 'pages/CompareInvestigations/CompareInvestigations';
import { AddPatient } from 'pages/addPatient/AddPatient';
import { AddedPatient } from 'pages/addPatient/components/SuccessForm/AddedPatient';
import { AdvancedSearch } from 'pages/advancedSearch/AdvancedSearch';
import { Login } from 'pages/login/Login';
import { PatientProfile } from 'pages/patient/profile';
import { UserContext } from 'providers/UserContext';
import { ReactNode, useContext, useEffect, useState } from 'react';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import { QuestionLibrary } from 'apps/page-builder/pages/QuestionLibrary/QuestionLibrary';

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

    const pageLibraryRoutes = (enabled: boolean) => {
        return enabled ? <Route index element={<PageLibrary />} /> : <Route index element={<Navigate to={'/'} />} />;
    };

    const pageManagementRoutes = (enabled: boolean) => {
        return enabled ? (
            <>
                <Route path="add" element={<AddNewPage />} />
                <Route path=":pageId">
                    <Route index element={<PreviewPage />} />
                    <Route path="edit" element={<Edit />} />
                </Route>
            </>
        ) : (
            <Route index element={<Navigate to={'/'} />} />
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
                                <Route path="/compare-investigation/:id" element={<CompareInvestigations />} />
                                <Route path="/add-patient" element={<AddPatient />} />
                                <Route path="/add-patient/patient-added" element={<AddedPatient />} />

                                {config.features.pageBuilder.enabled && (
                                    <Route path="/page-builder" element={<PageBuilderContextProvider />}>
                                        <Route path="pages">
                                            {pageLibraryRoutes(config.features.pageBuilder.page.library.enabled)}
                                            {pageManagementRoutes(config.features.pageBuilder.page.management.enabled)}
                                        </Route>
                                        <Route path="question-library" element={<QuestionLibrary />} />
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

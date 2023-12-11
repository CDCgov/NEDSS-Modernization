import { ReactNode, useContext, useEffect, useState } from 'react';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import { Config } from 'config';
import { useConfiguration } from 'configuration';
import { UserContext } from 'providers/UserContext';
import { Spinner } from 'components/Spinner';
import { Login } from 'pages/login/Login';
import { AdvancedSearch } from 'pages/advancedSearch/AdvancedSearch';
import { PatientProfile } from 'pages/patient/profile';
import { CompareInvestigations } from 'pages/CompareInvestigations/CompareInvestigations';
import { AddPatient } from 'pages/addPatient/AddPatient';
import { AddedPatient } from 'pages/addPatient/components/SuccessForm/AddedPatient';
import PageBuilderContextProvider from 'apps/page-builder/context/PageBuilderContext';
import { AddNewPage } from 'apps/page-builder/pages/AddNewPage/AddNewPage';
import ConditionLibrary from 'apps/page-builder/pages/ConditionLibrary/ConditionLibrary';
import { EditPage } from 'apps/page-builder/pages/EditPage/EditPage';
import { PageLibrary } from 'apps/page-builder/page/library/PageLibrary';
import { ConditionalCase } from 'apps/page-builder/components/ConditionalCase/ConditionalCase';
import { CreateCondition } from 'apps/page-builder/components/CreateCondition/CreateCondition';
import { CreateQuestion } from 'apps/page-builder/components/CreateQuestion/CreateQuestion';
import { BusinessRulesLibrary } from 'apps/page-builder/pages/BusinessRulesLibrary/BusinessRulesLibrary';
import { QuestionLibrary } from 'apps/page-builder/pages/QuestionLibrary/QuestionLibrary';
import { ValuesetLibrary } from 'apps/page-builder/pages/ValuesetLibrary/ValuesetLibrary';
import { Edit } from 'apps/page-builder/page/management/Edit/Edit';

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

    return (
        <>
            {loading && <Spinner />}
            <ScrollToTop>
                <Routes>
                    {state.isLoggedIn && !loading && (
                        <>
                            <Route path="/advanced-search/:searchType?" element={<AdvancedSearch />} />
                            <Route path="/patient-profile/:id" element={<PatientProfile />} />
                            <Route path="/compare-investigation/:id" element={<CompareInvestigations />} />
                            <Route path="/add-patient" element={<AddPatient />} />
                            <Route path="/add-patient/patient-added" element={<AddedPatient />} />

                            {config.features.pageBuilder.enabled ? (
                                <Route path="/page-builder" element={<PageBuilderContextProvider />}>
                                    <Route path="manage">
                                        <Route path="pages" element={<PageLibrary />} />
                                        <Route path="valueset-library" element={<ValuesetLibrary />} />
                                        <Route path="question-library" element={<QuestionLibrary />} />
                                        <Route path="business-rules-library" element={<BusinessRulesLibrary />} />
                                        <Route path="condition-library" element={<ConditionLibrary />} />
                                    </Route>
                                    <Route path="add">
                                        <Route path="page" element={<AddNewPage />} />
                                        <Route path="condition" element={<CreateCondition />} />
                                        <Route path="conditional-case" element={<ConditionalCase />} />
                                        <Route path="question" element={<CreateQuestion />} />
                                    </Route>
                                    <Route path="edit">
                                        <Route path="page/:pageId?" element={<EditPage />} />
                                    </Route>
                                    <Route path="pages">
                                        <Route path=":pageId">
                                            <Route path="edit" element={<Edit />} />
                                        </Route>
                                    </Route>
                                </Route>
                            ) : null}
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
                                <Route path="*" element={<>{(window.location.href = `${Config.nbsUrl}/login`)}</>} />
                            )}
                        </>
                    )}
                </Routes>
            </ScrollToTop>
        </>
    );
};

import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import { AdvancedSearch } from '../pages/advancedSearch/AdvancedSearch';
import { Login } from '../pages/login/Login';
import { PatientProfile } from '../pages/patient/profile';
import AddPatient from '../pages/addPatient/AddPatient';
import { ManagePages } from 'apps/page-builder/pages/ManagePages/ManagePages';
import { ReactNode, useContext, useEffect, useState } from 'react';
import { UserContext } from 'providers/UserContext';
import { Spinner } from 'components/Spinner/Spinner';
import { CompareInvestigations } from 'pages/CompareInvestigations/CompareInvestigations';
import { AddedPatient } from 'pages/addPatient/components/SuccessForm/AddedPatient';

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
    const [loading, setLoading] = useState(location.pathname !== '/login'); // allow login page to load immediately
    const [initializing, setInitializing] = useState(true);

    useEffect(() => {
        if (state) {
            if (state.isLoggedIn) {
                setLoading(false);
            }
        }
    }, [state]);

    useEffect(() => {
        // After initialization timeout, if the login isn't at least pending, send to the login page
        if (!initializing && !state.isLoggedIn && !state.isLoginPending) {
            setLoading(false);
        }
    }, [initializing]);

    // allow 1 second to initialize and send a login request
    setTimeout(() => {
        setInitializing(false);
    }, 1000);

    return (
        <>
            {loading && <Spinner />}
            <ScrollToTop>
                <Routes>
                    {state.isLoggedIn && (
                        <>
                            <Route path="/advanced-search/:searchType?" element={<AdvancedSearch />} />
                            <Route path="/patient-profile/:id" element={<PatientProfile />} />
                            <Route path="/compare-investigation/:id" element={<CompareInvestigations />} />
                            <Route path="/add-patient" element={<AddPatient />} />
                            <Route path="/add-patient/patient-added" element={<AddedPatient />} />

                            <Route path="/page-builder">
                                <Route path="manage">
                                    <Route path="pages" element={<ManagePages />} />
                                </Route>
                            </Route>

                            <Route path="*" element={<Navigate to="/advanced-search" />} />
                            <Route path="/" element={<Navigate to="/advanced-search" />} />
                        </>
                    )}
                    {!state.isLoggedIn && !state.isLoginPending && !loading && (
                        <Route path="*" element={<Navigate to="/login" />} />
                    )}
                    <Route path="/login" element={<Login />} />
                </Routes>
            </ScrollToTop>
        </>
    );
};

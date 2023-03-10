import { Navigate, Route, Routes } from 'react-router-dom';
import { AdvancedSearch } from '../pages/advancedSearch/AdvancedSearch';
import { Login } from '../pages/login/Login';
import { PatientProfile } from '../pages/patientProfile/PatientProfile';
import AddPatient from '../pages/addPatient/AddPatient';
import { useContext } from 'react';
import { UserContext } from 'providers/UserContext';

export const AppRoutes = () => {
    const { state } = useContext(UserContext);

    return (
        <Routes>
            {state.isLoggedIn && state.isLoginPending && (
                <>
                    <Route path="/advanced-search/:searchType?" element={<AdvancedSearch />} />
                    <Route path="/patient-profile/:id" element={<PatientProfile />} />
                    <Route path="/add-patient" element={<AddPatient />} />
                    <Route path="*" element={<Navigate to="/advanced-search" />} />
                    <Route path="/" element={<Navigate to="/advanced-search" />} />
                </>
            )}
            <Route path="*" element={<Login />} />
        </Routes>
    );
};

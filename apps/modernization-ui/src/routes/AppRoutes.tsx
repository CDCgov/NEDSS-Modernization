import { Navigate, Route, Routes } from 'react-router-dom';
import { AdvancedSearch } from '../pages/advancedSearch/AdvancedSearch';
import { Login } from '../pages/login/Login';
import { PatientProfile } from '../pages/patientProfile/PatientProfile';

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/">
                <Route path="/login" element={<Login />} />
                <Route path="/advanced-search" element={<AdvancedSearch />} />
                <Route path="/patient-profile/:id" element={<PatientProfile />} />
                <Route path="*" element={<Navigate to="/advanced-search" />} />
                <Route path="/" element={<Navigate to="/advanced-search" />} />
            </Route>
        </Routes>
    );
};

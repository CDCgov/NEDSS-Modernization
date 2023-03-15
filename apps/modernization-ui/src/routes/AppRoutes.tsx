import { Navigate, Route, Routes } from 'react-router-dom';
import { AdvancedSearch } from '../pages/advancedSearch/AdvancedSearch';
import { Login } from '../pages/login/Login';
import { PatientProfile } from '../pages/patientProfile/PatientProfile';
import AddPatient from '../pages/addPatient/AddPatient';
import { CompareInvestigations } from 'pages/CompareInvestigations/CompareInvestigations';

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/">
                <Route path="/login" element={<Login />} />
                <Route path="/advanced-search" element={<AdvancedSearch />} />
                <Route path="/patient-profile/:id" element={<PatientProfile />} />
                <Route path="/compare-investigation/:id" element={<CompareInvestigations />} />
                <Route path="/add-patient" element={<AddPatient />} />
                <Route path="*" element={<Navigate to="/advanced-search" />} />
                <Route path="/" element={<Navigate to="/advanced-search" />} />
            </Route>
        </Routes>
    );
};

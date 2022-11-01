import { Routes, Route, Navigate } from 'react-router-dom';
import AddPatient from '../pages/addPatient/AddPatient';
import { AdvancedSearch } from '../pages/advancedSearch/advancedSearch';
import { SimpleSearch } from '../pages/search/Search';

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/">
                <Route path="/search" element={<SimpleSearch />} />
                <Route path="/patient" element={<AddPatient />} />
                <Route path="/advanced-search" element={<AdvancedSearch />} />
                <Route path="*" element={<Navigate to="/search" />} />
                <Route path="/" element={<Navigate to="/search" />} />
            </Route>
        </Routes>
    );
};

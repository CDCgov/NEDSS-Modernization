import { Routes, Route, Navigate } from 'react-router-dom';
import AddPatient from '../pages/addPatient/AddPatient';
import { Home } from '../pages/home/Home';
import { SearchEngine } from '../pages/search';

export const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/">
                <Route path="/search" element={<Home />} />
                <Route path="/patient" element={<AddPatient />} />
                <Route path="/advanced-search" element={<SearchEngine />} />
                <Route path="*" element={<Navigate to="/search" />} />
                <Route path="/" element={<Navigate to="/search" />} />
            </Route>
        </Routes>
    );
};

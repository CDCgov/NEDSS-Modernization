import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { AppRoutes } from 'routes/AppRoutes';

import 'styles/global.scss';

const container = document.getElementById('root');
const root = createRoot(container!);
root.render(
    <StrictMode>
        <AppRoutes />
    </StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

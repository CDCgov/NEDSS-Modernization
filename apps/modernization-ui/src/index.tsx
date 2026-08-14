import { StrictMode } from 'react';

import { createRoot } from 'react-dom/client';

import { AppRoutes } from 'routes/AppRoutes';

import 'styles/global.scss';

const container = document.getElementById('root');
const root = createRoot(container!);
root.render(
    <StrictMode>
        <AppRoutes />
    </StrictMode>
);

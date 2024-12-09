import { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { DefaultNewPatentEntry, NewPatientEntry } from 'apps/patient/add';

const usePreFilled = (initial: DefaultNewPatentEntry): NewPatientEntry => {
    const location = useLocation();
    const [prefilled, setPrefilled] = useState<NewPatientEntry>(initial);

    useEffect(() => {
        if (location?.state?.defaults) {
            setPrefilled(location?.state?.defaults);
        }
    }, [location?.state?.criteria, location?.state?.defaults]);

    return prefilled;
};

export { usePreFilled };

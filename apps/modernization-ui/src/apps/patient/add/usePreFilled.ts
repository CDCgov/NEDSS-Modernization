import { DefaultNewPatentEntry, NewPatientEntry } from 'apps/patient/add';
import { useEffect, useState } from 'react';
import { useLocation } from 'react-router';

const usePreFilled = (initial: DefaultNewPatentEntry): NewPatientEntry => {
    const location = useLocation();
    const [prefilled, setPrefilled] = useState<NewPatientEntry>(initial);

    useEffect(() => {
        if (location?.state?.defaults) {
            setPrefilled(location?.state?.defaults);
        }
    }, [location?.state?.defaults]);

    return prefilled;
};
export { usePreFilled };

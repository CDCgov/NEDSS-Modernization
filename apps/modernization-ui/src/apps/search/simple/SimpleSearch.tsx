import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { decrypt } from 'decryption';
import { Loading } from 'components/Spinner';
import { internalizeDate } from 'date';
import { BasicInformation } from 'apps/search/patient/criteria';

const internalize = (value: BasicInformation) => ({
    ...value,
    dateOfBirth: internalizeDate(value?.dateOfBirth)
});

const SimpleSearch = () => {
    const { type, criteria } = useParams();

    const navigate = useNavigate();

    useEffect(() => {
        if (criteria) {
            decrypt(criteria)
                .then((response) => internalize(response as BasicInformation))
                .then((decrypted) => navigate(`/search/${type}`, { state: decrypted, replace: true }));
        }
    }, [criteria]);

    return <Loading center />;
};

export { SimpleSearch };

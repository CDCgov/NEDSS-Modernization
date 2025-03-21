import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router';
import { Loading } from 'components/Spinner';

const SimpleSearch = () => {
    const { type, criteria } = useParams();

    const navigate = useNavigate();

    useEffect(() => {
        if (type) {
            const search = criteria ? `?q=${criteria}` : undefined;

            navigate({ pathname: `/search/${type}`, search }, { replace: true });
        }
    }, [type, criteria]);

    return <Loading center />;
};

export { SimpleSearch };

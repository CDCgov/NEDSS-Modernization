import { useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Loading } from 'components/Spinner';

const SimpleSearch = () => {
    const { type } = useParams();

    const navigate = useNavigate();

    useEffect(() => {
        if (type) {
            navigate({ pathname: `/search/${type}` }, { replace: true });
        }
    }, [type]);

    return <Loading center />;
};

export { SimpleSearch };

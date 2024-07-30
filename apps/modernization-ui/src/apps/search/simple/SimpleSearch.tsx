import { useParams, useSearchParams } from 'react-router-dom';

const SimpleSearch = () => {
    const { type } = useParams();
    const [params] = useSearchParams();

    return <>{type}</>;
};

export { SimpleSearch };

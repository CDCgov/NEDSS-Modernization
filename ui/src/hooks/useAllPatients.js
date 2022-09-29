import { useQuery, gql } from '@apollo/client';

const GET_PATIENTS = gql`
    {
        findAllPatients {
            personUid
            firstNm
            lastNm
            ageReported
        }
    }
`;

export const useAllPatients = () => {
    const { error, data, loading } = useQuery(GET_PATIENTS);

    return {
        error,
        data,
        loading
    };
};

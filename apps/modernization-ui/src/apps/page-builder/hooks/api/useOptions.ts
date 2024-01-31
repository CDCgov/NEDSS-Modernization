import { authorization } from 'authorization';
import { ConceptOptionsResponse, ConceptOptionsService } from 'generated';

export const useOptions = (name: string): Promise<ConceptOptionsResponse> => {
    return ConceptOptionsService.allUsingGet({ authorization: authorization(), name });
};

import { Selectable } from 'options';
import { exists } from 'utils';

/**
 * A RaceCategoryValidator that validates the existence of the provided race category.
 *
 * @param {number} id The unique identifier of the race entry that is being validated.
 * @param {Selectable | null} category The race category to validate
 *
 * @return {Promise<string | boolean>}
 */
const categoryRequiredValidator = (id: number, category: Selectable | null): Promise<string | boolean> =>
    !exists(category) ? Promise.resolve('The Race is required.') : Promise.resolve(true);

export { categoryRequiredValidator };

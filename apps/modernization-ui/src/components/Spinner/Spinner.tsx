import { Loading } from './Loading';
import './Spinner.scss';

export const Spinner = () => {
    return (
        <div className="spinner">
            <Loading />
        </div>
    );
};

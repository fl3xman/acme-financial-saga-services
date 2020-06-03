import * as H from "history";

export interface MediatorProps {
    namespace: string;
    name: string;
    host: string;
    history: H.History;
}

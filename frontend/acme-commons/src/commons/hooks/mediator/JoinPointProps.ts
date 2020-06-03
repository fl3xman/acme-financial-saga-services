import * as H from "history";
import { interfaces } from "inversify";

export interface JoinPointProps {
    id: string;
    history?: H.History;
    container?: interfaces.Container;
}

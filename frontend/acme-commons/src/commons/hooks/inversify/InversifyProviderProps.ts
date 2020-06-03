import { interfaces } from "inversify";

export interface InversifyProviderProps {
    id?: string;
    container?: interfaces.Container;
    configurations?: interfaces.ContainerModule[];
    asyncConfigurations?: interfaces.AsyncContainerModule[];
}

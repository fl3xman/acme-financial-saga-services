import { interfaces } from "inversify";

export const inversifyOptionalGet = <T>(
    container: interfaces.Container,
    serviceIdentifier: interfaces.ServiceIdentifier<T>, defaultValue?: T
): T | undefined => {
    if (container.isBound(serviceIdentifier) || !defaultValue) {
        return container.get(serviceIdentifier);
    } else {
        return defaultValue;
    }
}
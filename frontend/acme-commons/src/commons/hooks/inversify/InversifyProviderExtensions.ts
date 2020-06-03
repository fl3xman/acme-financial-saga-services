import { interfaces } from "inversify";

import { inversifyOptionalGet } from "../../inversify";
import { useInversify } from "./InversifyProvider";

export const useInversifyOptionalGet = <T>(serviceIdentifier: interfaces.ServiceIdentifier<T>, defaultValue?: T): T | undefined =>
    inversifyOptionalGet(useInversify(), serviceIdentifier, defaultValue);

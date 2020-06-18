import { useInversifyOptionalGet } from "../inversify";
import { ReduxRegistryAware, ReduxRegistryAssembly } from "../../redux";

export const useReduxRegistry = (): ReduxRegistryAware | undefined =>
    useInversifyOptionalGet<ReduxRegistryAware>(ReduxRegistryAssembly.Instance);

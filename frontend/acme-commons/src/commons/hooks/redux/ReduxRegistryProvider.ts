import { useInversify } from "../inversify";
import { ReduxRegistryAware, ReduxRegistryAssembly } from "../../redux";

export const useReduxRegistry = (): ReduxRegistryAware => useInversify().get<ReduxRegistryAware>(ReduxRegistryAssembly.Instance);

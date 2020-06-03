import { useInversifyOptionalGet } from "../inversify";
import { LoggerFactory, Logger, LoggerAssembly } from "../../logger";

export const useLogger = (): Logger | undefined => useInversifyOptionalGet<Logger>(LoggerAssembly.Instance);
export const useLoggerFactory = (category?: string): Logger | undefined =>
    useInversifyOptionalGet<LoggerFactory>(LoggerAssembly.Factory)?.(category);

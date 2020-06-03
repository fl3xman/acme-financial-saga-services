export interface Logger {
    trace?(message: string, ...args: unknown[]): Logger | void;
    debug(message: string, ...args: unknown[]): Logger | void;
    info(message: string, ...args: unknown[]): Logger | void;
    warn(message: string, ...args: unknown[]): Logger | void;
    error(message: string, ...args: unknown[]): Logger | void;
}

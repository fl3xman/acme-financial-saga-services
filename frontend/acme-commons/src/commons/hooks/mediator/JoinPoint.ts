import * as H from "history";
import { interfaces } from "inversify";

export interface JoinPoint {
    mount(id: string, history?: H.History, container?: interfaces.Container, ...args: any[]): void;
    unmount(id: string): void;
}

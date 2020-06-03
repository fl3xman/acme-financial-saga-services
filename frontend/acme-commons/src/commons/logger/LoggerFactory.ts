import { Logger } from "./Logger";

export type LoggerFactory = (category?: string) => Logger;

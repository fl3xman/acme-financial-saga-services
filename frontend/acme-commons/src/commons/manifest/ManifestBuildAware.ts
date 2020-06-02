import { Manifest } from "./Manifest";
import { LoggerAware } from "../logger";

export interface ManifestBuildAware extends LoggerAware {
    url: string;
    id: string;
    manifest: Manifest;
}
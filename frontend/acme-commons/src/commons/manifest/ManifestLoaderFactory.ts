import { Observable } from "rxjs";
import { Manifest } from "./Manifest";

export type ManifestLoaderFactory = <M extends Manifest = Manifest>(host: string) => Observable<M>;

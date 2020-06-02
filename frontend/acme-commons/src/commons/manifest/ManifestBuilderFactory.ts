import { Observable } from "rxjs";

export type ManifestBuilderFactory = (host: string, id: string) => Observable<void>;